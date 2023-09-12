package com.example.comatching_be.inquiry.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class InquiryRes {

	private String phone;

	private String depart;

	private String song;

	private String mbti;

	private Integer year;

}
