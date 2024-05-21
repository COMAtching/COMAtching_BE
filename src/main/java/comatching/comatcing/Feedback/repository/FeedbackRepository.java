package comatching.comatcing.Feedback.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import comatching.comatcing.Feedback.entity.Feedback;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {

}
