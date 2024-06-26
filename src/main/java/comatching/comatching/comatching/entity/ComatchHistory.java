package comatching.comatching.comatching.entity;

import java.util.List;

import comatching.comatching.Feedback.entity.Feedback;
import comatching.comatching.process_ai.match_options.ContactFrequencyOption;
import comatching.comatching.user.entity.UserInfo;
import comatching.comatching.user.enums.Hobby;
import comatching.comatching.util.BaseEntity;
import comatching.comatching.util.HobbyListConverter;
import comatching.comatching.process_ai.match_options.AgeOption;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ComatchHistory extends BaseEntity {
	@Id
	@Column(name = "comatch_history_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "user_info_id")
	private UserInfo userInfo;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "enemy_info_id")
	private UserInfo enemyInfo;

	@OneToOne
	@JoinColumn(name = "feedback_id")
	private Feedback feedback;

	@Column(length = 4)
	private String mbtiOption;

	@Enumerated(value = EnumType.STRING)
	private AgeOption ageOption;

	@Enumerated(value = EnumType.STRING)
	private ContactFrequencyOption contactFrequencyOption;

	@Convert(converter = HobbyListConverter.class)
	private List<Hobby> hobbyOption;

	private Boolean noSameMajorOption;

	@Builder
	public ComatchHistory(UserInfo userInfo, UserInfo enemyInfo, String mbtiOption, AgeOption ageOption,
		ContactFrequencyOption contactFrequencyOption, List<Hobby> hobbyOption, Boolean noSameMajorOption,
		Feedback feedback) {
		this.userInfo = userInfo;
		this.enemyInfo = enemyInfo;
		this.mbtiOption = mbtiOption;
		this.ageOption = ageOption;
		this.contactFrequencyOption = contactFrequencyOption;
		this.hobbyOption = hobbyOption;
		this.noSameMajorOption = noSameMajorOption;
		this.feedback = feedback;

	}
}
