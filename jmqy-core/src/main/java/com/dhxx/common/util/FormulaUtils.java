package com.dhxx.common.util;

import java.text.ParseException;
import java.util.Date;

public class FormulaUtils {
	
	/**
	 * 计算地球上任意两点(经纬度)距离
	 * 
	 * @param long1
	 *            第一点经度
	 * @param lat1
	 *            第一点纬度
	 * @param long2
	 *            第二点经度
	 * @param lat2
	 *            第二点纬度
	 * @return 返回距离 单位：米
	 */
	public static double Distance (double long1, double lat1, double long2,double lat2) throws Exception{
		double a, b, R;
		R = 6378137; // 地球半径
		lat1 = lat1 * Math.PI / 180.0;
		lat2 = lat2 * Math.PI / 180.0;
		a = lat1 - lat2;
		b = (long1 - long2) * Math.PI / 180.0;
		double d;
		double sa2, sb2;
		sa2 = Math.sin(a / 2.0);
		sb2 = Math.sin(b / 2.0);
		d = 2* R* Math.asin(Math.sqrt(sa2 * sa2 + Math.cos(lat1)* Math.cos(lat2) * sb2 * sb2));
		return d;
	}
	
	public static Double getLength(double lx1, double ly1, double lx2,double ly2, double px, double py) throws Exception {
		Double length = null;
		double b = Distance(lx1, ly1, px, py);
		double c = Distance(lx2, ly2, px, py);
		double a = Distance(lx1, ly1, lx2, ly2);

		if (c + b == a) {// 点在线段上
			length = (double) 0;
		} else if (c * c >= a * a + b * b) { // 组成直角三角形或钝角三角形，投影在point1延长线上，
			length = b;
		} else if (b * b >= a * a + c * c) {// 组成直角三角形或钝角三角形，投影在point2延长线上，
			length = c;
		} else {
			// 组成锐角三角形，则求三角形的高
			double p = (a + b + c) / 2;// 半周长
			double s = Math.sqrt(p * (p - a) * (p - b) * (p - c));// 海伦公式求面积
			length = 2 * s / a;// 返回点到线的距离（利用三角形面积公式求高）
		}

		return length;
	}
	
	//递归实现计算两个整数最大公约数
	public static int getGCD(int m,int n){
		 return n==0?m:getGCD(n,m%n);
	}

	  public static String dateDiff(Date startTime, Date endTime) throws ParseException { 
	        long nd = 1000 * 24 * 60 * 60;// 一天的毫秒数  
	        long nh = 1000 * 60 * 60;// 一小时的毫秒数  
	        long nm = 1000 * 60;// 一分钟的毫秒数  
	        long ns = 1000;// 一秒钟的毫秒数  
	        long diff;  
	        long day = 0;  
	        long hour = 0;  
	        long min = 0;  
	        long sec = 0;  
	        // 获得两个时间的毫秒时间差异  
	        diff = endTime.getTime() - startTime.getTime();  
	        day = diff / nd;// 计算差多少天  
	        hour = diff % nd / nh + day * 24;// 计算差多少小时  
	        min = diff % nd % nh / nm + day * 24 * 60;// 计算差多少分钟  
	        sec = diff % nd % nh % nm / ns;// 计算差多少秒  
	        String hourStr = hour - day * 24 + "";
	        if(hourStr.length() < 2)
	        	hourStr = "0" +hourStr;
	        String minStr = min - day * 24 * 60 + "";
	        if(minStr.length() < 2)
	        	minStr = "0" +minStr;
	        String secStr = sec + "";
	        if(secStr.length() < 2)
	        	secStr = "0" +secStr;
	        // 输出结果  
	        return hourStr + ":" +minStr+ ":" + secStr;  
	        }  
}
