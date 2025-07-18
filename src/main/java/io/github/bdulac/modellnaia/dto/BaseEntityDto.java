package io.github.bdulac.modellnaia.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public abstract class BaseEntityDto {

	protected Long id;

	private LocalDateTime createdAt;

	private LocalDateTime updatedAt;

}
