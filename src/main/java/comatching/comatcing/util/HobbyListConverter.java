package comatching.comatcing.util;

import java.util.ArrayList;
import java.util.List;

import comatching.comatcing.user.enums.Hobby;
import jakarta.persistence.AttributeConverter;

public class HobbyListConverter implements AttributeConverter<List<Hobby>, String> {

	@Override
	public String convertToDatabaseColumn(List<Hobby> attribute) {
		String result = "";
		for (Hobby hobby : attribute) {
			result.concat(hobby.toString() + ",");
		}
		return result;
	}

	@Override
	public List<Hobby> convertToEntityAttribute(String dbData) {
		List<Hobby> result = new ArrayList<>();
		for (String hobby : dbData.split(",")) {
			if (hobby != "") {
				result.add(Hobby.valueOf(hobby));
			}
		}
		return result;
	}
}
