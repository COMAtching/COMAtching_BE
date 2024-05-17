package comatching.comatcing.comatching.service;

import org.springframework.stereotype.Service;

import comatching.comatcing.comatching.dto.MatchReq;
import comatching.comatcing.comatching.dto.MatchRes;
import comatching.comatcing.user.entity.UserInfo;
import comatching.comatcing.user.repository.UserInfoRepository;
import comatching.comatcing.util.ResponseCode;
import comatching.comatcing.util.exception.BusinessException;
import jakarta.transaction.Transactional;

@Service
public class ComatchingMemberService {

	private final UserInfoRepository userInfoRepository;

	public ComatchingMemberService(UserInfoRepository userInfoRepository) {
		this.userInfoRepository = userInfoRepository;
	}

	public Integer getUserPoint() {
		return userInfoRepository.findById(1L).get().getPoint();
	}

	@Transactional
	public void requestMatch(MatchReq req, MatchRes res) {
		UserInfo userInfo = userInfoRepository.findByUsername(res.getUsername());

		Integer usePoint = req.getAiOptionCount() * 100
			+ 500
			+ (req.getMajorOptionCheck() ? 200 : 0);

		if (userInfo.getPickMe() - 1 == 0) {
			//Todo: redis cache 목록에서 삭제
		}

		if (usePoint > userInfo.getPoint()) {
			throw new BusinessException(ResponseCode.SHORT_OF_POINT);
		}

		userInfo.updatePointPickMe(
			userInfo.getPoint() - usePoint,
			userInfo.getPickMe() - 1
		);
		userInfoRepository.save(userInfo);
	}

}
