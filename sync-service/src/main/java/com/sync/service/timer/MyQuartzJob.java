package com.sync.service.timer;

import com.alibaba.dubbo.rpc.RpcException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by zbz on 2017/12/28.
 * 定时器管理类
 */
@DisallowConcurrentExecution
public class MyQuartzJob extends QuartzJobBean {
    private static Log log = LogFactory.getLog(MyQuartzJob.class);
    // 计划任务所在类
    private String targetObject;
    // 具体需要执行的计划任务
    private String targetMethod;
    private ApplicationContext ctx;

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        try {
            Object otargetObject = ctx.getBean(targetObject);
            Method m = null;
            try {
                m = otargetObject.getClass().getMethod(targetMethod);
                m.invoke(otargetObject);
            } catch (NoSuchMethodException e) {
                log.error(e.getMessage());
            }
        } catch (Exception e) {
            log.error("定时器异常", e);
            e.printStackTrace();
            if (e instanceof InvocationTargetException) {
                InvocationTargetException ie = (InvocationTargetException) e;
                if (ie.getTargetException() != null) {
                    Object t = ie.getTargetException();
                    ((Exception) t).printStackTrace();
                    if (t instanceof RpcException) {
                        RpcException r = (RpcException) t;
                        throw new JobExecutionException(r);
                    } else {
                        throw new JobExecutionException(e);
                    }
                }
            }
            throw new JobExecutionException(e);
        }
    }

    public void setApplicationContext(ApplicationContext applicationContext) {
        this.ctx = applicationContext;
    }

    public void setTargetObject(String targetObject) {
        this.targetObject = targetObject;
    }

    public void setTargetMethod(String targetMethod) {
        this.targetMethod = targetMethod;
    }
}
