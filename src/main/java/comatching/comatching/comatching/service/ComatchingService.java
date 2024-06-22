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

	public Response<GetMatchCodeRes> getMatchCode() {
		String username = SecurityUtil.getContextUserInfo().getUsername();
		GetMatchCodeRes res = new GetMatchCodeRes(comatchingRequestHandler.generateMatchCode(username));
		return Response.ok(res);
	}

	public Response<ValidUserMatchCodeRes> validUserMatchCode(String code) {
		String username = comatchingRequestHandler.checkMatchCode(code);
		ValidUserMatchCodeRes res = new ValidUserMatchCodeRes(comatchingMemberService.getUserPoint(username));
		return Response.ok(res);
	}

	public Response<MatchRes> requestMatch(MatchReq req) {
		String pickerUsername = comatchingRequestHandler.getUsernameForMatch(req.getMatchCode());
		csvHandler.match(req, pickerUsername);
		MatchRes res = comatchingAiConnectService.requestMatch();
		comatchingMemberService.requestMatch(req, res, pickerUsername);
		comatchingRequestHandler.updateReqRegisterTime(req.getMatchCode());
		return Response.ok(res);
	}
}
