package io.github.bdulac.modellnaia.mapping;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import io.github.bdulac.modellnaia.dto.BaseEntityDto;
import io.github.bdulac.modellnaia.entity.BaseEntity;

@Mapper
public interface EntityDtoMapper<E extends BaseEntity, D extends BaseEntityDto> {
	
	@Mapping(target = "id", source = "entity.id")
	@Mapping(target = "createdAt", source = "entity.createdAt")
	@Mapping(target = "updatedAt", source = "entity.updatedAt")
	public D toDto(E entity);
	 
	@Mapping(target = "id", source = "dto.id")
	@Mapping(target = "createdAt", source = "dto.createdAt")
	@Mapping(target = "updatedAt", source = "dto.updatedAt")
	 public E toEntity(D dto);

}
