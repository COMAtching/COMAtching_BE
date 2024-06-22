package comatching.comatching.user.service;

import org.springframework.stereotype.Service;

import comatching.comatching.admin.service.ChargeRequestHandler;
import comatching.comatching.security.SecurityUtil;
import comatching.comatching.security.jwt.JWTUtil;
import comatching.comatching.user.repository.UserAiFeatureRepository;
import comatching.comatching.user.repository.UserInfoRepository;
import comatching.comatching.process_ai.CSVHandler;
import comatching.comatching.user.dto.DuplicationCheckRes;
import comatching.comatching.user.dto.MainResponse;
import comatching.comatching.user.dto.RegisterDetailReq;
import comatching.comatching.user.dto.RegisterDetailRes;
import comatching.comatching.user.entity.UserAiFeature;
import comatching.comatching.user.entity.UserInfo;
import comatching.comatching.util.Response;
import jakarta.transaction.Transactional;

@Service
public class UserService {

	UserInfoRepository userInfoRepository;
	UserAiFeatureRepository userAiFeatureRepository;
	CSVHandler csvHandler;
	JWTUtil jwtUtil;
	ChargeRequestHandler chargeRequestHandler;

	public UserService(UserInfoRepository userInfoRepository, CSVHandler csvHandler,
		UserAiFeatureRepository userAiFeatureRepository,
		JWTUtil jwtUtil, ChargeRequestHandler chargeRequestHandler
	) {
		this.userInfoRepository = userInfoRepository;
		this.userAiFeatureRepository = userAiFeatureRepository;
		this.csvHandler = csvHandler;
		this.jwtUtil = jwtUtil;
		this.chargeRequestHandler = chargeRequestHandler;
	}

	public Response registerUserDetail(RegisterDetailReq req) {
		String username = SecurityUtil.getContextUserInfo().getUsername();
		saveUserDetail(req);
		csvHandler.addUser(userInfoRepository.findByUsername(username));
		RegisterDetailRes res = new RegisterDetailRes(
			jwtUtil.createJwt(
				SecurityUtil.getContextUserInfo().getUsername(),
				"ROLE_USER",
				21600000L
			));
		return Response.ok(res);
	}

	public Response<DuplicationCheckRes> duplicationCheck(String contactId, String contactType) {
		contactId = contactType.equals("instagram") ? "@" + contactId : contactId;
		Boolean isDuplication = userInfoRepository.existsByContactId(contactId);
		System.out.println("[UserService] - duplicationCheck.isDuplicated=" + isDuplication);
		DuplicationCheckRes res = new DuplicationCheckRes(isDuplication);
		return Response.ok(res);
	}

	public Response<MainResponse> getMainInfo() {
		String username = SecurityUtil.getContextUserInfo().getUsername();
		UserInfo userInfo = userInfoRepository.findByUsername(username);
		Long participation = userInfoRepository.count();

		return Response.ok(MainResponse.builder()
			.major(userInfo.getUserAiFeature().getMajor())
			.contactId(userInfo.getContactId())
			.mbti(userInfo.getUserAiFeature().getMbti())
			.hobbyList(userInfo.getUserAiFeature().getHobby())
			.contactFrequency(userInfo.getUserAiFeature().getContactFrequency().getValue())
			.song(userInfo.getSong())
			.participation(participation)
			.leftPoint(userInfo.getPoint())
			.pickMe(userInfo.getPickMe())
			.age(userInfo.getUserAiFeature().getAge())
			.comment(userInfo.getComment())
			.build());
	}

	public Response addChargeRequest() {
		String username = SecurityUtil.getContextUserInfo().getUsername();
		String contactId = userInfoRepository.findByUsername(username).getContactId();
		chargeRequestHandler.addRequest(contactId);
		return Response.ok();
	}

	@Transactional
	public void saveUserDetail(RegisterDetailReq req) {
		UserInfo userInfo = userInfoRepository.findByUsername(SecurityUtil.getContextUserInfo().getUsername());
		UserAiFeature userAiFeature = UserAiFeature.builder()
			.major(req.getMajor())
			.gender(req.getGender())
			.age(req.getAge())
			.mbti(req.getMbti())
			.hobby(req.getHobby())
			.contactFrequency(req.getContactFrequency())
			.build();

		userInfo.updateSocialToUser(
			req.getContactId(),
			req.getSong(),
			req.getComment(),
			userAiFeature,
			"ROLE_USER",
			0,
			1
		);
		userInfoRepository.save(userInfo);
	}
}
