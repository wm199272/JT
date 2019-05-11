package com.jt.quartz;

import java.util.Calendar;
import java.util.Date;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.jt.mapper.OrderMapper;
import com.jt.pojo.Order;
import com.jt.service.OrderService;

//准备订单定时任务
@Component
public class OrderQuartz extends QuartzJobBean{
	@Autowired
	private OrderMapper orderMapper;

	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		Calendar calender = Calendar.getInstance();
		calender.add(Calendar.MINUTE, -15);
		Date timeout = calender.getTime();
		Order order = new Order();
		order.setStatus(6).setUpdated(new Date());
		UpdateWrapper<Order> updateWrapper = new UpdateWrapper<>();
		updateWrapper.eq("status", 1).lt("created", timeout);
		orderMapper.update(order, updateWrapper);
		
		System.out.println("定时任务执行成功!");
	}
}
