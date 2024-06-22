package comatching.comatching.user.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import comatching.comatching.user.repository.UserInfoRepository;
import comatching.comatching.user.service.UserService;
import comatching.comatching.util.Response;
import comatching.comatching.user.dto.ParticipationRes;

@Controller
public class UserController {

	private final UserInfoRepository userInfoRepository;

	private final UserService userService;

	UserController(UserInfoRepository userInfoRepository, UserService userService) {
		this.userInfoRepository = userInfoRepository;
		this.userService = userService;
	}

	@GetMapping("/participation/count")
	@ResponseBody
	public ParticipationRes getParticipation() {
		Long participation = userInfoRepository.count();
		System.out.println("controller");
		return new ParticipationRes(String.valueOf(participation));
	}

	@GetMapping("/user/charge/request")
	@ResponseBody
	public Response addChargeRequest() {
		return userService.addChargeRequest();
	}
}
