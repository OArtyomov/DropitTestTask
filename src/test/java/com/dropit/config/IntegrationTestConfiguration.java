package com.dropit.config;

import com.zaxxer.hikari.HikariDataSource;
import net.ttddyy.dsproxy.listener.DataSourceQueryCountListener;
import net.ttddyy.dsproxy.listener.logging.CommonsQueryLoggingListener;
import net.ttddyy.dsproxy.support.ProxyDataSource;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;

@Configuration
public class IntegrationTestConfiguration {

	@SuppressWarnings("unchecked")
	protected static <T> T createDataSource(DataSourceProperties properties, Class<? extends DataSource> type) {
		return (T) properties.initializeDataSourceBuilder().type(type).build();
	}

	@Bean
	@Primary
	public ProxyDataSource proxyDataSource(DataSourceProperties properties) {
		HikariDataSource dataSource = createDataSource(properties, HikariDataSource.class);
		if (StringUtils.hasText(properties.getName())) {
			dataSource.setPoolName(properties.getName());
		}
		ProxyDataSource proxyDataSource = new ProxyDataSource();
		proxyDataSource.setDataSource(dataSource);
		proxyDataSource.addListener(new CommonsQueryLoggingListener());
		proxyDataSource.addListener(new DataSourceQueryCountListener());
		return proxyDataSource;
	}
}
