package comatching.comatcing.Feedback.entity;

import comatching.comatcing.Feedback.enums.FeedbackState;
import comatching.comatcing.util.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class Feedback extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "feedback_id")
	Long id;

	Double grade;

	@Enumerated(EnumType.STRING)
	FeedbackState feedbackState;

	@Builder
	public Feedback(Double grade, FeedbackState feedbackState) {
		this.grade = grade;
		this.feedbackState = feedbackState;
	}
}
