package comatching.comatching.Feedback.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import comatching.comatching.Feedback.entity.Feedback;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {

}
