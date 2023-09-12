package com.example.comatching_be.admin;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.comatching_be.admin.dto.AdminReq;
import com.example.comatching_be.domain.UserInfo;
import com.example.comatching_be.domain.reopository.UserInfoRepository;

@Service
public class AdminService {

	@Autowired
	UserInfoRepository userInfoRepository;

	public String makeAdminRes(String phone) {
		UserInfo userinfo = userInfoRepository.findAllByPhone(phone);
		if (userinfo == null) {
			return "일치하는 코드가 없습니다. 다시 입력해주세요.";
		}
		String chance = "Pick Someone: " + userinfo.getChance().toString();
		String choose = " Pick Me: " + userinfo.getChoose().toString();
		String passwd = " Password " + userinfo.getPasswd();
		String result = chance + choose + passwd;
		return result;
	}

	public String updateChanceChoose(AdminReq req) {
		System.out.println(req.getPasswd());
		UserInfo userInfo = userInfoRepository.findAllByPasswd(req.getPasswd());
		if ((userInfo.getChance() + req.getChance()) > 5) {
			return "현재 Pick Someone: " + userInfo.getChance().toString() + "입니다. 최대 5번까지 등록 가능합니다.";
		} else {
			userInfo = updateChanceChoose(req.getChance(), req.getChoose(), userInfo);
		}

		userInfoRepository.saveAndFlush(userInfo);
		return "처리되었습니다";
	}

	@Transactional
	public UserInfo updateChanceChoose(Integer chance, Integer choose, UserInfo userInfo) {
		userInfo.setChance(userInfo.getChance() + chance);
		userInfo.setChoose(userInfo.getChoose() + choose);
		return userInfo;
	}
}
