package com.dropit.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

import static com.dropit.utils.Constants.ASYNC_TASK_EXECUTOR_BEAN_NAME;

@Configuration
@EnableAsync
@Slf4j
public class AsyncConfiguration {

	@Bean(name = ASYNC_TASK_EXECUTOR_BEAN_NAME)
	public Executor taskExecutor(ApplicationConfiguration applicationConfiguration) {
		final ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(applicationConfiguration.getAsyncPoolSize());
		executor.setMaxPoolSize(applicationConfiguration.getMaxPoolSize());
		executor.setQueueCapacity(applicationConfiguration.getQueueSize());
		executor.setThreadNamePrefix("ExecuteCommandsThread-");
		executor.initialize();
		return executor;
	}
}
