package com.dhxx.service.task.accept;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.apache.commons.lang.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.support.TaskUtils;
import org.springframework.stereotype.Component;

import com.dhxx.service.biz.order.OrderBiz;
import com.google.common.util.concurrent.ThreadFactoryBuilder;


/**
 * 每隔60秒检测待接受的订单是否被运输方接收超时
 * */
@Component
@Lazy(value=false)
public class TimerCheckAccept implements Runnable{
	
	private int initialDelay = 0;

	private int period = 60;
	
	private int shutdownTimeout = Integer.MAX_VALUE;

	private ScheduledExecutorService scheduledExecutorService;
	
	private Runnable task;
	
	@Autowired
	private OrderBiz orderBiz;
	
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
	
	public void execute(){
		try {
			orderBiz.checkAccept();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
