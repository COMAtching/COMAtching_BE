package comatching.comatcing.comatching.dto;

import java.time.LocalDateTime;

import comatching.comatcing.comatching.enums.ReqState;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class RequestMapDto {
	String username;
	LocalDateTime registerTime;
	ReqState reqState;
}
