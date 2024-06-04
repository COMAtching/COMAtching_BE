package comatching.comatcing.security.jwt;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import comatching.comatcing.security.dto.CustomOAuth2User;
import comatching.comatcing.security.dto.UserDto;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtFilter extends OncePerRequestFilter {

	private final JWTUtil jwtUtil;

	public JwtFilter(JWTUtil jwtUtil) {
		this.jwtUtil = jwtUtil;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
		FilterChain filterChain) throws ServletException, IOException {

		System.out.println(request.getRequestURI());
		if (request.getRequestURI().equals("/participation/count")) {
			filterChain.doFilter(request, response); // "/login" 요청이 들어오면, 다음 필터 호출
			System.out.println("work");
			return;
		}

		//crookie들을 불러온 뒤 Authorization Key에 담긴 쿠키를 찾음
		String authorization = null;
		/*Cookie[] cookies = request.getCookies();
		for (Cookie cookie : cookies) {
			//System.out.println(cookie.getName());
			if (cookie.getName().equals("Authorization")) {
				authorization = cookie.getValue();
			}
		}*/

		authorization = request.getHeader("Authorization");
		System.out.println("[JwtFilter] - get token=" + authorization);

		//Authorization 헤더 검증
		if (authorization == null || authorization.isBlank() || authorization.isEmpty() || authorization.equals(
			"undefined")) {
			System.out.println("[JwtFilter] - token is blank");
			throw new JwtException("INVALID");
			//filterChain.doFilter(request, response);
		}

		//System.out.println("[filterchain] - 널검증 만료검증 사이");

		//토큰
		String token = authorization;
		//토큰 소멸 시간 검증
		if (jwtUtil.isExpired(token)) {
			System.out.println("token expired");
			throw new JwtException("EXPIRE");
			//filterChain.doFilter(request, response);
			//조건이 해당되면 메소드 종료 (필수)
		}
		//System.out.println("[filterchain] - 만료후");

		//토큰에서 username과 role 획득
		String username = jwtUtil.getUsername(token);
		String role = jwtUtil.getRole(token);

		//System.out.println("[filterchain] - 유저이름 역할");

		//userDTO를 생성하여 값 set
		UserDto userDTO = new UserDto();
		userDTO.setUsername(username);
		userDTO.setRole(role);

		//UserDetails에 회원 정보 객체 담기
		CustomOAuth2User customOAuth2User = new CustomOAuth2User(userDTO);

		//스프링 시큐리티 인증 토큰 생성
		Authentication authToken = new UsernamePasswordAuthenticationToken(customOAuth2User, null,
			customOAuth2User.getAuthorities());

		//세션에 사용자 등록
		SecurityContextHolder.getContext().setAuthentication(authToken);
		filterChain.doFilter(request, response);
	}
}
