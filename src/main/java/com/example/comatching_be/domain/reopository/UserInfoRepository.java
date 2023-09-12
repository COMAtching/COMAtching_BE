package com.example.comatching_be.domain.reopository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.comatching_be.domain.MatchInfo;
import com.example.comatching_be.domain.UserInfo;

public interface UserInfoRepository extends JpaRepository<UserInfo, Long> {

	UserInfo findAllById(Long id);

	UserInfo findByPhone(String phone);

	Boolean existsByPhone(String phone);

	Boolean existsByPasswd(String passwd);

	UserInfo findAllByPasswd(String passwd);

	List<MatchInfo> findMatchInfosByPasswd(String passwd);

	List<UserInfo> findByMbtiStartingWithAndMbtiEndingWithAndGenderAndChoose(String ei, String pj, Boolean gender,
		Integer choose);

	List<UserInfo> findByMbtiEndingWithAndGenderAndChoose(String pj, Boolean gender, Integer choose);

	List<UserInfo> findByMbtiStartingWithAndGenderAndChoose(String ei, Boolean gender, Integer choose);

	List<UserInfo> findByMbtiStartingWithAndMbtiEndingWithAndGenderAndChooseGreaterThan(String ei, String pj,
		Boolean gender,
		Integer choose);

	List<UserInfo> findByMbtiEndingWithAndGenderAndChooseGreaterThan(String pj, Boolean gender, Integer choose);

	List<UserInfo> findByMbtiStartingWithAndGenderAndChooseGreaterThan(String ei, Boolean gender, Integer choose);

	List<UserInfo> findByGenderAndChoose(Boolean gender, Integer choose);

	List<UserInfo> findByGenderAndChooseGreaterThan(Boolean gender, Integer choose);

	List<Integer> findMatchesByPasswd(String passwd);

	Integer countBy();

	@Modifying(clearAutomatically = true)
	@Query(value = "UPDATE user_info u set u.choose = :choose where u.passwd = :passwd", nativeQuery = true)
	void updateChoose(@Param(value = "passwd") String passwd, @Param(value = "choose") int a);

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
