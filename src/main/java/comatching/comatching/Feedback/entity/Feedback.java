package comatching.comatching.Feedback.entity;

import comatching.comatching.util.BaseEntity;
import comatching.comatching.Feedback.enums.FeedbackState;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
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

	public void writeFeedback(Double grade) {
		this.grade = grade;
		this.feedbackState = FeedbackState.COMPLETE;
	}
}
