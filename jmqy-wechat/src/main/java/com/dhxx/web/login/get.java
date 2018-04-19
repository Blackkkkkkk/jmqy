package com.dhxx.web.login;


import com.dhxx.common.wechat.WechatUtils;

import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2017/11/16.
 */
public class get {
    public static void main(String[] args) {

        WechatUtils wechatUtils = new WechatUtils();

        String url = "http://219.130.135.53:8090/WXImage.ASPX?orderid=547&size=200&money=1";

        try {
            String a=get.getOneHtml(url);
            System.out.println(a);
            System.out.println(get.match(a,"img","src"));

        } catch (Exception e) {

        }

    }
    public static String getOneHtml(String urlString) throws Exception {
        InputStreamReader in = new InputStreamReader(new URL(urlString).openStream());
        // read contents into string buffer
        StringBuilder input = new StringBuilder();
        int ch;
        while ((ch = in.read()) != -1) input.append((char) ch);
        //System.out.println(input);
        return input.toString();
    }


    public static String match(String source, String element, String attr) {
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


