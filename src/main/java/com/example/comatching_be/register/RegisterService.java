package com.example.comatching_be.register;

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

	public RegisterRes registerUser(RegisterReq req){

		UserInfo userInfo = new UserInfo();
		userInfo.setGender(req.getGender());
		userInfo.setPhone(req.getPhone());
		userInfo.setDepart(req.getDepart());
		userInfo.setSong(req.getSong());
		userInfo.setYear(req.getYear());
		userInfo.setMbti(req.getMbti());
		userInfo.setChance(1);
		userInfo.setChoose(0);
		userInfoRepository.save(userInfo);
		RegisterRes registerRes = new RegisterRes();
		registerRes.setA(1);
		return registerRes;
	}

}
