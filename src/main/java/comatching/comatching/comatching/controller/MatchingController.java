package comatching.comatching.comatching.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import comatching.comatching.comatching.dto.GetMatchCodeRes;
import comatching.comatching.comatching.dto.MatchRes;
import comatching.comatching.comatching.dto.ValidUserMatchCodeRes;
import comatching.comatching.comatching.service.ComatchingService;
import comatching.comatching.util.Response;
import comatching.comatching.comatching.dto.MatchReq;

@Controller
@ResponseBody
@RequestMapping("/comatching")
public class MatchingController {

	private final ComatchingService comatchingService;

	public MatchingController(ComatchingService comatchingService) {
		this.comatchingService = comatchingService;
	}

	@GetMapping("/code-req/user")
	public Response<GetMatchCodeRes> getMatchCode() {
		System.out.println("here");
		return comatchingService.getMatchCode();
	}

	@GetMapping("/code-req/admin")
	public Response<ValidUserMatchCodeRes> validUserMatchCode(@RequestParam String code) {
		return comatchingService.validUserMatchCode(code);
	}

	@PostMapping("/match")
	public Response<MatchRes> requestMatch(@RequestBody MatchReq req) {
		return comatchingService.requestMatch(req);
	}
}
