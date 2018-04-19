package com.dhxx.common.util;

import javax.xml.crypto.Data;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class DataUtils {

	private static Random random = new Random();

	public static int nextInt(int index) {
		return random.nextInt(index);
	}
	
	public static String getYesterDay(){
		Calendar   cal   =   Calendar.getInstance();
		cal.add(Calendar.DATE,   -1);
		String yesterday = new SimpleDateFormat( "yyyy-MM-dd ").format(cal.getTime());
		return yesterday;
	}
	
	public static String getCurrentTime(){
		Calendar   cal   =   Calendar.getInstance();
		cal.add(Calendar.DATE, 0);
		String time = new SimpleDateFormat( "yyyy-MM-dd").format(cal.getTime());
		return time;
	}
	
	public static String getCurrentTimeStr(){
		Calendar   cal   =   Calendar.getInstance();
		cal.add(Calendar.DATE, 0);
		String time = new SimpleDateFormat( "yyyyMMddHHmmsss").format(cal.getTime());
		return time;
	}
	
	public static String getNextYearTime(){
		Calendar   cal   =   Calendar.getInstance();
		cal.add(Calendar.YEAR, 1);
		String time = new SimpleDateFormat( "yyyy-MM-dd ").format(cal.getTime());
		return time;
	}
	
	public static Date getByDay(Date d, int day){
		Calendar   cal   =   Calendar.getInstance();
		cal.setTime(d);
		cal.add(Calendar.DATE,   day);
		return cal.getTime();
	}
	
	public static Date getByHour(Date d, int hour){
		Calendar   cal   =   Calendar.getInstance();
		cal.setTime(d);
		cal.add(Calendar.HOUR_OF_DAY,   hour);
		return cal.getTime();
	}
	
	public static Date getByMinute(Date d, int minute){
		Calendar   cal   =   Calendar.getInstance();
		cal.setTime(d);
		cal.add(Calendar.MINUTE,   minute);
		return cal.getTime();
	}
	
	public static Date format(Date d, String pattern){
		try {
			return new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(new SimpleDateFormat(pattern).format(d));
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String formatStr(Date d, String pattern){
		return new SimpleDateFormat(pattern).format(d);
	}
	
}
