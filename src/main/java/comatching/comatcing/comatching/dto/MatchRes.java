package comatching.comatcing.comatching.dto;

import java.util.List;

import comatching.comatcing.user.entity.UserInfo;
import comatching.comatcing.user.enums.ContactFrequency;
import comatching.comatcing.user.enums.Gender;
import comatching.comatcing.user.enums.Hobby;
import comatching.comatcing.user.enums.Major;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MatchRes {
	private String song;
	private String word;
	private String mbti;
	private ContactFrequency contactFrequency;
	private List<Hobby> hobby;
	private Integer age;
	private Gender gender;
	private Major major;
	private Integer currentPoint;
	private String enemyUsername;

	public static MatchRes fromEnemyEntity(UserInfo userInfo) {

		return MatchRes.builder()
			.song(userInfo.getSong())
			.word(userInfo.getComment())
			.mbti(userInfo.getUserAiFeature().getMbti())
			.contactFrequency(userInfo.getUserAiFeature().getContactFrequency())
			.hobby(userInfo.getUserAiFeature().getHobby())
			.age(userInfo.getUserAiFeature().getAge())
			.major(userInfo.getUserAiFeature().getMajor())
			.enemyUsername(userInfo.getUsername())
			.gender(userInfo.getUserAiFeature().getGender())
			.build();
	}
}
