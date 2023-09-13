package com.example.comatching_be.register;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.comatching_be.domain.UserInfo;
import com.example.comatching_be.domain.reopository.UserInfoRepository;
import com.example.comatching_be.register.dto.RegisterReq;
import com.example.comatching_be.register.dto.RegisterRes;

@Service
public class RegisterService {
	@Autowired
	UserInfoRepository userInfoRepository;

	public RegisterRes registerUser(RegisterReq req) {
		String[] depart = new String[] {"인문계열", "국어국문학과", "철학과", "국사학과", "어문계열", "영어영문학부", "중국언어문화학과", "일어일본문화학과",
			"프랑스어문화학과", "음악과", "종교학과", "신학대학(성신교정)", "사회과학계열", "사회복지학과", "심리학과", "사회학과", "특수교육과", "경영계열", "경영학과",
			"회계학과", "국제·법정경계열", "국제학부", "법학과", "경제학과", "행정학과", "글로벌경영대학", "글로벌미래경영학과", "세무회계금융학과", "IT파이낸스학과", "자연과학계열",
			"화학과", "수학과", "물리학과", "생활과학계열", "공간디자인·소비자학과", "의류학과", "아동학과", "식품영양학과", "의생명과학과", "약학대학", "간호대학(성의교정)",
			"의과대학(성의교정)", "ICT공학계열", "컴퓨터정보공학부", "미디어기술콘텐츠학과", "정보통신전자공학부", "바이오융합공학계열", "생명공학과", "에너지환경공학과",
			"바이오메디컬화학공학과 ", "인공지능학과", "데이터사이언스학과", "바이오메디컬소프트웨어학과", "가톨릭대학교가 아닙니다"};
		int status = 0;
		for (int i = 0; i < depart.length; i++) {
			if (req.getDepart().equals(depart[i])) {
				status = 1;
			}
		}
		if (status == 0) {
			RegisterRes depart_fail = new RegisterRes();
			depart_fail.setPasswd("FAIL");
			return depart_fail;
		}

		String passwd = makeCode();

		while (userInfoRepository.existsByPasswd(passwd)) {
			passwd = makeCode();
		}

		UserInfo userInfo = new UserInfo();
		userInfo.setGender(req.getGender());
		userInfo.setPhone(req.getPhone());
		userInfo.setDepart(req.getDepart());
		userInfo.setSong(req.getSong());
		userInfo.setYear(req.getYear());
		userInfo.setMbti(req.getMbti());
		userInfo.setChance(0);
		userInfo.setChoose(1);
		userInfo.setPasswd(passwd);
		userInfo.setChanceAccrue(0);
		userInfoRepository.save(userInfo);
		RegisterRes registerRes = new RegisterRes();
		registerRes.setPasswd(passwd);
		System.out.println("registerService=====" + registerRes);
		return registerRes;
	}

	private String makeCode() {

		Random rnd = new Random();
		StringBuffer buf = new StringBuffer();
		String passwd;
		int p;

		for (int i = 0; i < 6; i++) {
			p = rnd.nextInt(3);
			if (p == 0) {
				buf.append((char)((int)(rnd.nextInt(26)) + 97));
			} else if (p == 1) {
				buf.append((char)((int)(rnd.nextInt(26)) + 65));
			} else {
				buf.append((char)((int)(rnd.nextInt(9)) + 48));
			}
		}
		passwd = buf.toString();
		System.out.println(passwd);
		return passwd;
	}

}
