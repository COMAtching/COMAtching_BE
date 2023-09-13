package com.example.comatching_be.match;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;

import com.example.comatching_be.match.dto.MatchReq;
import com.example.comatching_be.match.dto.MatchRes;
import com.example.comatching_be.util.BaseResponse;

@Transactional
@RestController
public class MatchController {

	@Autowired
	private MatchService matchService;

	@GetMapping("/match")
	public BaseResponse<MatchRes> matching(@ModelAttribute MatchReq req) {
		BaseResponse<MatchRes> result = matchService.matching(req);
		return result;
	}
}
