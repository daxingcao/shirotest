package com.caodaxing.core.aop;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.caodaxing.core.annotation.LogOut;

@Aspect
@Component
public class LogOutAdvice {
	
	@Before("within(com.caodaxing..*) && @annotation(logOut)")
	public void beforeOutLogByMetched(JoinPoint jp,LogOut logOut) {
		
		System.out.println(jp.getSignature().getName());
		Arrays.stream(jp.getArgs()).map(arg -> {
			String jsonString = JSONObject.toJSONString(arg);
			System.out.println(jsonString);
			return jsonString;
		}).toArray(String[]::new);
		System.out.println(logOut.value());
		
	}

}
