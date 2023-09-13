package com.example.comatching_be.domain.reopository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.comatching_be.domain.UserInfo;

public interface UserInfoRepository extends JpaRepository<UserInfo, Long> {

	UserInfo findAllById(Long id);

	UserInfo findAllByPhone(String phone);

	Boolean existsByPhone(String phone);

	Boolean existsByPasswd(String passwd);

	UserInfo findAllByPasswd(String passwd);

	List<UserInfo> findByMbtiStartingWithAndMbtiEndingWithAndGenderAndChooseGreaterThan(String ei, String pj,
		Boolean gender,
		Integer choose);

	List<UserInfo> findByMbtiEndingWithAndGenderAndChooseGreaterThan(String pj, Boolean gender, Integer choose);

	List<UserInfo> findByMbtiStartingWithAndGenderAndChooseGreaterThan(String ei, Boolean gender, Integer choose);

	List<UserInfo> findByGenderAndChooseGreaterThan(Boolean gender, Integer choose);

	Integer countBy();

	//// update 이따구로 하지 말자
	// @Modifying(clearAutomatically = true)
	// @Query(value = "UPDATE user_info u set u.choose = :choose where u.passwd = :passwd", nativeQuery = true)
	// void updateChoose(@Param(value = "passwd") String passwd, @Param(value = "choose") int a);

	// @Modifying(clearAutomatically = true, flushAutomatically = true)
	// @Query(value = "UPDATE user_info u set u.chance = :chance where u.passwd = :passwd", nativeQuery = true)
	// void updateChance(@Param(value = "passwd") String passwd, @Param(value = "chance") int a);
	//
	// @Modifying(clearAutomatically = true, flushAutomatically = true)
	// @Query(value = "UPDATE user_info u set u.chance = :chance, u.matches = :matches where u.passwd = :passwd", nativeQuery = true)
	// void updateChanceMatches(
	// 	@Param(value = "passwd") String passwd,
	// 	@Param(value = "chance") int a,
	// 	@Param(value = "matches") List<Integer> matches);
}
