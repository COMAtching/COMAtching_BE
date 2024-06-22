package comatching.comatching.comatching.service;

import org.springframework.stereotype.Service;

import comatching.comatching.comatching.dto.GetMatchCodeRes;
import comatching.comatching.comatching.dto.ValidUserMatchCodeRes;
import comatching.comatching.security.SecurityUtil;
import comatching.comatching.util.Response;
import comatching.comatching.comatching.dto.MatchReq;
import comatching.comatching.comatching.dto.MatchRes;
import comatching.comatching.process_ai.CSVHandler;

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
		csvHandler.match(req, pickerUsername);  //csv에 match유저 추가
		MatchRes res = comatchingAiConnectService.requestMatch(); //AI한테 결과 받아오기
		comatchingMemberService.requestMatch(req, res, pickerUsername);  //포인트 차감
		comatchingRequestHandler.updateReqRegisterTime(req.getMatchCode());  //
		return Response.ok(res);
	}
}
