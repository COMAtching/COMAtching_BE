package comatching.comatcing.security.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import comatching.comatcing.util.Response;

@Controller
public class AuthController {

	@ResponseBody
	@GetMapping("/token/check")
	public Response authTest() {
		System.out.println("/token/check -> controller");
		return Response.ok();
	}
}
