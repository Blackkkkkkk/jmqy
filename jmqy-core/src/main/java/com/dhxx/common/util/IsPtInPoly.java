package com.dhxx.common.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * java判断某个点是否在所画范围内(多边形【isPtInPoly】/圆形【distencePC】)
 * @param point 检测点
 * @param pts   多边形的顶点
 * @return      点在多边形内返回true,否则返回false
 * @author      ardo
 */
public class IsPtInPoly {

    /**
     * 判断点是否在多边形内
     * @param point 检测点
     * @param pts   多边形的顶点
     * @return      点在多边形内返回true,否则返回false
     */
    public static boolean isPtInPoly(Point2D point, List<Point2D> pts){

        int N = pts.size();
        boolean boundOrVertex = true; //如果点位于多边形的顶点或边上，也算做点在多边形内，直接返回true
        int intersectCount = 0;//cross points count of x
        double precision = 2e-10; //浮点类型计算时候与0比较时候的容差
        Point2D p1, p2;//neighbour bound vertices
        Point2D p = point; //当前点

        p1 = pts.get(0);//left vertex
        for(int i = 1; i <= N; ++i){//check all rays
            if(p.equals(p1)){
                return boundOrVertex;//p is an vertex
            }

            p2 = pts.get(i % N);//right vertex
            if(p.x < Math.min(p1.x, p2.x) || p.x > Math.max(p1.x, p2.x)){//ray is outside of our interests
                p1 = p2;
                continue;//next ray left point
            }

            if(p.x > Math.min(p1.x, p2.x) && p.x < Math.max(p1.x, p2.x)){//ray is crossing over by the algorithm (common part of)
                if(p.y <= Math.max(p1.y, p2.y)){//x is before of ray
                    if(p1.x == p2.x && p.y >= Math.min(p1.y, p2.y)){//overlies on a horizontal ray
                        return boundOrVertex;
                    }

                    if(p1.y == p2.y){//ray is vertical
                        if(p1.y == p.y){//overlies on a vertical ray
                            return boundOrVertex;
                        }else{//before ray
                            ++intersectCount;
                        }
                    }else{//cross point on the left side
                        double xinters = (p.x - p1.x) * (p2.y - p1.y) / (p2.x - p1.x) + p1.y;//cross point of y
                        if(Math.abs(p.y - xinters) < precision){//overlies on a ray
                            return boundOrVertex;
                        }

                        if(p.y < xinters){//before ray
                            ++intersectCount;
                        }
                    }
                }
            }else{//special case when ray is crossing through the vertex
                if(p.x == p2.x && p.y <= p2.y){//p crossing over p2
                    Point2D p3 = pts.get((i+1) % N); //next vertex
                    if(p.x >= Math.min(p1.x, p3.x) && p.x <= Math.max(p1.x, p3.x)){//p.x lies between p1.x & p3.x
                        ++intersectCount;
                    }else{
                        intersectCount += 2;
                    }
                }
            }
            p1 = p2;//next ray left point
        }

        if(intersectCount % 2 == 0){//偶数在多边形外
            return false;
        } else { //奇数在多边形内
            return true;
        }

    }

    /**
     * 判断是否在圆形内
     * @param p
     * @param c
     * @return
     */
    public static String distencePC(Point2D p,Circle c){//判断点与圆心之间的距离和圆半径的关系
        String s ;
        double d2 = Math.hypot( (p.getX() - c.getCC().getX() ), (p.getY() - c.getCC().getY()) );
        System.out.println("d2=="+d2);
        double r = c.getR();
        if(d2 > r){
            s = "圆外";
        }else if(d2 < r){
            s = "圆内";
        }else{
            s = "圆上";
        }
        return s;
    }

    /**
     * 将度转化为弧度
     *
     * @param {degree}
     *            Number 度
     * @returns {Number} 弧度
     */
    public static  Double degreeToRad(Double degree){
        return Math.PI * degree / 180;
    }

    /**
     * 将v值限定在a,b之间，经度使用
     */
    public  static  Double getLoop(Double v ,Double a, Double b){
        while (v > b) {
            v -= b - a;
        }
        while (v < a) {
            v += b - a;
        }
        return v;
    }

    /**
     * 将v值限定在a,b之间，纬度使用
     */
    public  static  Double getRange(Double v ,Double a, Double b){
        if (a != null) {
            v = Math.max(v, a);
        }
        if (b != null) {
            v = Math.min(v, b);
        }
        return v;
    }

/*
    public  static  Double getDistan(Map<String,String> point1, Map<String,String> point2){

       Double poi1lng = getLoop(Double.parseDouble(""),-180.0,180.0);

    /*    point1.lng = _getLoop(point1.lng, -180, 180);
        point1.lat = _getRange(point1.lat, -74, 74);
        point2.lng = _getLoop(point2.lng, -180, 180);
        point2.lat = _getRange(point2.lat, -74, 74);
*/
  //  }



    public static String getDistance1(Map<String,Double>point1,Map<String,Double>point2){
        double distance = 0.0;
        double R = 6378.137; // 地球半径


        double lat1 = point1.get("lat") * Math.PI / 180.0;
        double lng1 = point1.get("lng") * Math.PI / 180.0;
        double lat2 = point2.get("lat") * Math.PI / 180.0;
        double lng2 = point2.get("lng") * Math.PI / 180.0;
        double d =  Math.acos(Math.sin(lat1)*Math.sin(lat2)+Math.cos(lat1)*Math.cos(lat2)*Math.cos(lng2-lng1))*R;

        d = (double)Math.round(d*100000)/100000;
        distance += d;

        return String.valueOf(distance);
    }




    public String getDistance(List<double[]> latLngs) {
        double distance = 0.0;
        if (latLngs.size() >= 2) {
            for (int i = 1; i < latLngs.size(); i++) {
                double R = 6378.137; // 地球半径
                double lat1 = latLngs.get(i-1)[1] * Math.PI / 180.0;
                double lng1 = latLngs.get(i-1)[0] * Math.PI / 180.0;
                double lat2 = latLngs.get(i)[1] * Math.PI / 180.0;
                double lng2 = latLngs.get(i)[0] * Math.PI / 180.0;

                double d =  Math.acos(Math.sin(lat1)*Math.sin(lat2)+Math.cos(lat1)*Math.cos(lat2)*Math.cos(lng2-lng1))*R;
                d = (double)Math.round(d*100000)/100000;
                distance += d;
            }
        }
        distance = (double)Math.round(distance*100000)/100000;
        return String.valueOf(distance);
    }




    public static void main(String[] args) {

        Point2D point = new Point2D(116.404072, 39.916605);

        // 测试一个点是否在多边形内
        List<Point2D> pts = new ArrayList<Point2D>();
        pts.add(new Point2D(116.395, 39.910));
        pts.add(new Point2D(116.394, 39.914));
        pts.add(new Point2D(116.403, 39.920));
        pts.add(new Point2D(116.402, 39.914));
        pts.add(new Point2D(116.410, 39.913));

        if(isPtInPoly(point, pts)){
            System.out.println("点在多边形内");
        }else{
            System.out.println("点在多边形外");
        }

        // 测试一个点是否在圆形内
        Point2D centerPoint = new Point2D(118.404173, 39.916605);
        Circle c = new Circle();
        c.setCC(centerPoint);
        c.setR(1000);
        String s = distencePC(point,c);
        System.out.println("点是否在圆内："+s);
    }

}