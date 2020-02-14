package com.dropit.config;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.Lifecycle;
import org.testcontainers.containers.GenericContainer;

@AllArgsConstructor
@Slf4j
public class LifecycleHandler implements Lifecycle {

	private final GenericContainer container;

	@Override
	public void start() {

	}

	@Override
	public void stop() {
		log.info("Stop container {} with id {}", container.getClass(), container.getContainerId());
		container.stop();
	}

	@Override
	public boolean isRunning() {
		return container.isRunning();
	}
}
