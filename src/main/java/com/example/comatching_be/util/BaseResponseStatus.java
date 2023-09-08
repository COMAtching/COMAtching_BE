package com.example.comatching_be.util;

import lombok.Getter;

@Getter
public enum BaseResponseStatus {
	SUCCESS(true, 1000, "요청에 성공하였습니다."),
	SUCCESS_REGISTER(true, 1001, "요청에 성공하였습니다."),
	FAIL_REGISTER_DUPLICATE(false, 1002, "이미 등록된 연락처 입니다.");

	private final boolean isSuccess;
	private final int code;
	private final String message;

	private BaseResponseStatus(boolean isSuccess, int code, String message) {
		this.isSuccess = isSuccess;
		this.code = code;
		this.message = message;
	}
}