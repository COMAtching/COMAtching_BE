package com.example.comatching_be.register;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.comatching_be.domain.reopository.UserInfoRepository;

@Service
public class ParticipationService {
	@Autowired
	UserInfoRepository userInfoRepository;

	public Integer participationNums() {
		return userInfoRepository.countBy();
	}
}
