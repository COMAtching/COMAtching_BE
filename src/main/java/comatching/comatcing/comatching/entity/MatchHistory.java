package comatching.comatcing.comatching.entity;

import comatching.comatcing.process_ai.match_options.AgeOption;
import comatching.comatcing.user.entity.UserInfo;
import comatching.comatcing.user.enums.ContactFrequency;
import comatching.comatcing.user.enums.Hobby;
import comatching.comatcing.util.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MatchHistory extends BaseEntity {
	@Id
	@Column(name = "matching_history_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_info_id")
	UserInfo userInfo;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "matcher_info_id")
	UserInfo matcherInfo;

	@Column(length = 4)
	String mbtiOption;

	@Enumerated(value = EnumType.STRING)
	AgeOption ageOption;

	@Enumerated(value = EnumType.STRING)
	ContactFrequency contactFrequencyOption;

	@Enumerated(value = EnumType.STRING)
	Hobby hobbyOption;

	@Builder
	public MatchHistory(UserInfo userInfo, UserInfo matcherInfo, String mbtiOption, AgeOption ageOption,
		ContactFrequency contactFrequencyOption, Hobby hobbyOption) {
		this.userInfo = userInfo;
		this.matcherInfo = matcherInfo;
		this.mbtiOption = mbtiOption;
		this.ageOption = ageOption;
		this.contactFrequencyOption = contactFrequencyOption;
		this.hobbyOption = hobbyOption;
	}
}
