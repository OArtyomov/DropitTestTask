package com.dropit.rest;

import com.dropit.dto.ValidationErrorMessageDTO;
import com.dropit.exceptions.DeliveryNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Locale;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Slf4j
@RestControllerAdvice(basePackages = "com.dropit.rest")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class GlobalExceptionHandler {

	private ResourceBundleMessageSource msgSource;

	@ExceptionHandler({DeliveryNotFoundException.class})
	@ResponseStatus(NOT_FOUND)
	public ValidationErrorMessageDTO entityNotFoundExceptionHandler(DeliveryNotFoundException ex, Locale locale) {
		log.error("Exception: ", ex);
		String message = msgSource.getMessage("deliveryNotFound", new Object[]{ex.getId()}, locale);
		return new ValidationErrorMessageDTO().setMessage(message);
	}
}
