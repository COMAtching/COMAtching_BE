package comatching.comatcing.comatching.service;

import org.springframework.stereotype.Service;

import comatching.comatcing.Feedback.entity.Feedback;
import comatching.comatcing.Feedback.enums.FeedbackState;
import comatching.comatcing.Feedback.repository.FeedbackRepository;
import comatching.comatcing.comatching.dto.MatchReq;
import comatching.comatcing.comatching.dto.MatchRes;
import comatching.comatcing.comatching.entity.ComatchHistory;
import comatching.comatcing.comatching.repository.ComatchHistoryRepository;
import comatching.comatcing.point.entity.PointHistory;
import comatching.comatcing.point.enums.PointHistoryType;
import comatching.comatcing.point.repository.PointHistoryRepository;
import comatching.comatcing.process_ai.CSVHandler;
import comatching.comatcing.user.entity.UserInfo;
import comatching.comatcing.user.repository.UserInfoRepository;
import comatching.comatcing.util.ResponseCode;
import comatching.comatcing.util.exception.BusinessException;
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

	@Transactional
	public void requestMatch(MatchReq req, MatchRes res, String pickerUsername) {
		UserInfo enemyInfo = userInfoRepository.findByUsername(res.getEnemyUsername());
		UserInfo pickerInfo = userInfoRepository.findByUsername(pickerUsername);

		Integer usePoint = req.getAiOptionCount() * 100
			+ 500
			+ (req.getNoSameMajorOption() ? 200 : 0);
		Integer beforeWork = pickerInfo.getPoint();
		Integer afterWork = pickerInfo.getPoint() - usePoint;

		csvHandler.updatePickMeCount(enemyInfo.getUsername());
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
