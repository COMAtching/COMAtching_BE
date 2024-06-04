package comatching.comatcing.comatching.dto;

import java.util.List;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import comatching.comatcing.process_ai.match_options.AgeOption;
import comatching.comatcing.process_ai.match_options.ContactFrequencyOption;
import comatching.comatcing.user.enums.Hobby;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class MatchReq {
	private String mbtiOption;
	private ContactFrequencyOption contactFrequencyOption;
	private List<Hobby> hobbyOption;
	private AgeOption ageOption;
	private String matchCode;
	private Integer aiOptionCount;
	private Boolean noSameMajorOption;
}
