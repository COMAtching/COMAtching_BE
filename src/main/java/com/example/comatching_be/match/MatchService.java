package com.example.comatching_be.match;

import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.comatching_be.domain.UserInfo;
import com.example.comatching_be.domain.reopository.UserInfoRepository;
import com.example.comatching_be.match.dto.MatchReq;
import com.example.comatching_be.match.dto.MatchRes;
import com.example.comatching_be.util.BaseResponse;
import com.example.comatching_be.util.BaseResponseStatus;

@Service
public class MatchService {
	@Autowired
	UserInfoRepository userInfoRepository;

	private String choosePasswd;

	public BaseResponse<MatchRes> matching(MatchReq req) {
		String mbti = req.getMbti();
		String ei = String.valueOf(mbti.charAt(0));
		String jp = String.valueOf(mbti.charAt(1));
		List<UserInfo> candidate;
		MatchRes result;
		Boolean gender = !req.getGender();
		BaseResponse<MatchRes> response = new BaseResponse<>(BaseResponseStatus.SUCCESS);

		System.out.println(gender);

		System.out.println(ei + jp);
		if (ei.equals("Z") && jp.equals("Z")) {
			candidate = userInfoRepository.findByGenderAndChoose(gender, 1);
		} else if (jp.equals("Z")) {
			candidate = userInfoRepository.findByMbtiStartingWithAndGenderAndChoose(ei, gender, 1);
		} else if (ei.equals("Z")) {
			candidate = userInfoRepository.findByMbtiEndingWithAndGenderAndChoose(jp, gender, 1);
		} else {
			candidate = userInfoRepository.findByMbtiStartingWithAndMbtiEndingWithAndGenderAndChoose(ei, jp, gender, 1);
		}

		System.out.println(candidate);
		if (candidate.isEmpty()) {
			candidate = userInfoRepository.findByGenderAndChoose(gender, 1);
			if (candidate.isEmpty()) {
				response.setStatus(BaseResponseStatus.FAIL_NO_MATCH);
				return response;
			}
			response.setStatus(BaseResponseStatus.FAIL_MBTI_MATCH);
			System.out.println(choosePasswd);
			result = pickPartnerAsResponse(candidate, req.getPasswd());
		} else {
			result = pickPartnerAsResponse(candidate, req.getPasswd());
			System.out.println(choosePasswd);
			response.setResult(result);
		}

		System.out.println(candidate);
		System.out.println(result);
		System.out.println(choosePasswd);
		userInfoRepository.updateChoose(choosePasswd, 0);
		return response;

	}

	public MatchRes pickPartnerAsResponse(List<UserInfo> candidate, String passwd) {
		Random rnd = new Random();
		UserInfo matchPartner = new UserInfo();
		MatchRes result = new MatchRes();

		for (UserInfo userInfo_temp : candidate) {
			if (userInfo_temp.getPasswd().equals(passwd)) {
				candidate.remove(userInfo_temp);
			}
		}

		matchPartner = candidate.get(rnd.nextInt(candidate.size()));
		result.setSong(matchPartner.getSong());
		result.setPhone(matchPartner.getPhone());
		result.setMbti(matchPartner.getMbti());
		result.setYear(matchPartner.getYear());
		result.setDepart(matchPartner.getDepart());
		result.setGender(matchPartner.getGender());
		choosePasswd = matchPartner.getPasswd();
		return result;
	}
	
}
