package com.dhxx.service.task.refund;

import java.awt.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import com.dhxx.facade.entity.bill.Bill;
import com.dhxx.facade.entity.credit.Credit;
import com.dhxx.facade.entity.refund.Refund;
import com.dhxx.facade.service.bill.BillFacade;
import com.dhxx.facade.service.credit.CreditFacade;
import com.dhxx.facade.service.refund.RefundFacade;
import org.apache.commons.lang.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.support.TaskUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;


/**
 * 每隔20秒检测匹配中的订单是否支付超时
 * */
@Component
@Lazy(value=false)
public class refundCheck implements Runnable{

    private int initialDelay = 0;

    private int period = 300;

    private int shutdownTimeout = Integer.MAX_VALUE;

    private ScheduledExecutorService scheduledExecutorService;

    private Runnable task;

    @Autowired
    private RefundFacade refundFacade;

    @Autowired
    private BillFacade billFacade;

    @Autowired
    private CreditFacade creditFacade;


    private static Logger log = LoggerFactory.getLogger(refundCheck.class);

    @PostConstruct
    public void start() throws Exception {
        Validate.isTrue(period > 0);

        // 任何异常不会中断schedule执行, 由Spring TaskUtils的LOG_AND_SUPPRESS_ERROR_HANDLER進行处理
        task = TaskUtils.decorateTaskWithErrorHandler(this, null, true);

        // 创建单线程的SechdulerExecutor,并用guava的ThreadFactoryBuilder设定生成线程的名称
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor(new ThreadFactoryBuilder().setNameFormat(
                "TaskConverter-%1$d").build());

        // scheduleAtFixedRatefixRate() 固定任务两次启动之间的时间间隔.
        // scheduleAtFixedDelay() 固定任务结束后到下一次启动间的时间间隔.
        scheduledExecutorService.scheduleAtFixedRate(task, initialDelay, period, TimeUnit.SECONDS);

    }

    @PreDestroy
    public void stop() {
        //Threads.normalShutdown(scheduledExecutorService, shutdownTimeout, TimeUnit.SECONDS);
    }

    @Override
    public void run() {
        try {
            execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Transactional
    public void execute(){
        try {

            log.info("--------------更新退款订单表开始--------------");
            Refund refund = new Refund();
            refund.setUpdateStatus("0");
            refund.setRefundStatus("1");
            List<Refund> list = refundFacade.list(refund);
            if (list.size()>0){
                for (int i = 0; i <list.size() ; i++) {
                    Refund re = new Refund();
                    re = list.get(i);

                    if(re.getPayType()!=null && re.getPayType()==3) {   //记账方式
                        //添加账单流水
                        Bill bill = new Bill();
                        bill.setBillType("0");//包车方订单支付
                        bill.setMoney(re.getRefundRealityMoney() + "");//金额
                        bill.setTradeMode("5");//交易方式: 0账户余额，1信用额度，2微信，3支付宝，4银联 5申请退款
                        bill.setOrderCode(re.getOrderCode());//大订单编号
                        bill.setUserId(re.getUserId());//使用ID
                        billFacade.save(bill);


                        //更新对应信用额度

                        Credit credit = new Credit();
                        //	credit.setUserId(shiroUser.id);
                        credit.setCompanyCode(re.getCompanyCode());
                        credit = (Credit) creditFacade.findOne(credit);

                        credit.setStockCredit(credit.getStockCredit() + re.getRefundRealityMoney());
                        credit.setConsumeCredit(credit.getConsumeCredit() - re.getRefundRealityMoney());
                        creditFacade.update(credit);

                        //更改退款表的状态
                        Refund updateRe = new Refund();

                        updateRe.setOrderCode(re.getOrderCode());
                        updateRe.setUpdateStatus("1");
                        refundFacade.update(updateRe);
                    }

                }

                log.info("--------------更新退款订单表结束--------------");
            }
        } catch (Exception e) {

            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();

           // throw new RuntimeException();
        }
    }

}
