package comatching.comatching.comatching.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import comatching.comatching.comatching.dto.FeedbackReq;
import comatching.comatching.comatching.dto.HistoryListData;
import comatching.comatching.comatching.dto.UserComatchHistoryRes;
import comatching.comatching.comatching.entity.ComatchHistory;
import comatching.comatching.comatching.repository.ComatchHistoryRepository;
import comatching.comatching.process_ai.CSVHandler;
import comatching.comatching.security.SecurityUtil;
import comatching.comatching.user.entity.UserInfo;
import comatching.comatching.user.repository.UserInfoRepository;
import comatching.comatching.util.Response;
import comatching.comatching.util.ResponseCode;
import comatching.comatching.util.exception.BusinessException;
import jakarta.transaction.Transactional;

@Service
public class HistoryService {
	ComatchHistoryRepository comatchHistoryRepository;
	UserInfoRepository userInfoRepository;
	CSVHandler csvHandler;

	HistoryService(ComatchHistoryRepository comatchHistoryRepository,
		CSVHandler csvHandler, UserInfoRepository userInfoRepository) {
		this.comatchHistoryRepository = comatchHistoryRepository;
		this.csvHandler = csvHandler;
		this.userInfoRepository = userInfoRepository;
	}

	public Response<UserComatchHistoryRes> getHistoryList() {
		String username = SecurityUtil.getContextUserInfo().getUsername();
		List<ComatchHistory> comatchHistoryList = comatchHistoryRepository.findByUserInfoUsername(username);
		if (comatchHistoryList == null) {
			throw new BusinessException(ResponseCode.NO_HISTORY);
		}

		List<HistoryListData> historyList = new ArrayList<>();
		for (ComatchHistory comatchHistory : comatchHistoryList) {
			historyList.add(
				HistoryListData.fromComatchHistory(comatchHistory)
			);
		}

		UserComatchHistoryRes res = new UserComatchHistoryRes(historyList);
		return Response.ok(res);
	}

	@Transactional
	public Response writeFeedback(FeedbackReq req) {
		ComatchHistory history = comatchHistoryRepository.findById(req.getHistoryId()).orElseThrow(
			() -> new BusinessException(ResponseCode.NO_HISTORY)
		);
		history.getFeedback().writeFeedback(Double.valueOf(req.getGrade()));

		UserInfo userInfo = userInfoRepository.findByUsername(history.getUserInfo().getUsername());
		userInfo.updatePickMe(
			userInfo.getPickMe() + 1
		);
		csvHandler.addPickMeCount(userInfo.getUsername(), 1);
		comatchHistoryRepository.save(history);
		userInfoRepository.save(userInfo);
		return Response.ok();
	}

}
