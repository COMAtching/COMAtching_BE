package comatching.comatcing.util.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import comatching.comatcing.util.Response;

@RestControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler(BusinessException.class)
	public Response handleBusinessException(BusinessException ex) {
		return Response.errorResponse(ex.getResponseCode());
	}
}
