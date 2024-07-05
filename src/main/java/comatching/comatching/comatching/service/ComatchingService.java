package comatching.comatching.comatching.service;

import org.springframework.stereotype.Service;

import comatching.comatching.comatching.dto.GetMatchCodeRes;
import comatching.comatching.comatching.dto.MatchReq;
import comatching.comatching.comatching.dto.MatchRes;
import comatching.comatching.comatching.dto.ValidUserMatchCodeRes;
import comatching.comatching.process_ai.CSVHandler;
import comatching.comatching.security.SecurityUtil;
import comatching.comatching.util.Response;

@Service
public class ComatchingService {
	private final ComatchingRequestHandler comatchingRequestHandler;
	private final ComatchingMemberService comatchingMemberService;
	private final ComatchingAiConnectService comatchingAiConnectService;
	private final CSVHandler csvHandler;

	public ComatchingService(ComatchingRequestHandler comatchingRequestHandler,
		ComatchingMemberService comatchingMemberService,
		ComatchingAiConnectService comatchingAiConnectService,
		CSVHandler csvHandler) {
		this.comatchingRequestHandler = comatchingRequestHandler;
		this.comatchingMemberService = comatchingMemberService;
		this.comatchingAiConnectService = comatchingAiConnectService;
		this.csvHandler = csvHandler;
	}

	/**
	 * QR에 쓰일 코드 handler를 통한 등록 및 발급
	 * @return 매칭 요청 코드 반환
	 */
	public Response<GetMatchCodeRes> getMatchCode() {
		String username = SecurityUtil.getContextUserInfo().getUsername();
		GetMatchCodeRes res = new GetMatchCodeRes(comatchingRequestHandler.generateMatchCode(username));
		return Response.ok(res);
	}

	/**
	 * 사용자 요청 코드 검증
	 * @param code : 사용자 요청 코드
	 * @return 코드 검증 성공시, 사용자 잔여 포인트 반환
	 */
	public Response<ValidUserMatchCodeRes> validUserMatchCode(String code) {
		String username = comatchingRequestHandler.checkMatchCode(code);
		ValidUserMatchCodeRes res = new ValidUserMatchCodeRes(comatchingMemberService.getUserPoint(username));
		return Response.ok(res);
	}

	/**
	 * 매칭 처리
	 * 매치
	 * @param req : 매칭을 위한 상대방 피처
	 * @return : 결과창에서 쓰일 매칭 상대에 대한 피처 정보
	 */
	public Response<MatchRes> requestMatch(MatchReq req) {
		String pickerUsername = comatchingRequestHandler.getUsernameForMatch(req.getMatchCode());
		csvHandler.match(req, pickerUsername);
		MatchRes res = comatchingAiConnectService.requestMatch();
		comatchingMemberService.requestMatch(req, res, pickerUsername);
		comatchingRequestHandler.updateReqRegisterTime(req.getMatchCode());
		return Response.ok(res);
	}
}
