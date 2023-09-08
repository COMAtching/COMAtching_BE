package com.example.comatching_be.register.dto;

import javax.persistence.Column;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class RegisterReq { //모든 데이터 NULL Blank 허용x

	@NotNull(message = "성별 - 빈칸일 수 없습니다")
	private Boolean gender; //True = male False = female

	@NotBlank(message = "전화번호 - 빈칸일 수 없습니다")
	@Pattern(regexp = "^010([0-9]{3,4})([0-9]{4})$", message = "전화번호 - 번호 형식이 맞지 않습니다.")
	private String phone;

	@NotBlank(message = "학과명 - 빈칸일 수 없습니다")
	@Size(min=4, max=15, message="학과명 - 최대 길이는 13글자 입니다.")
	private String depart;

	@NotBlank(message = "노래제목 - 빈칸일 수 없습니다")
	@Size(max=30, message="노래제목 - 최대 길이는 30글자 입니다.")
	private String song;

	@NotNull(message = "학번 - 빈칸일 수 없습니다")
	@Max(value=23, message = "학번 - 최대 23학번까지 입니다.")
	@Min(value=01, message = "학번 - 최소 01학번부터 입니다.")
	private Integer year; 			// 1 ~ 23까지만 허용

	@NotBlank(message="MBTI - 빈칸일 수 없습니다")
	@Pattern(regexp = "^[EI][NS][FT][PJ]$", message = "MBTI형식에 맞게 입력하ㅔ요")
	private String mbti;			// 대문자 MBTI만 가능
}