package comatching.comatcing.user.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import lombok.Getter;

@Getter
public enum Major {
	// 인문계열
	국어국문학과(1, "국어국문학과"),
	철학과(2, "철학과"),
	국사학과(3, "국사학과"),

	// 어문계열
	영어영문학부(4, "영어영문학부"),
	중국언어문화학과(5, "중국언어문화학과"),
	일어일본문화학과(6, "일어일본문화학과"),
	프랑스어문화학과(7, "프랑스어문화학과"),

	// 음악과
	음악과(8, "음악과"),
	성악과(9, "성악과"),

	// 종교학과
	종교학과(10, "종교학과"),

	// 사회과학계열
	사회복지학과(11, "사회복지학과"),
	심리학과(12, "심리학과"),
	사회학과(13, "사회학과"),
	특수교육과(14, "특수교육과"),

	// 경영계열
	경영학과(15, "경영학과"),
	회계학과(16, "회계학과"),

	// 국제·법정경계열
	국제학부(17, "국제학부"),
	법학과(18, "법학과"),
	경제학과(19, "경제학과"),
	행정학과(20, "행정학과"),

	// 생활과학계열
	공간디자인소비자학과(21, "공간디자인소비자학과"),
	의류학과(22, "의류학과"),
	아동학과(23, "아동학과"),
	식품영양학과(24, "식품영양학과"),
	의생명과학과(25, "의생명과학과"),

	// 약학대학
	간호대학(26, "간호대학"),
	의과대학(27, "의과대학"),

	// 공학
	// ICT공학계열
	컴퓨터정보공학부(28, "컴퓨터정보공학부"),
	미디어기술콘텐츠학과(29, "미디어기술콘텐츠학과"),
	정보통신전자공학부(30, "정보통신전자공학부"),
	// 바이오융합공학계열
	생명공학과(31, "생명공학과"),
	에너지환경공학과(32, "에너지환경공학과"),
	바이오메디컬화학공학과(33, "바이오메디컬화학공학과"),
	인공지능학과(34, "인공지능학과"),
	데이터사이언스학과(35, "데이터사이언스학과"),
	바이오메디컬소프트웨어학과(36, "바이오메디컬소프트웨어학과"),

	// 자유전공
	자유전공학과(37, "자유전공학과"),
	가톨릭대_아님(38, "가톨릭대_아님");

	private final Integer vector;
	private final String value;

	Major(Integer vector, String value) {
		this.vector = vector;
		this.value = value;
	}

	@JsonCreator
	public static Major fromString(String value) {
		for (Major major : Major.values()) {
			if (major.getValue().equals(value)) {
				return major;
			}
		}
		return null;
	}

	@JsonValue
	public String getValue() {
		return value;
	}
}
