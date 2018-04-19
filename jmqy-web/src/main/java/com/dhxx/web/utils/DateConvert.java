package com.dhxx.web.utils;

import org.springframework.core.convert.converter.Converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


//自定义格式转换器 String  to date

public class DateConvert implements Converter<String, Date> {

    @Override
    public Date convert(String stringDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            if(stringDate!=null && stringDate !="") {
                return sdf.parse(stringDate);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
