package com.example.comatching_be.domain;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Data
@Getter
@Setter
@Table(name = "user_info")
public class UserInfo {

	@Id
	@GeneratedValue
	private Long id;

	private Boolean gender; //True = male False = female

	@Column(length = 30, unique = true)
	private String phone;

	@Column(length = 15)
	private String depart;

	@Column(length = 30)
	private String song;

	private Integer year;

	@Column(length = 4)
	private String mbti;

	private Integer choose;

	private Integer chance;

	@Column(length = 6)
	private String passwd;

	// @ElementCollection
	// @CollectionTable(name = "match_info", joinColumns = @JoinColumn(name = "matches"))
	// @Column(name = "match_info")
	// private List<Long> matches;

	@OneToMany(mappedBy = "userId")
	private List<MatchInfo> matchInfos;

	public void addMatchInfo(MatchInfo matchInfo) {
		matchInfos.add(matchInfo);
	}

	private Integer chanceAccrue;
}
