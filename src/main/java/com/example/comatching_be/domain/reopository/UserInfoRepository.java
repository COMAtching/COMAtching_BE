package com.example.comatching_be.domain.reopository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.comatching_be.domain.UserInfo;

public interface UserInfoRepository extends JpaRepository<UserInfo, Long> {
	UserInfo findByPhone(String phone);

}
