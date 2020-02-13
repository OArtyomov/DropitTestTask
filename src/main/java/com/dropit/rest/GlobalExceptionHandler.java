package com.dropit.rest;

import com.dropit.dto.ValidationErrorMessageDTO;
import com.dropit.exceptions.DeliveryNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.Locale;

import static java.util.stream.Collectors.toList;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Slf4j
@RestControllerAdvice(basePackages = "com.dropit.rest")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class GlobalExceptionHandler {

	private ResourceBundleMessageSource msgSource;

	private ValidationErrorMessageDTO convertMessage(ObjectError error, Locale locale) {
		String message = msgSource.getMessage(error, locale);
		return new ValidationErrorMessageDTO()
				.setMessage(message)
				.setFieldName(extractFieldName(error));
	}

	private String extractFieldName(ObjectError error) {
		if (error instanceof FieldError) {
			return ((FieldError) error).getField();
		}
		return null;
	}

	@ExceptionHandler({MethodArgumentNotValidException.class})
	@ResponseStatus(BAD_REQUEST)
	public List<ValidationErrorMessageDTO> validationErrorHandler(MethodArgumentNotValidException ex, Locale locale) {
		log.error("Exception: ", ex);
		return ex.getBindingResult().getAllErrors().stream().map(error -> convertMessage(error, locale)).collect(toList());
	}

	@ExceptionHandler({DeliveryNotFoundException.class})
	@ResponseStatus(NOT_FOUND)
	public ValidationErrorMessageDTO entityNotFoundExceptionHandler(DeliveryNotFoundException ex, Locale locale) {
		log.error("Exception: ", ex);
		String message = msgSource.getMessage("deliveryNotFound", new Object[]{ex.getId()}, locale);
		return new ValidationErrorMessageDTO().setMessage(message);
	}
}
