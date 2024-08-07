package comatching.comatching.comatching.service;

import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

import comatching.comatching.comatching.dto.RequestMapDto;
import comatching.comatching.comatching.enums.ReqState;
import comatching.comatching.util.ResponseCode;
import comatching.comatching.util.exception.BusinessException;

@Component
public class ComatchingRequestHandler {
	private Map<String, RequestMapDto> reqMap = new ConcurrentHashMap<>();

	public String generateMatchCode(String username) {
		removeSchedule();
		String newCode = UUID.randomUUID().toString().replace("-", "");
		RequestMapDto requestMapDto = RequestMapDto.builder()
			.username(username)
			.registerTime(LocalDateTime.now())
			.reqState(ReqState.REQ)
			.build();

		Iterator<Map.Entry<String, RequestMapDto>> iterator = reqMap.entrySet().iterator();

		while (iterator.hasNext()) {
			Map.Entry<String, RequestMapDto> entry = iterator.next();
			if (entry.getValue().getUsername().equals(username)) {
				iterator.remove();
			}
		}
		reqMap.put(newCode, requestMapDto);
		return newCode;
	}

	public String checkMatchCode(String code) {
		removeSchedule();
		if (reqMap.containsKey(code)) {
			RequestMapDto dto = reqMap.get(code);
			dto.setReqState(ReqState.VALID);
			//printMap();
			return dto.getUsername();
		} else {
			throw new BusinessException(ResponseCode.MATCH_CODE_NOT_FOUND);
		}

	}

	public void updateReqRegisterTime(String matchCode) {
		removeSchedule();
		if (reqMap.containsKey(matchCode)) {
			reqMap.get(matchCode).setRegisterTime(LocalDateTime.now());
			//printMap();
		} else {
			System.out.println("[Match] - 결과 응답후 날짜 업데이트 안됨!");
		}
	}

	public String getUsernameForMatch(String matchCode) {
		RequestMapDto dto = reqMap.get(matchCode);
		if (dto.getReqState().equals(ReqState.VALID)) {
			return dto.getUsername();
		} else {
			throw new BusinessException(ResponseCode.MATCH_NOT_ALLOW);
		}
	}

	private void removeSchedule() {
		LocalDateTime tenMinutesAgo = LocalDateTime.now().minusMinutes(10);
		Iterator<Map.Entry<String, RequestMapDto>> iterator = reqMap.entrySet().iterator();

		while (iterator.hasNext()) {
			Map.Entry<String, RequestMapDto> entry = iterator.next();
			if (entry.getValue().getRegisterTime().isBefore(tenMinutesAgo)) {
				iterator.remove();
			}
		}
	}

	public void printMap() {
		int i = 0;
		System.out.println("====== request handle ======");
		for (Map.Entry<String, RequestMapDto> entry : reqMap.entrySet()) {
			System.out.println(
				"[" + i + "] " + entry.getKey() + entry.getValue().getUsername() + ": " + entry.getValue()
					.getReqState()
					.toString());
			i++;
		}
	}
}
