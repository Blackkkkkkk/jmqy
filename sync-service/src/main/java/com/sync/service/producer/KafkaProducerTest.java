package com.sync.service.producer;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Map;

public class KafkaProducerTest {

    public static void main(String[] args) {

        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:spring/spring-context.xml");
        ProducerService kafkaProducer = (ProducerService) ctx.getBean("producerService");
//        NativeDirectoryProducerService kafkaProducer = new NativeDirectoryProducerService();
        String topic = "xiaoqiang";
        String value = "{\"rownum\":625001,\"registrationOn\":\"粤N65269\",\"registrationNoColor\":\"黄色\",\"gpsTime\":1515687016000,\"latitude\":23.158619,\"longitude\":114.145485,\"speed\":98,\"dirction\":271,\"altitude\":0,\"mileage\":2824000,\"driverName\":\"\",\"driverIDCardNO\":\"\",\"valid\":true,\"state\":\"AAAAAAABYgA=\",\"recordTime\":1515686697493,\"protocolCode\":\"2\"}";
        String ifPartition = "0";
        Integer partitionNum = 1;
        Map<String,Object> res = kafkaProducer.sendMesForTemplate(topic, value, ifPartition, partitionNum);

        System.out.println("测试结果如下：===============");
        String message = (String)res.get("message");
        String code = (String)res.get("code");

        System.out.println("code:"+code);
        System.out.println("message:"+message);
    }
}