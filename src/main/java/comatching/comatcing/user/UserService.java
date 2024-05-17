package comatching.comatcing.user;

import java.util.UUID;

import org.springframework.stereotype.Service;

import comatching.comatcing.process_ai.CSVHandler;
import comatching.comatcing.user.dto.RegisterDetailReq;
import comatching.comatcing.user.entity.UserAiFeature;
import comatching.comatcing.user.entity.UserInfo;
import comatching.comatcing.user.repository.UserAiFeatureRepository;
import comatching.comatcing.user.repository.UserInfoRepository;
import comatching.comatcing.util.Response;
import jakarta.transaction.Transactional;

@Service
public class UserService {

	UserInfoRepository userInfoRepository;
	UserAiFeatureRepository userAiFeatureRepository;
	CSVHandler csvHandler;

	public UserService(UserInfoRepository userInfoRepository, CSVHandler csvHandler,
		UserAiFeatureRepository userAiFeatureRepository
	) {
		this.userInfoRepository = userInfoRepository;
		this.userAiFeatureRepository = userAiFeatureRepository;
		this.csvHandler = csvHandler;
	}

	public Response registerUserDetail(RegisterDetailReq req) {
		String temp_name = saveUserDetail(req);
		csvHandler.addUser(userInfoRepository.findByUsername(temp_name));
		return Response.ok();
	}

	//todo:username 임시 설정함 변경해야됨 나중에
	@Transactional
	public String saveUserDetail(RegisterDetailReq req) {
		UserAiFeature userAiFeature = UserAiFeature.builder()
			.major(req.getMajor())
			.gender(req.getGender())
			.age(req.getAge())
			.mbti(req.getMbti())
			.hobby(req.getHobby())
			.contactFrequency(req.getContactFrequency())
			//.userInfo(userInfo)
			.build();
		String temp_name = UUID.randomUUID().toString();

		UserInfo userInfo = UserInfo.builder()
			.contactId(req.getContactId())
			.song(req.getSong())
			.comment(req.getComment())
			.userAiFeature(userAiFeature)
			.username(temp_name)
			.build();

		userInfoRepository.save(userInfo);
		return temp_name;
	}
}
