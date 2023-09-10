package com.example.comatching_be.match.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class MatchRes {
	private Boolean gender;
	private String phone;
	private String depart;
	private String song;
	private Integer year;
	private String mbti;
}
