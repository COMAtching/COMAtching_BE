package comatching.comatching.util.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import comatching.comatching.util.Response;

@RestControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler(BusinessException.class)
	public Response handleBusinessException(BusinessException ex) {
		return Response.errorResponse(ex.getResponseCode());
	}
}
