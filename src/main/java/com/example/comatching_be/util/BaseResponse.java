package com.example.comatching_be.util;

import org.springframework.web.bind.MethodArgumentNotValidException;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.ser.Serializers;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
@JsonPropertyOrder({"isSuccess", "code", "message", "result"})
@Setter
public class BaseResponse<T> {
	@JsonProperty("isSuccess")
	private Boolean isSuccess;
	private String message;
	private int code;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private T result;

	public BaseResponse(T result) {
		this.isSuccess = BaseResponseStatus.SUCCESS.isSuccess();
		this.message = BaseResponseStatus.SUCCESS.getMessage();
		this.code = BaseResponseStatus.SUCCESS.getCode();
		this.result = result;
	}

	public BaseResponse(BaseResponseStatus status) {
		this.isSuccess = status.isSuccess();
		this.message = status.getMessage();
		this.code = status.getCode();
	}

	public BaseResponse(BaseResponseStatus status, T result) {
		this.isSuccess = status.isSuccess();
		this.message = status.getMessage();
		this.code = status.getCode();
		this.result = result;
	}

	public BaseResponse(MethodArgumentNotValidException exception) {
		this.isSuccess = false;
		this.message = exception.getBindingResult().getAllErrors().get(0).getDefaultMessage();
		this.code = 400;
	}

	public void setStatus(BaseResponseStatus status) {
		this.isSuccess = status.isSuccess();
		this.message = status.getMessage();
		this.code = status.getCode();
	}
}
