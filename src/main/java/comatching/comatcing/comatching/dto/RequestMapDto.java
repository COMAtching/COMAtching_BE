package comatching.comatcing.comatching.dto;

import java.time.LocalDateTime;

import comatching.comatcing.comatching.enums.ReqState;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class RequestMapDto {
	Long userId;
	LocalDateTime registerTime;
	ReqState reqState;
}
