package comatching.comatcing.comatching.service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import comatching.comatcing.comatching.dto.MatchCodeAdminReq;
import comatching.comatcing.comatching.dto.RequestMapDto;
import comatching.comatcing.comatching.enums.ReqState;
import comatching.comatcing.util.ResponseCode;
import comatching.comatcing.util.exception.BusinessException;

@Component
public class ComatchingRequestHandler {
	private HashMap<String, RequestMapDto> reqMap = new HashMap<String, RequestMapDto>();

	public String generateMatchCode(Long userId) {
		String newCode = UUID.randomUUID().toString().replace("-", "");
		RequestMapDto requestMapDto = RequestMapDto.builder()
			.userId(userId)
			.registerTime(LocalDateTime.now())
			.reqState(ReqState.REQ)
			.build();

		Iterator<Map.Entry<String, RequestMapDto>> iterator = reqMap.entrySet().iterator();

		while (iterator.hasNext()) {
			Map.Entry<String, RequestMapDto> entry = iterator.next();
			if (entry.getValue().getUserId().equals(userId)) {
				iterator.remove(); // 현재 요소를 안전하게 제거
			}
		}
		reqMap.put(newCode, requestMapDto);
		printMap();
		return newCode;
	}

	public Long checkMatchCode(MatchCodeAdminReq req) {

		if (reqMap.containsKey(req.getMatchCode())) {
			RequestMapDto dto = reqMap.get(req.getMatchCode());
			dto.setReqState(ReqState.VALID);
			printMap();
			return dto.getUserId();
		} else {
			throw new BusinessException(ResponseCode.MATCH_CODE_NOT_FOUND);
		}
	}

	public void updateReqRegisterTime(String matchCode) {
		if (reqMap.containsKey(matchCode)) {
			reqMap.get(matchCode).setRegisterTime(LocalDateTime.now());
			printMap();
		} else {
			System.out.println("[Match] - 결과 응답후 날짜 업데이트 안됨!");
		}
	}

	@Scheduled(fixedDelay = 600000, initialDelay = 600000)
	public void removeSchedule() {
		reqMap.forEach((matchCode, requestMapDto) -> {
			LocalDateTime tenMinutesAgo = LocalDateTime.now().minusMinutes(10);
			if (requestMapDto.getRegisterTime().isBefore(tenMinutesAgo)) {
				reqMap.remove(matchCode);
			}
		});
	}

	public void printMap() {
		int i = 0;
		System.out.println("====== request handle ======");
		for (Map.Entry<String, RequestMapDto> entry : reqMap.entrySet()) {
			System.out.println("[" + i + "] " + entry.getKey() + entry.getValue().getUserId() + ": " + entry.getValue()
				.getReqState()
				.toString());
		}

	}
}
