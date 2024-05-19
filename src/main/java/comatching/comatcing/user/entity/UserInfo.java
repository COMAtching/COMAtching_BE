package comatching.comatcing.user.entity;

import comatching.comatcing.util.BaseEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "user_info", uniqueConstraints = {
	@UniqueConstraint(
		name = "username_unique",
		columnNames = "username"
	)
})
public class UserInfo extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "user_info_id")
	private Long id;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "user_ai_feature_id")
	private UserAiFeature userAiFeature;

	private String username;

	private String accessToken;

	private String password;

	private String contactId;

	private String email;

	private String role;

	private Integer pickMe;

	private String song;

	private Integer point;

	private String comment;

	@Builder
	public UserInfo(UserAiFeature userAiFeature, String accessToken, String password,
		String contactId, String role, Integer pickMe, Integer pickMatch, String song, String comment, Integer point,
		String username, String email) {
		this.userAiFeature = userAiFeature;
		this.accessToken = accessToken;
		this.password = password;
		this.contactId = contactId;
		this.role = role;
		this.pickMe = pickMe;
		this.song = song;
		this.comment = comment;
		this.point = point;
		this.username = username;
		this.email = email;
	}

	public void updatePointPickMe(Integer point, Integer pickMe) {
		this.point = point;
		this.pickMe = pickMe;
	}

	public void updateUsername(String username) {
		this.username = username;
	}

	public void updateSocialToUser(String contactId, String song, String comment, UserAiFeature userAiFeature,
		String role, Integer point, Integer pickMe) {
		this.contactId = contactId;
		this.song = song;
		this.comment = comment;
		this.userAiFeature = userAiFeature;
		this.role = role;
		this.point = point;
		this.pickMe = pickMe;
	}
}
