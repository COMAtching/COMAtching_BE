package com.example.comatching_be.match;

import java.util.List;
import java.util.Random;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.comatching_be.domain.MatchInfo;
import com.example.comatching_be.domain.UserInfo;
import com.example.comatching_be.domain.reopository.MatchInfoRepository;
import com.example.comatching_be.domain.reopository.UserInfoRepository;
import com.example.comatching_be.match.dto.MatchReq;
import com.example.comatching_be.match.dto.MatchRes;
import com.example.comatching_be.util.BaseResponse;
import com.example.comatching_be.util.BaseResponseStatus;

@Service
public class MatchService {
	@Autowired
	UserInfoRepository userInfoRepository;

	@Autowired
	MatchInfoRepository matchInfoRepository;

	private String choosePasswd;
	private Long chosenIdx;

	public BaseResponse<MatchRes> matching(MatchReq req) {
		String mbti = req.getMbti();
		String ei = String.valueOf(mbti.charAt(0));
		String jp = String.valueOf(mbti.charAt(1));
		List<UserInfo> candidate;
		MatchRes result;
		Boolean gender = req.getGender();
		BaseResponse<MatchRes> response = new BaseResponse<>(BaseResponseStatus.SUCCESS);

		//매칭 5회 초과시 예외처리
		UserInfo userinfo_temp = userInfoRepository.findAllByPasswd(req.getPasswd());
		Integer acc = userinfo_temp.getChanceAccrue();
		if (acc == 5) {
			response = new BaseResponse<>(BaseResponseStatus.FAIL_ACCRUE_OVER);
			return response;
		}

		Integer reqUserChance = userinfo_temp.getChance();
		if (reqUserChance < 1) {
			response = new BaseResponse<>(BaseResponseStatus.FAIL_NOT_BUY_CHANCE);
			return response;
		}

		UserInfo reqUserInfo;
		UserInfo resUserInfo;

		//MBTI 조건별 후보 List 조회
		System.out.println(ei + jp);
		if (ei.equals("Z") && jp.equals("X")) {
			candidate = userInfoRepository.findByGenderAndChooseGreaterThan(gender, 0);
		} else if (jp.equals("X")) {
			candidate = userInfoRepository.findByMbtiStartingWithAndGenderAndChooseGreaterThan(ei, gender, 0);
		} else if (ei.equals("Z")) {
			candidate = userInfoRepository.findByMbtiEndingWithAndGenderAndChooseGreaterThan(jp, gender, 0);
		} else {
			candidate = userInfoRepository.findByMbtiStartingWithAndMbtiEndingWithAndGenderAndChooseGreaterThan(ei, jp,
				gender, 0);
		}

		//조건에 맞지 않는 경우 처리
		System.out.println(candidate);
		if (candidate.isEmpty()) {
			candidate = userInfoRepository.findByGenderAndChooseGreaterThan(gender, 0);
			if (candidate.isEmpty()) {
				response.setStatus(BaseResponseStatus.FAIL_NO_MATCH);
				return response;
			}
			response.setStatus(BaseResponseStatus.FAIL_MBTI_MATCH);
			result = pickPartnerAsResponse(candidate, req.getPasswd());
			response.setResult(result);
		} else {
			result = pickPartnerAsResponse(candidate, req.getPasswd());
			response.setResult(result);
		}

		reqUserInfo = addMatcher(chosenIdx, req.getPasswd());
		resUserInfo = updateChoose(choosePasswd);
		userInfoRepository.saveAndFlush(reqUserInfo);
		userInfoRepository.saveAndFlush(resUserInfo);
		return response;
	}

	public MatchRes pickPartnerAsResponse(List<UserInfo> candidate, String passwd) {
		Random rnd = new Random();
		UserInfo matchPartner = new UserInfo();
		MatchRes result = new MatchRes();

		//매칭 신청자가 후보지에 있는 경우 제거
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
		chosenIdx = matchPartner.getId();
		System.out.println(chosenIdx);
		return result;
	}

	@Transactional
	public UserInfo addMatcher(Long matcherId, String passwd) { //UserInfo 테이블에 매칭상대 리스트 추가
		UserInfo userInfo = userInfoRepository.findAllByPasswd(passwd);
		MatchInfo matchInfo = new MatchInfo();
		matchInfo.setUserId(userInfo.getId());
		matchInfo.setMatcherId(matcherId);
		matchInfoRepository.saveAndFlush(matchInfo);
		userInfo.addMatchInfo(matchInfo);
		userInfo.setChanceAccrue(userInfo.getChanceAccrue() + 1);
		System.out.println("userinfo: " + userInfo);
		return userInfo;
	}

	@Transactional
	public UserInfo updateChoose(String passwd) {            //매칭당한 사람의 choose값 -1
		UserInfo userInfo = userInfoRepository.findAllByPasswd(passwd);
		userInfo.setChoose(userInfo.getChoose() - 1);
		return userInfo;
	}
}