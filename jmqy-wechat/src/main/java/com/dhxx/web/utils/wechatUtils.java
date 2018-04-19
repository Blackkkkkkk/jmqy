package com.dhxx.web.utils;



import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



/**
 * Created by Administrator on 2017/11/16.
 */
public class wechatUtils {

    public  String getOneHtml(String urlString) throws Exception {
        InputStreamReader in = new InputStreamReader(new URL(urlString).openStream());
        // read contents into string buffer
        StringBuilder input = new StringBuilder();
        int ch;
        while ((ch = in.read()) != -1) input.append((char) ch);
        //System.out.println(input);
        return input.toString();
    }




    public  String match(String source, String element, String attr) {
        String a= null;
        List<String> result = new ArrayList<String>();
        String reg = "<" + element + "[^<>]*?\\s" + attr + "=['\"]?(.*?)['\"]?\\s.*?>";
        Matcher m = Pattern.compile(reg).matcher(source);
        while (m.find()) {
            a = m.group(1);

        }
        return a;
    }




}
