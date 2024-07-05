package comatching.comatching.comatching.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import comatching.comatching.comatching.dto.MatchRes;
import comatching.comatching.user.entity.UserInfo;
import comatching.comatching.user.repository.UserInfoRepository;
import comatching.comatching.util.ResponseCode;
import comatching.comatching.util.exception.BusinessException;

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

	/**
	 * AI를 실행 후 프롬프트의 결과 값을 인식(사용자 uuid)
	 * @return : 결과로 나온 사용자의 결과창에 필요한 정보
	 */
	public synchronized MatchRes requestMatch() {
		String enemyUsername;

		try {
			Process process = Runtime.getRuntime().exec(startAiCommand);

			process.waitFor();
			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

			enemyUsername = reader.readLine();

		} catch (IOException | InterruptedException e) {
			throw new BusinessException(ResponseCode.AI_FAIL_MATCH);
		}

		UserInfo enemyUserInfo = userInfoRepository.findByUsername(enemyUsername);
		return MatchRes.fromEnemyEntity(enemyUserInfo);
	}
}
