package com.sync.service.main.gps;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import com.sync.facade.entity.oracle.rule.RouteDeviate;
import com.sync.facade.entity.sqlserver.Car;
import com.sync.facade.entity.oracle.Order;
import com.sync.service.mapper.oracle.rule.RouteDeviateMapper;
import com.sync.service.mapper.sqlserver.GprsMapper;
import com.sync.service.mapper.oracle.OrderMapper;
import com.sync.service.producer.ProducerService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * gps同步信息
 */
@Component(value = "gpsJob")
public class GpsJob {
    private static Log log = LogFactory.getLog(GpsJob.class);

    @Autowired
    private ProducerService producerService;

    @Autowired
    private GprsMapper gprsMapper;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private RouteDeviateMapper routeDeviateMapper;

    private final String topic = "gps-topic";
    private final String ifPartition = "0";
    private final Integer partitionNum = 1;


    public void doTask() {
        Integer startRownum = 0;

        // 获取线路偏差的设置
        RouteDeviate routeDeviate = new RouteDeviate();
        Map<String, Object> routeMap = routeDeviateMapper.find(routeDeviate);

        double routeDeviateValue = Double.parseDouble(routeMap.get("routeDeviateValue") + "");  //获取的线路偏差百分比值转换为double
        routeDeviateValue = routeDeviateValue * 0.01; //转换为实际值



        log.info("gps同步信息开始1...");
        System.out.println("gps同步信息开始2...");


        try {
            Order order = new Order(); //查询订单信息
            Car car = new Car();       //查询GPRS表的车辆信息

            StringBuffer buf = new StringBuffer(); // 储存从gprs表获取的经纬度数据

            order.setIds("0,1,2");//-4已取消，-3被拒绝，-2待匹配，-1匹配中，0待接受，1等待上车，2在途,3完成

            List<Map<String, Object>> orderList = orderMapper.find(order);

            if (orderList.size() > 0) {     // 获取任务表里面的订单


                for (int i = 0; i < orderList.size(); i++) {

                    String deviation = "1"; //线路是否有偏差值  0:没有偏差 1:有偏差


                    if ((orderList.get(i)).get("orderCode").equals("DH-201801311737000_01")) {
                        // System.out.println((orderList.get(i)).get("orderCode"));
                    }

                    buf.delete(0, buf.length());
                    car.setCarNum((orderList.get(i)).get("carNum") + "");           //车牌
                    car.setBoaringTime((orderList.get(i)).get("boardingTime") + "");  //起始时间
                    car.setDebusTime((orderList.get(i)).get("debusTime") + "");       //结束时间

                    //获取RPRS表车辆数据
                    List<Map<String, Object>> carList = gprsMapper.find(car);

                    if (carList.size() > 0) {                      // 获取GRPS表里面的信息

                        int comparisonValue = (int) Math.floor(carList.size() * routeDeviateValue); //算出需要对比的点数

                        int complementation = carList.size() / comparisonValue;  //算出求余数，去数组获取


                        ArrayList<String> compList = new ArrayList<String>();

                        for (int j = 0; j < carList.size(); j++) {

                            if (j % complementation == 0) {   //均匀取出需要对比的值
                                compList.add((carList.get(j)).get("longitude") + "," + (carList.get(j)).get("latitude"));
                            }
                            buf.append((carList.get(j)).get("longitude")).append(",");
                            buf.append((carList.get(j)).get("latitude")).append(";");
                        }


                        //请求订单号的规划路径的经纬度集合

                        if (((orderList.get(i)).get("startlongLat") + "").length() > 5 && ((orderList.get(i)).get("endlongLat") + "").length() > 5) {  //判断是否为空
                            String url = "http://api.map.baidu.com/direction/v2/driving?origin=" + (orderList.get(i)).get("startlongLat") + "&destination=" + (orderList.get(i)).get("endlongLat") + "&ak=1w2xcsGWIGbPyhIngYF2uBK0";
                            String re = GpsJob.httpRequest(url, "GET", null);

                            re.trim();

                            JSONObject json = JSONObject.fromObject(re);

                            json = JSONObject.fromObject(json.get("result"));
                            // System.out.println(json);
                            JSONArray jsonArray = JSONArray.fromObject(json.get("routes"));

                            String distance = null; // 获取总路程
                            List<Map<String, String>> rsList = new ArrayList<Map<String, String>>();

                            JSONArray jsonArraySteps = new JSONArray();  //获取

                            for (int k = 0; k < jsonArray.size(); k++) {
                                JSONObject job = jsonArray.getJSONObject(k); // 遍历 jsonarray 数组，把每一个对象转成 json 对象
                                distance = job.get("distance") + "";
                                jsonArraySteps = JSONArray.fromObject(job.get("steps"));

                            }

                            StringBuffer longLatPlan = new StringBuffer();   //获取规划路线的经纬度合集
                            for (int l = 0; l < jsonArraySteps.size(); l++) {
                                JSONObject job = jsonArraySteps.getJSONObject(l); // 遍历 jsonarray 数组，把每一个对象转成 json 对象

                                longLatPlan.append(job.get("path")).append(";");


                            }
                            //获取的规划路线切割成数组
                            String[] sourceStrArray = (longLatPlan).toString().split(";");

                            for (int w = 0; w < compList.size(); w++) {
                                comp:
                                for (int q = 0; q < sourceStrArray.length; q++) {   //获取的线路规划经纬度集合做对比
                                    deviation = "1";
                                    if (sourceStrArray[q].length() > 5) {         //判断点数是否为空
                                        //只要有一个点满足就跳出,遍历判断下一个点
                                        if (GpsJob.getDistance(compList.get(w), sourceStrArray[q]) <= (Integer.parseInt(routeMap.get("routeDeviateDistance") + ""))) {
                                            deviation = "0";
                                            break comp;
                                        }

                                    }
                                }

                            }

                            StringBuffer stringBuffer = new StringBuffer();  //kafka的发送数据
                            stringBuffer.append("{");

                            stringBuffer.append("\"").append("orderCode\"").append(":\"")
                                    .append((orderList.get(i)).get("orderCode")).append("\"").append(",\"")
                                    .append("longLat\"").append(":\"");
                            stringBuffer.append(buf).append("\"").append(",\"").append("longLatPlan\"").append(":\"").append(longLatPlan).append("\"");
                            stringBuffer.append(",\"").append("deviation\"").append(":\"").append(deviation).append("\"");
                            stringBuffer.append("}");
                            System.out.println("stringBuffer=" + stringBuffer);


                            //发送数据至kafka
                            Map<String,Object> res = producerService.sendMesForTemplate(topic,stringBuffer, ifPartition, partitionNum);
                            //System.out.println("测试结果如下：===============");
                            String message = (String)res.get("message");
                            String code = (String)res.get("code");
                              System.out.println("code:"+code);
                              System.out.println("message:"+message);
                            log.info("code:"+code+"message:"+message);


                        }

                        /*
                        //发送数据至kafka
                        Map<String,Object> res = producerService.sendMesForTemplate(topic,buf, ifPartition, partitionNum);
                        //System.out.println("测试结果如下：===============");
                        String message = (String)res.get("message");
                        String code = (String)res.get("code");
                        System.out.println("code:"+code);
                        System.out.println("message:"+message);
                        log.info("code:"+code+"message:"+message);

                        */
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    // 请求http
    public static String httpRequest(String requestUrl, String requestMethod, String outputStr) {
        StringBuffer buffer = new StringBuffer();
        try {
            URL url = new URL(requestUrl);
            HttpURLConnection httpUrlConn = (HttpURLConnection) url.openConnection();

            httpUrlConn.setDoOutput(true);
            httpUrlConn.setDoInput(true);
            httpUrlConn.setUseCaches(false);

            httpUrlConn.setRequestMethod(requestMethod);

            if ("GET".equalsIgnoreCase(requestMethod)) {
                httpUrlConn.connect();
            }

            if (outputStr != null) {
                OutputStream outputStream = httpUrlConn.getOutputStream();

                outputStream.write(outputStr.getBytes("UTF-8"));
                outputStream.close();
            }

            InputStream inputStream = httpUrlConn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String str = null;
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }
            bufferedReader.close();
            inputStreamReader.close();

            inputStream.close();
            inputStream = null;
            httpUrlConn.disconnect();
        } catch (ConnectException ce) {
            log.error("Weixin server connection timed out.");
        } catch (Exception e) {
            log.error("https request error:{}", e);
        }
        return buffer.toString();
    }


    // 获取路径返回差值
    public static int getDistance(String point1, String point2) {
        double distance = 0.0;
        double R = 6378.137; // 地球半径


        String[] point1List = point1.split(",");
        String[] point2List = point2.split(",");

        double lat1 = 0.0;
        double lng1 = 0.0;
        double lat2 = 0.0;
        double lng2 = 0.0;
        for (int i = 0; i < point1List.length; i++) {

            lat1 = Double.parseDouble(point1List[0]) * Math.PI / 180.0;
            lng1 = Double.parseDouble(point1List[1]) * Math.PI / 180.0;
            lat2 = Double.parseDouble(point2List[0]) * Math.PI / 180.0;
            lng2 = Double.parseDouble(point2List[1]) * Math.PI / 180.0;

        }

        double d = Math.acos(Math.sin(lat1) * Math.sin(lat2) + Math.cos(lat1) * Math.cos(lat2) * Math.cos(lng2 - lng1)) * R;

        d = (double) Math.round(d * 100000) / 100000;
        distance += d;

        return (int) distance;
    }

}
