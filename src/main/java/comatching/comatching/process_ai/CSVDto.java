package comatching.comatching.process_ai;

import comatching.comatching.process_ai.match_options.AgeOption;
import comatching.comatching.process_ai.match_options.ContactFrequencyOption;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CSVDto {
	//User
	private String idx;
	private Integer gender;
	private Integer age;
	private String mbti;
	private Integer major;
	private String hobby;
	private String contact_frequency;

	//Options
	private String mbtiOption;
	private String hobbyOption;
	private AgeOption ageOption;
	private ContactFrequencyOption contactFrequencyOption;
	private Boolean majorOption;

	//Duplication
	private String duplicationUsers;

}



