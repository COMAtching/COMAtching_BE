package com.example.comatching_be.register;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.comatching_be.register.dto.RegisterReq;
import com.example.comatching_be.register.dto.RegisterRes;
import com.example.comatching_be.util.BaseResponse;
import com.example.comatching_be.util.BaseResponseStatus;

@RestController
public class RegisterController {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public BaseResponse<MethodArgumentNotValidException> handleValidationExceptions(
		MethodArgumentNotValidException ex) {
		return new BaseResponse<>(ex);
	}

	@Autowired
	private RegisterService registerService;

	@Autowired
	private PhoneCheckService phoneCheckService;

	@Autowired
	private ParticipationService participationService;

	@PostMapping("/register")
	public BaseResponse<RegisterRes> registerUser(@RequestBody @Valid RegisterReq req) {
		RegisterRes registerRes = registerService.registerUser(req);
		if (registerRes.getPasswd().equals("FAIL")) {
			BaseResponse<RegisterRes> departFailRes = new BaseResponse<>(registerRes);
			departFailRes.setStatus(BaseResponseStatus.FAIL_REGISTER_NO_DEPART);
			return departFailRes;
		}
		return new BaseResponse<>(registerRes);
	}

	@GetMapping("/register")
	public Boolean checkPhone(@RequestParam String phone) {
		return phoneCheckService.checkPhone(phone);
	}

	@GetMapping("/register_result")
	public String sendPassword(@RequestParam String password) {
		return password;
	}

	@GetMapping("/participations")
	public Integer participationNums() {
		Integer num = participationService.participationNums();
		return num;
	}
}
