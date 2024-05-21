package comatching.comatcing.util;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public enum ResponseCode {
	//Basic
	SUCCESS(200, "GEN-000", HttpStatus.OK, "Success"),

	//Matching : MAT
	MATCH_CODE_NOT_FOUND(404, "MAT-000", HttpStatus.NOT_FOUND, "해당 유저가 없습니다"),
	SHORT_OF_POINT(400, "MAT-001", HttpStatus.BAD_REQUEST, "point is short "),
	MATCH_NOT_ALLOW(400, "MAT-002", HttpStatus.BAD_REQUEST, "아직 인증되지 않은 사용자"),
	AI_FAIL_MATCH(400, "MAT-003", HttpStatus.BAD_REQUEST, "아직 인증되지 않은 사용자"),

	//Security
	TOKEN_EXPIRED(400, "SEC-001", HttpStatus.BAD_REQUEST, "token is expired or not available"),
	TOKEN_NOT_AVAILABLE(400, "SEC-002", HttpStatus.BAD_REQUEST, "token is not available ");

	private final Integer status;
	private final String code;
	private final HttpStatus httpStatus;
	private final String message;

	ResponseCode(Integer status, String code, HttpStatus httpStatus, String message) {
		this.status = status;
		this.code = code;
		this.httpStatus = httpStatus;
		this.message = message;
	}
}
