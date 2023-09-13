package com.example.comatching_be.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.comatching_be.admin.dto.AdminReq;

@RestController
public class AdminController {

	@Autowired
	AdminService adminService;

	@GetMapping("/adminSearchPhone")
	public String makeAdminRes(@RequestParam String phone) {
		String result = adminService.makeAdminRes(phone);
		return result;
	}

	@PostMapping("/adminButton")
	public String updateChanceChoose(@RequestBody AdminReq req) {
		String result = adminService.updateChanceChoose(req);
		return result;

	}
}
