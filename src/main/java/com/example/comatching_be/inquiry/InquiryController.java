package com.example.comatching_be.inquiry;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;

import com.example.comatching_be.inquiry.dto.InquiryReq;
import com.example.comatching_be.inquiry.dto.InquiryRes;
import com.example.comatching_be.util.BaseResponse;
import com.example.comatching_be.util.BaseResponseStatus;

@RestController
public class InquiryController {
	@Autowired
	private InquiryService inquiryService;

	@ExceptionHandler(NullPointerException.class)
	public BaseResponse<BaseResponseStatus> handNullPointerExceptions(NullPointerException ex) {
		BaseResponse<BaseResponseStatus> failResult = new BaseResponse<>(BaseResponseStatus.FAIL_NO_PASSWORD);
		return failResult;
	}

	@GetMapping("/inquiry")
	public BaseResponse<List<InquiryRes>> makeInquiry(@ModelAttribute InquiryReq req) {
		List<InquiryRes> inquiryRes = inquiryService.makeInquiry(req.getPasswd());
		if (inquiryRes.size() == 0) {
			BaseResponse<List<InquiryRes>> failResult = new BaseResponse<>(inquiryRes);
			failResult.setStatus(BaseResponseStatus.FAIL_NO_MATCHER);
			return failResult;
		} else {
			BaseResponse<List<InquiryRes>> result = new BaseResponse<>(inquiryRes);
			return result;
		}
	}
}
