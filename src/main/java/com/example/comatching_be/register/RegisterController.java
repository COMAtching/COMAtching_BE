package com.example.comatching_be.register;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.comatching_be.register.dto.RegisterReq;
import com.example.comatching_be.register.dto.RegisterRes;
import com.example.comatching_be.util.BaseResponse;

@RestController
public class RegisterController {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public BaseResponse<MethodArgumentNotValidException> handleValidationExceptions(
		MethodArgumentNotValidException ex) {
		return new BaseResponse<>(ex);
	}


	@Autowired
	private RegisterService registerService;

	@PostMapping("/register")
	public BaseResponse<RegisterRes> registerUser(@RequestBody @Valid RegisterReq req){
		RegisterRes registerRes = registerService.registerUser(req);
		return new BaseResponse<>(registerRes);
	}
}
