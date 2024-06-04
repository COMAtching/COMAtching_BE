package comatching.comatcing.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import comatching.comatcing.security.dto.CustomOAuth2User;

public class SecurityUtil {

	public static CustomOAuth2User getContextUserInfo() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication == null) {
			return null;
		}

		Object principal = authentication.getPrincipal();

		if (principal instanceof CustomOAuth2User) {
			return (CustomOAuth2User)principal;
		}

		return null;
	}

}
