package com.dhxx.service.consumer;

import com.alibaba.fastjson.JSON;
import com.dhxx.common.util.Circle;
import com.dhxx.common.util.JSONUtils;
import com.dhxx.common.util.Point2D;
import com.dhxx.common.util.IsPtInPoly;
import com.dhxx.common.wechat.WechatUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;


public class ExcuteJs {


    public static void main(String[] args) throws ScriptException,
            FileNotFoundException, NoSuchMethodException {

      /*  Point2D point = new Point2D(116.404072, 39.916605);
        Point2D centerPoint = new Point2D(116.404172, 39.916605);

        Circle c = new Circle();

        c.setCC(centerPoint);
        c.setR(0.0056);
        String s = IsPtInPoly.distencePC(point,c);
        System.out.println("点是否在圆内："+s);

*/

        String a = "1";
        for (int w = 0; w < 6; w++) {
            a ="1";
            comp:
            for (int q = 0; q < 10; q++) {   //获取的线路规划经纬度集合做对比

                if (q > w) {         //判断点数是否为空

                    a = "2";
                    break comp;
                }

            }
        }
        System.out.println(a);

        // 获取两点的距离

   /*  Map<String,Double> map = new HashMap<String, Double>();
       map.put("lat",112.68565);
       map.put("lng",22.369115833333332);


        Map<String,Double> map1 = new HashMap<String, Double>();
        map1.put("lat",113.072957);
        map1.put("lng",22.637020);

       String a = IsPtInPoly.getDistance1(map,map1);
        System.out.println(a);
*/
      /*  WechatUtils wechatUtils = new WechatUtils();
        //请求订单号的规划路径的经纬度集合
        String url = "http://api.map.baidu.com/direction/v2/driving?origin=22.636572,113.072548&destination=22.566654,113.109846&ak=1w2xcsGWIGbPyhIngYF2uBK0";
        String re = wechatUtils.httpRequest(url, "GET", null);

        re.trim();

        JSONObject json = JSONObject.fromObject(re);

        json = JSONObject.fromObject(json.get("result"));
        JSONArray jsonArray = JSONArray.fromObject(json.get("routes"));

        String distance = null; // 获取总路程
        List<Map<String, String>> rsList = new ArrayList<Map<String, String>>();

        JSONArray jsonArraySteps = new JSONArray();  //获取

        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject job = jsonArray.getJSONObject(i); // 遍历 jsonarray 数组，把每一个对象转成 json 对象
            distance = job.get("distance") + "";
            jsonArraySteps = JSONArray.fromObject(job.get("steps"));

        }

        StringBuffer buf = new StringBuffer();   //获取规划路线的经纬度合集
        for (int i = 0; i < jsonArraySteps.size(); i++) {
            JSONObject job = jsonArraySteps.getJSONObject(i); // 遍历 jsonarray 数组，把每一个对象转成 json 对象

            buf.append(job.get("path")).append(";");


        }


        System.out.println(buf);

*/

        // 执行JS文件
       /* ScriptEngineManager engineManager = new ScriptEngineManager();
        ScriptEngine engine = engineManager.getEngineByName("JavaScript"); //得到脚本引擎
        engine.eval(new java.io.FileReader("F:\\jmqy\\jmqy-service\\src\\main\\resources\\JS\\testJs.js"));
        Invocable inv = (Invocable)engine;
        Object a = inv.invokeFunction(" GeoUtils.isPointInCircle", "北京","上海" );

        System.out.println(a);


*/

    }


}
