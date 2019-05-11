package com.jt.redis;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class HelloAspect {
	/**
	 *  切入点表达式的写法:
	 * 	1.within("包名.类名")   粗粒度
	 * 	匹配的类中的全部方法都会执行通知方法.
	 * 	
	 *  2.excution("返回值类型 包名.类名.方法名(参数列表)")
	 *  语法:
	 *   execution(* com.jt.controller.*.add*(..))
	 *   匹配返回值类型为任意, 指定包下(一级包)下的add开头的方法
	 *   execution(* com.jt.controller..*.*(..))
	 * 匹配返回值类型任意. controller包下的任意包下的任意方法
	 * 	
	 */
	@Before("within(com.jt.controller.HelloController)")
	public void before(JoinPoint joinPoint) {
		System.out.println("我是前置通知");
		
		String methodName = joinPoint.getSignature().getName();
		System.out.println("获取目标方法名称"+methodName);
		
		Class<?> target = joinPoint.getTarget().getClass();
		System.out.println("获取目标方法类型"+target);
	}
}
