package comatching.comatching.admin.service;

import org.springframework.stereotype.Service;

import comatching.comatching.point.entity.PointHistory;
import comatching.comatching.point.enums.PointHistoryType;
import comatching.comatching.point.repository.PointHistoryRepository;
import comatching.comatching.user.repository.UserInfoRepository;
import comatching.comatching.admin.dto.ManageChargeReq;
import comatching.comatching.admin.dto.ManageMainRes;
import comatching.comatching.process_ai.CSVHandler;
import comatching.comatching.user.entity.UserInfo;
import comatching.comatching.util.Response;
import comatching.comatching.util.ResponseCode;
import comatching.comatching.util.exception.BusinessException;
import jakarta.transaction.Transactional;

@Service
public class ChargeService {

	private final ChargeRequestHandler chargeRequestHandler;
	private final UserInfoRepository userInfoRepository;

	private final PointHistoryRepository pointHistoryRepository;

	private final CSVHandler csvHandler;

	public ChargeService(ChargeRequestHandler chargeRequestHandler,
		UserInfoRepository userInfoRepository,
		PointHistoryRepository pointHistoryRepository, CSVHandler csvHandler) {
		this.chargeRequestHandler = chargeRequestHandler;
		this.userInfoRepository = userInfoRepository;
		this.pointHistoryRepository = pointHistoryRepository;
		this.csvHandler = csvHandler;
	}

	public Response finishReq(String contactId) {
		chargeRequestHandler.finishRequest(contactId);
		return Response.ok();
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
		csvHandler.addPickMeCount(userInfo.getUsername(), req.getAddPickMe());
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
