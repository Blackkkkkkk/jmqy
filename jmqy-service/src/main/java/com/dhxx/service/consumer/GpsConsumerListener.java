package com.dhxx.service.consumer;


import com.alibaba.dubbo.config.annotation.Reference;
import com.dhxx.common.util.JSONUtils;
import com.dhxx.common.util.StringUtils;


import com.dhxx.facade.entity.gprs.GprsCar;
import com.dhxx.facade.service.gprs.GprsFacade;

import com.google.gson.Gson;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.listener.BatchAcknowledgingMessageListener;
import org.springframework.kafka.support.Acknowledgment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * kafka监听器启动
 * 自动监听是否有消息需要消费
 * Created by zbz on 2018/1/10.
 */
public class GpsConsumerListener implements BatchAcknowledgingMessageListener<String, String> {

    @Reference(protocol = "dubbo")
    private GprsFacade gprsFacade;

    Integer count = 0;
    private final Logger log = LoggerFactory.getLogger(getClass());


    /**
     * 批量自动提交位移消费监听
     *
     * @param list
     */
    //@Override //2.0.0 kafka-spring
    public void onMessage(List<ConsumerRecord<String, String>> list) {
        //TODO bussiness
        System.out.println("批量自动提交位移消费监听");
    }

    /**
     * 批量手动提交位移消费监听
     *
     * @param list
     */
    @Override
    public void onMessage(List<ConsumerRecord<String, String>> list, Acknowledgment acknowledgment) {


     /*  GprsCar gprsCar = new GprsCar();
       gprsCar.setOrderCode("DH-201801311737000_01");
       // List<Map<String, Object>> listMap = gprsFacade.save(gprsCar);

        gprsFacade.save(gprsCar);*/
        log.info("=============kafkaConsumer开始消费=============");
        if (null != list && list.size() > 0) {
            //TODO deal with record
            for (ConsumerRecord<String, String> record : list) {
                String value = record.value();
                if (!StringUtils.isEmpty(value)) {


                    Gson gson = new Gson();
                    Map<String, Object> map = new HashMap<String, Object>();
                    map = gson.fromJson(value, map.getClass());

                    GprsCar gprsCar = new GprsCar();

                    gprsCar.setOrderCode(map.get("orderCode") + "");
                    gprsCar.setLongLat(map.get("longLat") + "");
                    gprsCar.setLongLatPlan(map.get("longLatPlan") + "");
                    gprsCar.setDeviation(map.get("deviation") + "");

                    List<Map<String, Object>> listMap = gprsFacade.find(gprsCar);
                    if (listMap.size() > 0) {

                        gprsFacade.update(gprsCar);

                    } else {
                        gprsFacade.save(gprsCar);
                    }
                }
            }

        }

        log.info("~~~~~~~~~~~~~kafkaConsumer消费结束~~~~~~~~~~~~~");

    }
}
