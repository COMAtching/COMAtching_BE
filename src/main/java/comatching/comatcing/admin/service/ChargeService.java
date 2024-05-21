package comatching.comatcing.admin.service;

import org.springframework.stereotype.Service;

import comatching.comatcing.admin.dto.ManageChargeReq;
import comatching.comatcing.admin.dto.ManageMainRes;
import comatching.comatcing.point.entity.PointHistory;
import comatching.comatcing.point.enums.PointHistoryType;
import comatching.comatcing.point.repository.PointHistoryRepository;
import comatching.comatcing.user.entity.UserInfo;
import comatching.comatcing.user.repository.UserInfoRepository;
import comatching.comatcing.util.Response;
import comatching.comatcing.util.ResponseCode;
import comatching.comatcing.util.exception.BusinessException;
import jakarta.transaction.Transactional;

@Service
public class ChargeService {

	private final ChargeRequestHandler chargeRequestHandler;
	private final UserInfoRepository userInfoRepository;

	private final PointHistoryRepository pointHistoryRepository;

	public ChargeService(ChargeRequestHandler chargeRequestHandler,
		UserInfoRepository userInfoRepository,
		PointHistoryRepository pointHistoryRepository) {
		this.chargeRequestHandler = chargeRequestHandler;
		this.userInfoRepository = userInfoRepository;
		this.pointHistoryRepository = pointHistoryRepository;
	}

	public Response<ManageMainRes> getChargeRequestList() {
		ManageMainRes res = new ManageMainRes();
		res.setChargeRequestInfoList(
			chargeRequestHandler.getChargeRequestList()
		);
		return Response.ok(res);
	}

	@Transactional
	public Response processCharge(ManageChargeReq req) {
		String contactId = req.getContactId();
		UserInfo userInfo = userInfoRepository.findByContactId(contactId);
		Integer currentPickMe = userInfo.getPickMe();
		Integer dbPoint = userInfo.getPoint();

		if (chargeRequestHandler.isExist(contactId)) {
			if (checkChargeReq(dbPoint, req)) {
				userInfo.updatePoint(req.getResultPoint());
				userInfo.updatePickMe(currentPickMe + req.getAddPickMe());
				userInfoRepository.save(userInfo);
			} else {
				throw new BusinessException(ResponseCode.POINT_NOT_CORRECT);
			}

		} else {
			throw new BusinessException(ResponseCode.CHARGE_REQUEST_IS_NOT_EXIST);
		}

		PointHistory pointHistory = PointHistory.builder()
			.payType(PointHistoryType.CHARGE)
			.addPickMe(req.getAddPickMe())
			.beforeWork(dbPoint)
			.afterWork(req.getResultPoint())
			.pointAmount(req.getResultPoint() - dbPoint)
			.addPickMe(req.getAddPickMe())
			.userInfo(userInfo)
			.build();

		pointHistoryRepository.save(pointHistory);
		chargeRequestHandler.finishRequest(req.getContactId());
		return Response.ok();
	}

	private Boolean checkChargeReq(Integer dbPoint, ManageChargeReq req) {
		if (req.getResultPoint() == dbPoint + req.getAddPoint() - (req.getAddPickMe() * 500)) {
			return true;
		} else {
			return false;
		}

	}
}
