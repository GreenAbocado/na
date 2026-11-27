package com.na.project.global.entity;

import java.time.LocalDateTime;

import jakarta.persistence.MappedSuperclass;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@MappedSuperclass
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public abstract class SoftDeleteBaseEntity extends BaseEntity {

	private LocalDateTime deletedAt;

	public void delete() {
		if (deletedAt == null) {
			deletedAt = LocalDateTime.now();
		}
	}
}