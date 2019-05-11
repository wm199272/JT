package com.jt.aop;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.druid.util.StringUtils;
import com.jt.anno.CaCheAnnotation;
import com.jt.service.RedisService;
import com.jt.util.ObjectMapperUtil;

import redis.clients.jedis.JedisCluster;

@Component
@Aspect
public class CacheAspect {

//	@Autowired(required = false)
//	private ShardedJedis jedis;
	
	/*
	 * @Autowired(required = false) private
	 *  RedisService jedis;
	 */
	@Autowired(required = false)
	private JedisCluster jedis;

	@Around(value="@annotation(cacheAnno)")
	public Object around(ProceedingJoinPoint joinPoint,CaCheAnnotation cacheAnno) {
		//获取key值
		String key = getKey(joinPoint,cacheAnno);
		Object object = getObject(joinPoint,cacheAnno,key);
		return object;
	}

	private Object getObject(ProceedingJoinPoint joinPoint, CaCheAnnotation cacheAnno,String key) {
		Object object = null;
		switch (cacheAnno.cacheType()) {
		case FIND:
			object = findCache(joinPoint,cacheAnno,key);
			break;
		case UPDATE:
			object = updateCache(joinPoint,key);
			break;
		}
		return object;
	}

	//类名.方法名.参数名称.值
	private String getKey(ProceedingJoinPoint joinPoint,CaCheAnnotation cacheAnno) {
		String targetClassName = joinPoint.getSignature().getDeclaringTypeName();
		String methodName = joinPoint.getSignature().getName();
		//转化为方法对象
		MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
		String[] paramNames = methodSignature.getParameterNames();
		String paramName = paramNames[cacheAnno.index()];
		Object arg = joinPoint.getArgs()[cacheAnno.index()];
		String key = targetClassName+"."+methodName+"."+paramName+"."+arg;
		return key;
	}
	
	private Object findCache(ProceedingJoinPoint joinPoint, CaCheAnnotation cacheAnno, String key) {
		Object object = null;
		String result = jedis.get(key);	//根据key查询缓存信息
		try {
			if(StringUtils.isEmpty(result)) { //表示缓存中没有数据,则执行业务层方法
				object = joinPoint.proceed();
				String json = ObjectMapperUtil.toJSON(object);	//将数据转化为json串
				jedis.set(key, json);	//将数据保存到redis中
				System.out.println("AOP查询业务层获取信息");
			}else {		//表示缓存中有数据,可以直接返回数据
				MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
				//获取目标方法返回值类型
				Class<?> returnClass = methodSignature.getReturnType();
				object = ObjectMapperUtil.toObject(result, returnClass);
				System.out.println("AOP查询缓存");
			}
		} catch (Throwable e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
		
		return object;
	}
	
	private Object updateCache(ProceedingJoinPoint joinPoint, String key) {
		Object object = null;
		try {
			jedis.del(key);	//如果是更新操作,则直接删除缓存
			joinPoint.proceed();
		} catch (Throwable e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
		return object;
	}
}
