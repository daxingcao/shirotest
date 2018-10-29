package com.caodaxing.core.job;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class TestJob {
	
	private int num = 1;
	
	@Scheduled(cron="0-30/5 42 13 * * ?")
	public void queryLoginInfo() {
		System.out.println("第"+num+"调用...");
		num++;
	}

}
