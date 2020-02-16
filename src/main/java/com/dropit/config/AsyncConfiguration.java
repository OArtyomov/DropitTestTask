package com.dropit.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync
@Slf4j
public class AsyncConfiguration {

	@Bean(name = "taskExecutor")
	public Executor taskExecutor() {
		final ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(100);
		executor.setMaxPoolSize(100);
		executor.setQueueCapacity(100);
		executor.setThreadNamePrefix("ExecuteCommandsThread-");
		executor.initialize();
		return executor;
	}
}
