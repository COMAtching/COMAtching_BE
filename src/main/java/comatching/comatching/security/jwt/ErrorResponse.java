package comatching.comatching.security.jwt;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString

public class ErrorResponse {

	private static final ObjectMapper objectMapper = new ObjectMapper();

	private String timestamp;
	private int status;
	private String error;
	private String message;
	private String path;

	public static ErrorResponse of(HttpStatus httpStatus, String message, HttpServletRequest request) {
		ErrorResponse errorResponse = new ErrorResponse();
		errorResponse.timestamp = LocalDateTime.now().toString();
		errorResponse.status = httpStatus.value();
		errorResponse.error = httpStatus.name();
		errorResponse.message = message;
		errorResponse.path = request.getRequestURI();

		return errorResponse;
	}

	public String convertToJson() throws JsonProcessingException {
		return objectMapper.writeValueAsString(this);
	}

}
