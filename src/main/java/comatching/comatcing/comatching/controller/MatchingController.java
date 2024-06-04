package comatching.comatcing.comatching.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import comatching.comatcing.comatching.dto.GetMatchCodeRes;
import comatching.comatcing.comatching.dto.MatchReq;
import comatching.comatcing.comatching.dto.MatchRes;
import comatching.comatcing.comatching.dto.ValidUserMatchCodeRes;
import comatching.comatcing.comatching.service.ComatchingService;
import comatching.comatcing.util.Response;

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
