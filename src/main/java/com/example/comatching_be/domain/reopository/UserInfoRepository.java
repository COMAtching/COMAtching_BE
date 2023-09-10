package com.example.comatching_be.domain.reopository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.comatching_be.domain.UserInfo;

public interface UserInfoRepository extends JpaRepository<UserInfo, Long> {
	UserInfo findByPhone(String phone);

	Boolean existsByPhone(String phone);

	Boolean existsByPasswd(String passwd);

	List<UserInfo> findByMbtiStartingWithAndMbtiEndingWithAndGenderAndChoose(String ei, String pj, Boolean gender,
		Integer choose);

	List<UserInfo> findByMbtiEndingWithAndGenderAndChoose(String pj, Boolean gender, Integer choose);

	List<UserInfo> findByMbtiStartingWithAndGenderAndChoose(String ei, Boolean gender, Integer choose);

	List<UserInfo> findByGenderAndChoose(Boolean gender, Integer choose);

	@Modifying(clearAutomatically = true)
	@Query(value = "UPDATE user_info u set u.choose = :choose where u.passwd = :passwd", nativeQuery = true)
	void updateChoose(@Param(value = "passwd") String passwd, @Param(value = "choose") int a);

}
