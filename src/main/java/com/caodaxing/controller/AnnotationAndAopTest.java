package com.caodaxing.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.caodaxing.core.annotation.LogOut;
import com.caodaxing.service.LoginUserService;

@Controller
public class AnnotationAndAopTest {
	
	@Autowired
	private LoginUserService loginUserService;
	
	@LogOut("测试日志输出自定义注解!")
	@RequestMapping("/annotation/test1")
	public void logOutAnnotationTest(String name,Long loginId) {
		loginUserService.selectByPrimaryKey(loginId);
		System.out.println("hello world!");
	}

}
