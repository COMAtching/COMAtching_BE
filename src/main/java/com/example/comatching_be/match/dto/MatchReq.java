package com.example.comatching_be.match.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class MatchReq {

	@NotNull
	private Boolean gender;

	@NotBlank
	private String mbti;

	@NotBlank
	private String passwd;
}