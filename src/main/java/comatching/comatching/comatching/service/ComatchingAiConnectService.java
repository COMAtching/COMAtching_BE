package comatching.comatching.comatching.service;

import java.io.BufferedReader;
import java.io.FileReader;
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

	public synchronized MatchRes requestMatch() {
		String enemyUsername;

		try {
			Process process = Runtime.getRuntime().exec(startAiCommand);

			process.waitFor();
			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			BufferedReader br = new BufferedReader(new FileReader(resultPath));

			String enemyUsernameBr = br.readLine();

			// readLine 메서드를 사용하여 파일의 첫 줄을 읽습니다
			String line;

			//reader.readLine();
			enemyUsername = reader.readLine();
			System.out.println("[AIConnectService] - match readerBuffer =" + enemyUsername);
			System.out.println("[AIConnectService] - match resultFile =" + enemyUsernameBr);

		} catch (IOException | InterruptedException e) {
			System.out.println("[AIConnectService] - match fail..");
			throw new BusinessException(ResponseCode.AI_FAIL_MATCH);
		}

		UserInfo enemyUserInfo = userInfoRepository.findByUsername(enemyUsername);
		return MatchRes.fromEnemyEntity(enemyUserInfo);
	}
}
