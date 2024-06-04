package comatching.comatcing.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import comatching.comatcing.user.entity.UserInfo;

public interface UserInfoRepository extends JpaRepository<UserInfo, Long> {
	UserInfo findByUsername(@Param("username") String username);

	Boolean existsByContactId(@Param("contactId") String contactId);

	UserInfo findByContactId(@Param("contactId") String contactId);
}
