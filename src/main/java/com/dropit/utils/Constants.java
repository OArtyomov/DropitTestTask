package com.dropit.utils;

public final class Constants {

	private Constants() {

	}

	public static final String DELIVERY_BASE_URI = "/api/v1/delivery";

	public static final String PACKAGE_BASE_URI = "/api/v1/package";

	public static final String ADDRESS_BASE_URI = "/api/v1/address";

	public static final String DELIVERY_ENTRY_BASE_URI = DELIVERY_BASE_URI + "/{deliveryId}";

	public static final String COMMON_SWAGGER_TAG = "Delivery API: delivery";

	public static final String ASYNC_TASK_EXECUTOR_BEAN_NAME = "asyncTaskExecutor";
}