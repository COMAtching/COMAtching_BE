package comatching.comatcing.comatching.service;

import java.util.List;

import org.springframework.stereotype.Service;

import comatching.comatcing.comatching.dto.MatchReq;
import comatching.comatcing.comatching.dto.MatchRes;
import comatching.comatcing.process_ai.CSVHandler;
import comatching.comatcing.user.enums.ContactFrequency;
import comatching.comatcing.user.enums.Hobby;
import comatching.comatcing.user.enums.Major;

@Service
public class ComatchingAiConnectService {
	private final CSVHandler csvHandler;

	ComatchingAiConnectService(CSVHandler csvHandler) {
		this.csvHandler = csvHandler;
	}

	public MatchRes requestMatch(MatchReq matchReq) {

		//csvHandler.addUser();
		//통신 + 예외처리
		return MatchRes.builder()
			.song("test1")
			.word("test1")
			.mbti("ENFP")
			.contactFrequency(ContactFrequency.NORMAL)
			.hobby(List.of(Hobby.게임, Hobby.독서))
			.age(23)
			.major(Major.공간디자인소비자학과)
			.build();
	}
}
