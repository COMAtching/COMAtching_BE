package comatching.comatching.point.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import comatching.comatching.point.entity.PointHistory;

public interface PointHistoryRepository extends JpaRepository<PointHistory, Long> {

}
