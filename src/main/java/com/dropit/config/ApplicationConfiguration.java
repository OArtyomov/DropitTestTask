package com.dropit.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Getter
public class ApplicationConfiguration {

	@Value("${async.poolSize}")
	private Integer asyncPoolSize;

	@Value("${async.maxPoolSize}")
	private Integer maxPoolSize;

	@Value("${async.queueSize}")
	private Integer queueSize;

}
