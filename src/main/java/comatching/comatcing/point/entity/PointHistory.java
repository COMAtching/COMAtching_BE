package comatching.comatcing.point.entity;

import comatching.comatcing.comatching.entity.ComatchHistory;
import comatching.comatcing.point.enums.PointHistoryType;
import comatching.comatcing.user.entity.UserInfo;
import comatching.comatcing.util.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "point_history")
public class PointHistory extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "point_history_id")
	private Long id;

	@ManyToOne
	@JoinColumn(name = "user_info_id")
	private UserInfo userInfo;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "comatch_history_id")
	private ComatchHistory comatchHistory;
	@Enumerated(EnumType.STRING)
	private PointHistoryType payType;
	private Integer beforeWork;
	private Integer afterWork;
	private Integer pointAmount;

	private Integer addPickMe;

	@Builder
	public PointHistory(ComatchHistory comatchHistory, PointHistoryType payType, Integer beforeWork, Integer afterWork,
		Integer pointAmount, Integer addPickMe, UserInfo userInfo) {
		this.payType = payType;
		this.beforeWork = beforeWork;
		this.afterWork = afterWork;
		this.pointAmount = pointAmount;
		this.comatchHistory = comatchHistory;
		this.addPickMe = addPickMe;
		this.userInfo = userInfo;

	}
}
