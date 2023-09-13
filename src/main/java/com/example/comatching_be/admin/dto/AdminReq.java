package com.example.comatching_be.admin.dto;

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
public class AdminReq {
	private String passwd;
	private Integer chance; //pick someone
	private Integer choose; //pick me
}
