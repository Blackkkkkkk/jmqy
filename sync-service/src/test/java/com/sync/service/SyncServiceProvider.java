package com.sync.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by zbz on 2017/12/29.
 */
public class SyncServiceProvider {

    private static Logger log = LoggerFactory.getLogger(SyncServiceProvider.class);

    @SuppressWarnings("resource")
    public static void main(String[] args) {

        try {
            System.setProperty("dubbo.application.logger","slf4j");
            ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring/spring-context.xml");
            log.debug("== DubboProvider context start success");
            context.start();
        } catch (Exception e) {
            log.error("== DubboProvider context start error:",e);
        }

        synchronized (SyncServiceProvider.class) {
            while (true) {
                try {
                    SyncServiceProvider.class.wait();
                } catch (InterruptedException e) {
                    log.error("== synchronized error:",e);
                }
            }
        }
    }

}