package comatching.comatcing.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import comatching.comatcing.user.dto.RegisterDetailReq;
import comatching.comatcing.util.Response;

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

}
