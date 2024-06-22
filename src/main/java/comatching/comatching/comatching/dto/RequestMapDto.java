package comatching.comatching.comatching.dto;

import java.time.LocalDateTime;

import comatching.comatching.comatching.enums.ReqState;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class RequestMapDto {
	String username;
	LocalDateTime registerTime;
	ReqState reqState;
}
