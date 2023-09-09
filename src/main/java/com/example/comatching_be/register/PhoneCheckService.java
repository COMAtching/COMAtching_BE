package com.example.comatching_be.register;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.comatching_be.domain.reopository.UserInfoRepository;

@Service
public class PhoneCheckService {
	@Autowired
	private UserInfoRepository userInfoRepository;

	public Boolean checkPhone(String phone){
		Boolean exist = userInfoRepository.existsByPhone(phone);
		return exist;
	}
}
