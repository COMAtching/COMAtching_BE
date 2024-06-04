package comatching.comatcing.user.service;

import org.springframework.stereotype.Service;

import comatching.comatcing.admin.service.ChargeRequestHandler;
import comatching.comatcing.process_ai.CSVHandler;
import comatching.comatcing.security.SecurityUtil;
import comatching.comatcing.security.jwt.JWTUtil;
import comatching.comatcing.user.dto.DuplicationCheckRes;
import comatching.comatcing.user.dto.MainResponse;
import comatching.comatcing.user.dto.RegisterDetailReq;
import comatching.comatcing.user.dto.RegisterDetailRes;
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
