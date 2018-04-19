package com.sync.common.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by zbz on 2017/11/24.
 * 时间工具类
 */
public class DateUtil {

    private DateUtil() {}

    public static  String findYyyymmdd(){
        return new SimpleDateFormat("yyyyMMdd").format(new Date());
    }

    public static  String findymd(){
        return new SimpleDateFormat("yyyy-MM-dd").format(new Date());
    }

    public static  String findSatarToday(){
        return new SimpleDateFormat("yyyy-MM-dd").format(new Date())+" 00:00:00";
    }

    public static  String findEndToday(){
        return new SimpleDateFormat("yyyy-MM-dd").format(new Date())+" 23:59:59";
    }

}
