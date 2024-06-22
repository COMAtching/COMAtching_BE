package comatching.comatching.user.dto;

import java.util.List;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import comatching.comatching.user.enums.ContactFrequency;
import comatching.comatching.user.enums.Gender;
import comatching.comatching.user.enums.Hobby;
import comatching.comatching.user.enums.Major;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Getter
@Setter
public class ModifyDetailReq {
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
