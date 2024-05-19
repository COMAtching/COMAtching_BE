package comatching.comatcing.user.dto;

import java.util.List;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import comatching.comatcing.user.enums.Hobby;
import comatching.comatcing.user.enums.Major;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Getter
@Setter
@Builder
public class MainResponse {
	Major major;
	String contactId;
	Integer age;
	String mbti;
	List<Hobby> hobbyList;
	String contactFrequency;
	String song;
	String comment;
	Long participation;
	Integer leftPoint;
	Integer pickMe;

}
