package com.sync.service.producer;

import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.support.ProducerListener;

/**
 * kafkaProducer监听器，在producer配置文件中开启
 */
public class KafkaProducerListener implements ProducerListener {

    protected final Logger log = LoggerFactory.getLogger(KafkaProducerListener.class);

    /**
     * 发送消息成功后调用
     * @param topic
     * @param partition
     * @param key
     * @param value
     * @param recordMetadata
     */
    @Override
    public void onSuccess(String topic, Integer partition, Object key, Object value, RecordMetadata recordMetadata) {
        log.info("===========>>>>>>>>>>kafka发送"+topic+"数据成功!");
    }

    /**
     * 发送消息错误后调用
     * @param topic
     * @param partition
     * @param key
     * @param value
     * @param e
     */
    @Override
    public void onError(String topic, Integer partition, Object key, Object value, Exception e) {
        log.error("===========>>>>>>>>>>kafka发送数据失败! 数据如下：");
        log.error("topic:"+topic);
        log.error("partition:"+partition);
        log.error("key:"+key);
        log.error("value:"+value);
        log.error("Exception:"+e.getMessage());

    }

    /**
     * 方法返回值代表是否启动kafkaProducer监听器
     */
    @Override
    public boolean isInterestedInSuccess() {
        log.info("kafka producer监听器启动！");
        return true;
    }
}
