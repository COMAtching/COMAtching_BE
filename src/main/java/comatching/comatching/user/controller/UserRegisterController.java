package comatching.comatching.user.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import comatching.comatching.user.service.UserService;
import comatching.comatching.util.Response;
import comatching.comatching.user.dto.DuplicationCheckRes;
import comatching.comatching.user.dto.MainResponse;
import comatching.comatching.user.dto.RegisterDetailReq;

@Controller
@ResponseBody
@RequestMapping("/account")
public class UserRegisterController {

	private final UserService userService;

	public UserRegisterController(UserService userService) {
		this.userService = userService;
	}

	@PostMapping("/register-detail")
	public Response registerUserDetail(@RequestBody RegisterDetailReq req) {
		Response res = userService.registerUserDetail(req);
		System.out.println(res.getStatus() + res.getCode().toString());
		return res;
	}

	@GetMapping("/contact/duplication")
	public Response<DuplicationCheckRes> duplicationCheck(@RequestParam String contactId, String contactType) {
		return userService.duplicationCheck(contactId, contactType);
	}

	@GetMapping("/user/main")
	public Response<MainResponse> getMainInfo() {
		return userService.getMainInfo();
	}

}
