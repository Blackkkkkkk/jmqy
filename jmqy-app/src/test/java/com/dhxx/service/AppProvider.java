package com.dhxx.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 
 * @描述: 启动Dubbo服务用的MainClass.
 */
public class AppProvider {
    private static Logger log = LoggerFactory.getLogger(AppProvider.class);

    @SuppressWarnings("resource")
    public static void main(String[] args) {
        try {
            System.setProperty("dubbo.application.logger", "slf4j");
            ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring/spring-context.xml");
            context.start();
        } catch (Exception e) {
            log.error("== DubboProvider context start error:", e);
        }

        synchronized (AppProvider.class) {
            while (true) {
                try {
                    AppProvider.class.wait();
                } catch (InterruptedException e) {
                    log.error("== synchronized error:", e);
                }
            }
        }
    }
}