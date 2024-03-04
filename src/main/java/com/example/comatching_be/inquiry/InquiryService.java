package com.example.comatching_be.inquiry;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.comatching_be.domain.MatchInfo;
import com.example.comatching_be.domain.UserInfo;
import com.example.comatching_be.domain.reopository.MatchInfoRepository;
import com.example.comatching_be.domain.reopository.UserInfoRepository;
import com.example.comatching_be.inquiry.dto.InquiryRes;

@Service
public class InquiryService {
	@Autowired
	UserInfoRepository userInfoRepository;

	@Autowired
	MatchInfoRepository matchInfoRepository;

	public List<InquiryRes> makeInquiry(String passwd) {
		List<InquiryRes> inquiryRes = new ArrayList<>();
		List<MatchInfo> matchInfos = null;
		UserInfo reqUserInfo = userInfoRepository.findAllByPasswd(passwd);
		Long reqUserId = reqUserInfo.getId();
		matchInfos = matchInfoRepository.findAllByUserId(reqUserId);
		UserInfo userInfo_temp;

		//System.out.println("matchInfos --->" + matchInfos);

		if (matchInfos.isEmpty()) {
			return inquiryRes;
		}
		for (MatchInfo matchInfo_temp : matchInfos) {
			userInfo_temp = userInfoRepository.findAllById(matchInfo_temp.getMatcherId());
			InquiryRes inquiryRes_temp = new InquiryRes(userInfo_temp.getPhone(), userInfo_temp.getDepart(),
				userInfo_temp.getSong(), userInfo_temp.getMbti(), userInfo_temp.getYear());
			//System.out.println("inquiryRes_temp " + inquiryRes_temp);
			inquiryRes.add(inquiryRes_temp);
		}
		System.out.println("inquiryService: " + inquiryRes);
		System.out.println("test");
		return inquiryRes;
	}
}
