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
		userInfoRepository.save(userInfo);
		RegisterRes registerRes = new RegisterRes();
		registerRes.setPasswd(passwd);
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
