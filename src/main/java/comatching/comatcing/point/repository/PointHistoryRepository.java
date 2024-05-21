package comatching.comatcing.point.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import comatching.comatcing.point.entity.PointHistory;

public interface PointHistoryRepository extends JpaRepository<PointHistory, Long> {

}
