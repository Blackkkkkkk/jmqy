package com.dhxx.common.constant;

import java.util.Calendar;
import java.util.Date;

public class test {
    public static void main(String[] args) {
      String a = "小车,";
      String b[] = a.split(",");
        System.out.println(b.length);
        for (int i = 0; i <b.length ; i++) {
            System.out.println(i+"="+b[i]);
        }
    }
}
