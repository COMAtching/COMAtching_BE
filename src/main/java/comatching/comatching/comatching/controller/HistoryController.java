package comatching.comatching.comatching.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import comatching.comatching.comatching.dto.FeedbackReq;
import comatching.comatching.comatching.dto.UserComatchHistoryRes;
import comatching.comatching.comatching.service.HistoryService;
import comatching.comatching.util.Response;

@Controller
@ResponseBody
public class HistoryController {
	private final HistoryService historyService;

	HistoryController(HistoryService historyService) {
		this.historyService = historyService;

	}

	@GetMapping("/user/comatch-history")
	public Response<UserComatchHistoryRes> getHistoryList() {
		return historyService.getHistoryList();
	}

	@PostMapping("/user/comatch/feedback")
	public Response writeFeedback(@RequestBody FeedbackReq req) {
		return historyService.writeFeedback(req);
	}
}
