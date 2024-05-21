package comatching.comatcing.comatching.service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import comatching.comatcing.comatching.dto.MatchRes;
import comatching.comatcing.user.entity.UserInfo;
import comatching.comatcing.user.repository.UserInfoRepository;
import comatching.comatcing.util.ResponseCode;
import comatching.comatcing.util.exception.BusinessException;

@Service
public class ComatchingAiConnectService {
	private final UserInfoRepository userInfoRepository;

	@Value("${spring.command.ai.start}")
	String startAiCommand;

	@Value("${spring.result-file.path}")
	String resultPath;

	ComatchingAiConnectService(UserInfoRepository userInfoRepository) {
		this.userInfoRepository = userInfoRepository;
	}

	public MatchRes requestMatch() {
		String enemyUsername;

		try {
			Process process = Runtime.getRuntime().exec(startAiCommand);

			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			BufferedReader br = new BufferedReader(new FileReader(resultPath));

			// readLine 메서드를 사용하여 파일의 첫 줄을 읽습니다
			enemyUsername = br.readLine();

/*
			enemyUsername = reader.readLine();
			while ((enemyUsername = reader.readLine()) != null) {
				System.out.println(enemyUsername);
			}*/

			System.out.println("[AIConnectService] - match result =" + enemyUsername);
		} catch (IOException e) {
			System.out.println("[AIConnectService] - match fail..");
			throw new BusinessException(ResponseCode.AI_FAIL_MATCH);
		}

		UserInfo enemyUserInfo = userInfoRepository.findByUsername(enemyUsername);
		return MatchRes.fromEnemyEntity(enemyUserInfo);

	/*	//csvHandler.addUser();
		//통신 + 예외처리
		return MatchRes.builder()
			.song("test1")
			.word("test1")
			.mbti("ENFP")
			.contactFrequency(ContactFrequency.NORMAL)
			.hobby(List.of(Hobby.게임, Hobby.독서))
			.age(23)
			.major(Major.공간디자인소비자학과)
			.build();*/
	}
}
