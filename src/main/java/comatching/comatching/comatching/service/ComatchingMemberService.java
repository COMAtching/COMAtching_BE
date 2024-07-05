package comatching.comatching.comatching.service;

import org.springframework.stereotype.Service;

import comatching.comatching.Feedback.entity.Feedback;
import comatching.comatching.Feedback.enums.FeedbackState;
import comatching.comatching.Feedback.repository.FeedbackRepository;
import comatching.comatching.comatching.dto.MatchReq;
import comatching.comatching.comatching.dto.MatchRes;
import comatching.comatching.comatching.entity.ComatchHistory;
import comatching.comatching.comatching.repository.ComatchHistoryRepository;
import comatching.comatching.point.entity.PointHistory;
import comatching.comatching.point.enums.PointHistoryType;
import comatching.comatching.point.repository.PointHistoryRepository;
import comatching.comatching.process_ai.CSVHandler;
import comatching.comatching.user.entity.UserInfo;
import comatching.comatching.user.repository.UserInfoRepository;
import comatching.comatching.util.ResponseCode;
import comatching.comatching.util.exception.BusinessException;
import jakarta.transaction.Transactional;

@Service
public class ComatchingMemberService {

	private final UserInfoRepository userInfoRepository;
	private final CSVHandler csvHandler;
	private final ComatchHistoryRepository comatchHistoryRepository;
	private final PointHistoryRepository pointHistoryRepository;

	private final FeedbackRepository feedbackRepository;

	public ComatchingMemberService(UserInfoRepository userInfoRepository, CSVHandler csvHandler,
		ComatchHistoryRepository comatchHistoryRepository, PointHistoryRepository pointHistoryRepository,
		FeedbackRepository feedbackRepository) {
		this.userInfoRepository = userInfoRepository;
		this.csvHandler = csvHandler;
		this.comatchHistoryRepository = comatchHistoryRepository;
		this.pointHistoryRepository = pointHistoryRepository;
		this.feedbackRepository = feedbackRepository;

	}

	public Integer getUserPoint(String username) {
		return userInfoRepository.findByUsername(username).getPoint();
	}

	/**
	 * 매칭 요청에 대한 포인트, pickMe 기회 계산 & 매칭,포인트 history 생성
	 * @Autthor : greensnapback0229
	 * @param req : 매칭 피처
	 * @param res : 매칭 응답
	 * @param pickerUsername : 뽑은 유저 이름
	 */
	@Transactional
	public void requestMatch(MatchReq req, MatchRes res, String pickerUsername) {
		UserInfo enemyInfo = userInfoRepository.findByUsername(res.getEnemyUsername());
		UserInfo pickerInfo = userInfoRepository.findByUsername(pickerUsername);

		Integer usePoint = req.getAiOptionCount() * 100
			+ 500
			+ (req.getNoSameMajorOption() ? 200 : 0);
		Integer beforeWork = pickerInfo.getPoint();
		Integer afterWork = pickerInfo.getPoint() - usePoint;

		if (usePoint > pickerInfo.getPoint()) {
			throw new BusinessException(ResponseCode.SHORT_OF_POINT);
		}

		enemyInfo.updatePickMe(
			enemyInfo.getPickMe() - 1
		);
		pickerInfo.updatePoint(afterWork);

		res.setCurrentPoint(afterWork);

		Feedback feedback = Feedback.builder()
			.feedbackState(FeedbackState.IN_PROGRESS)
			.build();

		feedbackRepository.save(feedback);

		System.out.println("[MemberService] - req.hobby=" + req.getHobbyOption().toString());
		ComatchHistory comatchHistory = ComatchHistory.builder()
			.userInfo(pickerInfo)
			.enemyInfo(enemyInfo)
			.feedback(feedback)
			.ageOption(req.getAgeOption())
			.contactFrequencyOption(req.getContactFrequencyOption())
			.hobbyOption(req.getHobbyOption())
			.mbtiOption(req.getMbtiOption())
			.noSameMajorOption(req.getNoSameMajorOption())
			.build();

		PointHistory pointHistory = PointHistory.builder()
			.payType(PointHistoryType.PAY)
			.comatchHistory(comatchHistory)
			.beforeWork(beforeWork)
			.afterWork(afterWork)
			.pointAmount(usePoint)
			.build();

		userInfoRepository.save(pickerInfo);
		userInfoRepository.save(enemyInfo);
		comatchHistoryRepository.save(comatchHistory);
		pointHistoryRepository.save(pointHistory);
	}
}
