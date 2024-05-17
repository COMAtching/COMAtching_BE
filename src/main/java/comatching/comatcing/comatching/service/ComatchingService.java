package comatching.comatcing.comatching.service;

import org.springframework.stereotype.Service;

import comatching.comatcing.comatching.dto.GetMatchCodeRes;
import comatching.comatcing.comatching.dto.MatchCodeAdminReq;
import comatching.comatcing.comatching.dto.MatchReq;
import comatching.comatcing.comatching.dto.MatchRes;
import comatching.comatcing.comatching.dto.ValidUserMatchCodeRes;
import comatching.comatcing.process_ai.CSVHandler;
import comatching.comatcing.util.Response;

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

	public Response<GetMatchCodeRes> getMatchCode() {
		//todo: context 홀더에서 유저 아이디 가져오기
		GetMatchCodeRes res = new GetMatchCodeRes(comatchingRequestHandler.generateMatchCode(1L));
		return Response.ok(res);
	}

	public Response<ValidUserMatchCodeRes> validUserMatchCode(MatchCodeAdminReq req) {
		comatchingRequestHandler.checkMatchCode(req);
		ValidUserMatchCodeRes res = new ValidUserMatchCodeRes(comatchingMemberService.getUserPoint());
		return Response.ok(res);
	}

	public Response<MatchRes> requestMatch(MatchReq req) {
		MatchRes res = comatchingAiConnectService.requestMatch(req);
		comatchingMemberService.requestMatch(req, res);
		comatchingRequestHandler.updateReqRegisterTime(req.getMatchCode());
		return Response.ok(res);
	}

}
