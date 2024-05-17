package comatching.comatcing.user.entity;

import java.util.List;

import comatching.comatcing.user.enums.ContactFrequency;
import comatching.comatcing.user.enums.Gender;
import comatching.comatcing.user.enums.Hobby;
import comatching.comatcing.user.enums.Major;
import comatching.comatcing.util.BaseEntity;
import comatching.comatcing.util.HobbyListConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserAiFeature extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "user_ai_feature_id")
	private Long id;

	@OneToOne(mappedBy = "userAiFeature")
	private UserInfo userInfo;

	private String mbti;

	@Enumerated(EnumType.STRING)
	private ContactFrequency contactFrequency;

	@Convert(converter = HobbyListConverter.class)
	private List<Hobby> hobby;

	private Integer age;

	@Enumerated(EnumType.STRING)
	private Gender gender;

	@Enumerated(EnumType.STRING)
	private Major major;

	@Builder
	public UserAiFeature(String mbti, ContactFrequency contactFrequency, List<Hobby> hobby, Integer age, Gender gender,
		Major major, UserInfo userInfo) {
		this.mbti = mbti;
		this.contactFrequency = contactFrequency;
		this.hobby = hobby;
		this.age = age;
		this.gender = gender;
		this.major = major;
		this.userInfo = userInfo;
	}
}
