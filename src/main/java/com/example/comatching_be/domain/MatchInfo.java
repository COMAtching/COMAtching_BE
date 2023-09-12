package com.example.comatching_be.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Data
@Getter
@Setter
@Table(name = "match_info")
public class MatchInfo {
	@Id
	@GeneratedValue
	@Column(nullable = false)
	private Long id;

	@Column(name = "user_id")
	private Long userId;

	@Column(name = "matcher_id")
	private Long matcherId;

}
