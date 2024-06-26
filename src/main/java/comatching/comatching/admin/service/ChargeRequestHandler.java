package comatching.comatching.admin.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Component;

import comatching.comatching.user.repository.UserInfoRepository;
import comatching.comatching.admin.dto.ChargeRequestInfo;
import comatching.comatching.user.entity.UserInfo;
import comatching.comatching.util.ResponseCode;
import comatching.comatching.util.exception.BusinessException;

@Component
public class ChargeRequestHandler {

	private final UserInfoRepository userInfoRepository;
	private Set<String> chargeReq = new HashSet<>();

	ChargeRequestHandler(UserInfoRepository userInfoRepository) {
		this.userInfoRepository = userInfoRepository;
	}

	public void addRequest(String contactId) {
		if (chargeReq.contains(contactId)) {
			throw new BusinessException(ResponseCode.CHARGE_ALREADY_REQUESTED);
		} else {
			chargeReq.add(contactId);
		}
	}

	public void finishRequest(String contactId) {
		if (chargeReq.contains(contactId)) {
			chargeReq.remove(contactId);
		} else {
			throw new BusinessException(ResponseCode.CHARGE_REQUEST_IS_NOT_EXIST);
		}
		System.out.println("[chrReqHdlr] - finish =" + contactId);
	}

	public List<ChargeRequestInfo> getChargeRequestList() {

		List<ChargeRequestInfo> chargeRequestList = new ArrayList<>();
		Iterator<String> iterator = chargeReq.iterator();

		while (iterator.hasNext()) {
			String contactId = iterator.next();
			UserInfo userInfo = userInfoRepository.findByContactId(contactId);

			chargeRequestList.add(
				ChargeRequestInfo.builder()
					.point(userInfo.getPoint())
					.contactId(contactId)
					.build()
			);
			System.out.println("[chrReqHdlr] - add =" + contactId);
		}

		if (chargeReq.isEmpty())
			throw new BusinessException(ResponseCode.CHARGE_REQUEST_IS_NOT_EXIST);

		return chargeRequestList;
	}

	public Boolean isExist(String contactId) {
		return chargeReq.contains(contactId);
	}
}
