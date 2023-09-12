package com.example.comatching_be.domain.reopository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.comatching_be.domain.MatchInfo;

public interface MatchInfoRepository extends JpaRepository<MatchInfo, Long> {
	List<MatchInfo> findAllByUserId(Long id);
}
