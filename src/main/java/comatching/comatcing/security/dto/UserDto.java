package comatching.comatcing.security.dto;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor

public class UserDto {
	private String role;
	private String username;
	private String email;

	@Builder
	public UserDto(String role, String username, String email) {
		this.role = role;
		this.username = username;
		this.email = email;
	}
}
