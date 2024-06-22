package comatching.comatching.user.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import comatching.comatching.user.entity.UserInfo;

public interface UserInfoRepository extends JpaRepository<UserInfo, Long> {
	UserInfo findByUsername(@Param("username") String username);

	Boolean existsByContactId(@Param("contactId") String contactId);

	UserInfo findByContactId(@Param("contactId") String contactId);

	@Query("SELECT u FROM UserInfo u WHERE u.userAiFeature IS NOT NULL")
	List<UserInfo> findAllUsers();
}
