package com.example.comatching_be.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import org.springframework.validation.annotation.Validated;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Data
@Getter @Setter
@Table(name = "user_info")
public class UserInfo {

	@Id
	@GeneratedValue
	@Column(nullable = false)
	private Long id;

	private Boolean gender; //True = male False = female

	@Column(length=11, unique = true)
	private String phone;

	@Column(length=15)
	private String depart;

	private String song;

	private Integer year;

	private String mbti;

	private Integer choose;

	private Integer chance;
}
