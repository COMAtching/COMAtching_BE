package comatching.comatcing.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import comatching.comatcing.admin.dto.ManageChargeReq;
import comatching.comatcing.admin.dto.ManageMainRes;
import comatching.comatcing.admin.service.ChargeService;
import comatching.comatcing.util.Response;

@Controller
@ResponseBody
@RequestMapping("/admin")
public class AdminController {

	private final ChargeService chargeService;

	AdminController(ChargeService chargeService) {
		this.chargeService = chargeService;
	}

	@GetMapping("/manage/main")
	public Response<ManageMainRes> getChargeRequestList() {
		return chargeService.getChargeRequestList();
	}

	@PostMapping("/manage/charge")
	public Response processCharge(@RequestBody ManageChargeReq req) {
		return chargeService.processCharge(req);
	}
}
