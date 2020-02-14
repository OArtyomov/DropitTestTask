package com.dropit.config;

import com.google.common.collect.ImmutableMap;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.MapPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;

import java.util.HashMap;
import java.util.Map;

public class IntegrationTestConfigurationInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
	@Override
	public void initialize(ConfigurableApplicationContext applicationContext) {
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.putAll(configurePostgresContainer(applicationContext));
		applicationContext.getEnvironment().getPropertySources().addFirst(new MapPropertySource("test", resultMap));
	}

	private Map<String, Object> configurePostgresContainer(ConfigurableApplicationContext applicationContext) {
		PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer();
		postgreSQLContainer.start();
		LifecycleHandler lifecycleHandler = new LifecycleHandler(postgreSQLContainer);
		applicationContext.getBeanFactory().registerSingleton("LifeHandlerPostgress",
				lifecycleHandler);
		return ImmutableMap.of(
				"spring.datasource.url", postgreSQLContainer.getJdbcUrl(),
				"spring.datasource.username", postgreSQLContainer.getUsername(),
				"spring.datasource.password", postgreSQLContainer.getPassword()
		);
	}

}
