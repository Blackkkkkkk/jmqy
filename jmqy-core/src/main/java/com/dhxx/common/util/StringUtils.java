package com.dhxx.common.util;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 自定义字符串工具类
 *  
 * @author lvfq
 * @date 2017-08-19
 *
 */
public class StringUtils {
	
	/**
	 * a-z,0-9生成的获取随机字符串
	 *
	 * @param length
	 * @return
	 */
	public static String getRandomString(int length) { //length表示生成字符串的长度
	    String base = "abcdefghijklmnopqrstuvwxyz0123456789";   
	    Random random = new Random();   
	    StringBuffer sb = new StringBuffer();   
	    for (int i = 0; i < length; i++) {   
	        int number = random.nextInt(base.length());   
	        sb.append(base.charAt(number));   
	    }   
	    return sb.toString();   
	 }   

	
	/**
	 * 判断字符串是否为空，null、空字符串或空白字符串都判断为空
	 * @param s
	 * @return
	 */
	public static boolean isEmpty(String s){
		return s == null||s.trim().length() == 0;
	}

	/**
	 * 将字符串转换成utf-8编码
	 *
	 * @param str
	 * @return
	 */
	public static String toUtf(String str){
		try {
			if(!isEmpty(str)){
				str =  new String(str.getBytes("iso8859-1"), "utf-8");
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return str;
	}
	
	/**
	 * 字符串分割
	 *
	 * @param target 需要被分割的字符串
	 * @param separator  分割符号,如果为空，会抛出IllegalArgumentException
	 * @return
	 */
	public static List<Long> getListWithLong(String target,String separator){
		if(isEmpty(target)){
			return null;
		}
		if(isEmpty(separator)){
			throw new IllegalArgumentException();
		}
		String[] elements = target.split(separator);
		List<Long> list = new ArrayList<Long>(elements.length);
		for(String element:elements){
			Long e = Long.valueOf(element);
			list.add(e);
		}
		return list;
	}
	
	/**
	 * 字符串分割
	 *
	 * @param target 需要被分割的字符串
	 * @param separator  分割符号,如果为空，会抛出IllegalArgumentException
	 * @return
	 */
	public static Long[] getArrayWithLong(String target,String separator){
		if(isEmpty(target)){
			return null;
		}
		if(isEmpty(separator)){
			throw new IllegalArgumentException();
		}
		String[] elements = target.split(separator);
		Long[] array = new Long[elements.length];
		for(int i=0;i<elements.length;i++){
			String element = elements[i];
			Long e = Long.valueOf(element);
			array[i]= e;
		}
		return array;
	}
	
	/**
	 * 字符串分割
	 *
	 * @param target 需要被分割的字符串
	 * @param separator  分割符号,如果为空，会抛出IllegalArgumentException
	 * @return
	 */
	public static List<String> getListWithString(String target,String separator){
		if(isEmpty(target)){
			return null;
		}
		if(isEmpty(separator)){
			throw new IllegalArgumentException();
		}
		String[] elements = target.split(separator);
		List<String> list = new ArrayList<String>(elements.length);
		for(String element:elements){
			list.add(element);
		}
		return list;
	}
	
	/**
	 * 去除特殊字符
	 *
	 * @param target
	 * @return
	 */
	public static String replaceSpecialCharacter(String target){
		String regEx="[`~!@#$%^&*()+=|{}':;',//[//].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";   
        Pattern   p   =   Pattern.compile(regEx);      
        Matcher   m   =   p.matcher(target);      
        return   m.replaceAll("").trim(); 
	}
	
	/**
	 * 获取字符串的编码格式
	 *
	 * @param str
	 * @return
	 */
	public static String getEncoding(String str) {
		String[] encodes = new String[]{"ISO-8859-1","UTF-8","GB2312","GBK"};
		String encode = "UTF-8";
		for(String e:encodes){
			try {
				//如果已获取到匹配的字符串格式，那么直接跳出循环
				if (str.equals(new String(str.getBytes(e), e))) {
					encode = e;
					break;
				}
			} catch (UnsupportedEncodingException e1) {
				e1.printStackTrace();
			}
		}
		return encode;
    }

	/**
	 * 获取名称后缀
	 * @param name
	 * @return
	 */
	public static String getExt(String name){
		if(name == null || "".equals(name) || !name.contains("."))
			return "";
		return name.substring(name.lastIndexOf(".")+1);
	}
}
