package comatching.comatcing.process_ai.match_options;

import lombok.Getter;

@Getter
public enum ContactFrequencyOption {
	FREQUENT(2),
	NORMAL(1),
	NOT_FREQUENT(0);

	private final Integer vector;

	ContactFrequencyOption(Integer vector) {
		this.vector = vector;
	}

	public static String toCsvValue(ContactFrequencyOption option) {
		if (option == null)
			return "";
		else
			return option.getVector().toString();
	}

}
