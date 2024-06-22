package comatching.comatching.comatching.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import comatching.comatching.comatching.entity.ComatchHistory;

public interface ComatchHistoryRepository extends JpaRepository<ComatchHistory, Long> {
	List<ComatchHistory> findByUserInfoUsername(String username);
}
