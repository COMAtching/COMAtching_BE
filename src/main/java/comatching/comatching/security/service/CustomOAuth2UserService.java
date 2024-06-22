package comatching.comatching.security.service;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import comatching.comatching.security.dto.OAuth2Response;
import comatching.comatching.security.dto.UserDto;
import comatching.comatching.user.repository.UserInfoRepository;
import comatching.comatching.security.dto.CustomOAuth2User;
import comatching.comatching.security.dto.KakaoResponse;
import comatching.comatching.user.entity.UserInfo;
import comatching.comatching.user.enums.Role;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
	private final UserInfoRepository userInfoRepository;

	public CustomOAuth2UserService(UserInfoRepository userInfoRepository) {
		this.userInfoRepository = userInfoRepository;
	}

	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		OAuth2User oAuth2User = super.loadUser(userRequest);
		System.out.println(oAuth2User.getName());

		String registrationId = userRequest.getClientRegistration().getRegistrationId();
		OAuth2Response oAuth2Response = null;

		if (registrationId.equals("kakao")) {
			oAuth2Response = new KakaoResponse(oAuth2User.getAttributes());
		} else {
			return null;
		}

		String username = oAuth2Response.getProvider() + " " + oAuth2Response.getProviderId();

		UserInfo existData = userInfoRepository.findByUsername(username);
		UserDto userDto;
		if (existData == null) {
			UserInfo userEntity = UserInfo.builder()
				.username(username)
				.email(oAuth2Response.getEmail())
				.role(Role.SOCIAL.getRoleName())
				.build();

			userInfoRepository.save(userEntity);

			userDto = new UserDto().builder()
				.username(username)
				.role(Role.SOCIAL.getRoleName())
				.email(oAuth2Response.getEmail())
				.build();
		} else {
			existData.updateUsername(username);
			userInfoRepository.save(existData);

			userDto = new UserDto().builder()
				.username(username)
				.role(existData.getRole())
				.email(oAuth2Response.getEmail())
				.build();
		}

		System.out.println(userDto.getRole());
		return new CustomOAuth2User(userDto);
	}
}
