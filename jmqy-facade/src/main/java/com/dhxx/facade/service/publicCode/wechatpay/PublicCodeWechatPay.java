package com.dhxx.facade.service.publicCode.wechatpay;


import com.dhxx.facade.entity.order.Order;

public interface PublicCodeWechatPay {

    String NewPayResult(Order order,Long userid,Double paymoney,String phone);

    void sendRemind(Long userid,Order order);
}
