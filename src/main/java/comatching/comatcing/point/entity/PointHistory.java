package comatching.comatcing.point.entity;

import comatching.comatcing.point.enums.PointHistoryType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "point_history")
public class PointHistory {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Enumerated(EnumType.STRING)
	private PointHistoryType type;
	private Long before;
	private Long after;
	private Long amount;

	@Builder
	public PointHistory(PointHistoryType type, Long before, Long after, Long amount) {
		this.type = type;
		this.before = before;
		this.after = after;
		this.amount = amount;
	}
}
