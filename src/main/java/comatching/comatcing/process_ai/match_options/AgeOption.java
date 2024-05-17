package comatching.comatcing.process_ai.match_options;

import lombok.Getter;

@Getter
public enum AgeOption {
	YOUNGER(0),
	EQUAL(1),
	OLDER(2);

	private final Integer vector;

	AgeOption(Integer vector) {
		this.vector = vector;
	}

	public static String toCsvValue(AgeOption option) {
		if (option == null)
			return "";
		else
			return option.getVector().toString();
	}
}
