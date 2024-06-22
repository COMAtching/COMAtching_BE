package comatching.comatching.comatching.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import comatching.comatching.Feedback.enums.FeedbackState;
import comatching.comatching.comatching.entity.ComatchHistory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Builder
public class HistoryListData {
	ComatchHistoryUserDto enemyInfo;
	FeedbackState feedbackState;
	Long historyId;
	LocalDateTime create_time;

	public static HistoryListData fromComatchHistory(ComatchHistory comatchHistory) {
		return HistoryListData.builder()
			.enemyInfo(ComatchHistoryUserDto.fromEntity(comatchHistory.getEnemyInfo()))
			.feedbackState(comatchHistory.getFeedback().getFeedbackState())
			.create_time(comatchHistory.getFeedback().getCreatedAt())
			.historyId(comatchHistory.getId())
			.build();
	}
}
