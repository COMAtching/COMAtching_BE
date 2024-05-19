package comatching.comatcing.security.oauth2;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import comatching.comatcing.security.dto.CustomOAuth2User;
import comatching.comatcing.security.jwt.JWTUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
	private final JWTUtil jwtUtil;

	@Value("${spring.jwt.login.success.redirection.link}")
	private String redirectionURI;

	public CustomSuccessHandler(JWTUtil jwtUtil) {
		this.jwtUtil = jwtUtil;
	}

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
		Authentication authentication) throws
		IOException, ServletException {

		CustomOAuth2User customUserDetails = (CustomOAuth2User)authentication.getPrincipal();
		String username = customUserDetails.getUsername();

		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
		Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
		GrantedAuthority auth = iterator.next();
		String role = auth.getAuthority();

		String token = jwtUtil.createJwt(username, role, 60 * 600 * 600L);

		//response.addCookie(createCookie("Authorization", token));
		response.addHeader("Authorization", token);
		response.sendRedirect(redirectionURI + token);
		System.out.println("[CustomSuccessHandler] - token = " + token);

		/*Map<String, String> tokenData = new HashMap<>();
		tokenData.put("token", token);

		Response<Map<String, String>> responseBody = Response.ok(tokenData);

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.setStatus(HttpServletResponse.SC_OK);
		response.getWriter().write(new ObjectMapper().writeValueAsString(responseBody));
		response.getWriter().flush();*/
	}

	private Cookie createCookie(String key, String value) {

		Cookie cookie = new Cookie(key, value);
		cookie.setMaxAge(60 * 60 * 6000);
		//cookie.setSecure(true);
		cookie.setPath("/");
		cookie.setHttpOnly(true);
		cookie.setAttribute("SameSite", "None");
		cookie.setDomain("comatest.web.app");
		return cookie;
	}
}
