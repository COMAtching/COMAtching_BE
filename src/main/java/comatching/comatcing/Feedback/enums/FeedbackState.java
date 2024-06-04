package comatching.comatcing.Feedback.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum FeedbackState {

	COMPLETE("done"),
	IN_PROGRESS("inProgress"),
	FINISH("finish");

	private final String value;
}
