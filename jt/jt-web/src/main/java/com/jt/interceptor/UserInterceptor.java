package com.jt.interceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.jt.pojo.User;
import com.jt.util.ObjectMapperUtil;
import com.jt.util.UserThreadLocal;

import redis.clients.jedis.JedisCluster;
@Component
public class UserInterceptor implements HandlerInterceptor{
	@Autowired
	private JedisCluster jedisCluster;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
			String token=null;
			Cookie[] cookies = request.getCookies();
			for (Cookie cookie : cookies) {
				if ("JT_TICKET".equals(cookie.getName())) {
					token= cookie.getValue();
					break;
				}
			}
			if (!StringUtils.isEmpty(token)) {
				//判断redis是否有数据
				String userJSON = jedisCluster.get(token);
				if (!StringUtils.isEmpty(userJSON)) {
					User user = ObjectMapperUtil.toObject(userJSON, User.class);
					UserThreadLocal.set(user);
				return true;
				}
			}
			response.sendRedirect("/user/login.html");
		return false;
	}
	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
	}
	
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		UserThreadLocal.remove();//删除线程数据
		HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
	}
	
}
