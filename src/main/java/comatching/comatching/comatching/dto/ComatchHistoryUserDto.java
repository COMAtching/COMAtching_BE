package comatching.comatching.comatching.dto;

import java.util.List;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import comatching.comatching.user.entity.UserInfo;
import comatching.comatching.user.enums.Hobby;
import comatching.comatching.user.enums.Major;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ComatchHistoryUserDto {
	Major major;
	String contactId;
	Integer age;
	String mbti;
	List<Hobby> hobby;
	String contactFrequency;
	String song;
	String comment;

	public static ComatchHistoryUserDto fromEntity(UserInfo userInfo) {
		return new ComatchHistoryUserDto(
			userInfo.getUserAiFeature().getMajor(),
			userInfo.getContactId(),
			userInfo.getUserAiFeature().getAge(),
			userInfo.getUserAiFeature().getMbti(),
			userInfo.getUserAiFeature().getHobby(),
			userInfo.getUserAiFeature().getContactFrequency().getValue(),
			userInfo.getSong(),
			userInfo.getComment()
		);
	}
}
