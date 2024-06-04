package comatching.comatcing.user.dto;

import java.util.List;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import comatching.comatcing.user.enums.ContactFrequency;
import comatching.comatcing.user.enums.Gender;
import comatching.comatcing.user.enums.Hobby;
import comatching.comatcing.user.enums.Major;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class RegisterDetailReq {
	private Major major;
	private String contactId;
	private Gender gender;
	private Integer age;
	private String mbti;
	private List<Hobby> hobby;
	private ContactFrequency contactFrequency;
	private String song;
	private String comment;
}
