package comatching.comatcing.user.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import comatching.comatcing.user.dto.ParticipationRes;
import comatching.comatcing.user.repository.UserInfoRepository;
import comatching.comatcing.user.service.UserService;

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
}
