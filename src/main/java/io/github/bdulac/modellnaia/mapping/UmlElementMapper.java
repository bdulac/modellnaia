package io.github.bdulac.modellnaia.mapping;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import io.github.bdulac.modellnaia.dto.UmlElementDto;
import io.github.bdulac.modellnaia.entity.UmlElement;

/**
 * Mapper pour la conversion entre UMLElement et UMLElementDTO.
 */
@Mapper
public interface UmlElementMapper<E extends UmlElement, D extends UmlElementDto> extends EntityDtoMapper<E, D> {
    
	@Mapping(target = "id", source = "entity.id")
	@Mapping(target = "createdAt", source = "entity.createdAt")
	@Mapping(target = "updatedAt", source = "entity.updatedAt")
	@Mapping(target = "version", source = "entity.version")
	@Mapping(target = "name", source = "entity.name")
	@Override
	public D toDto(E entity);
	 
	@Mapping(target = "id", source = "dto.id")
	@Mapping(target = "createdAt", source = "dto.createdAt")
	@Mapping(target = "updatedAt", source = "dto.updatedAt")
	@Mapping(target = "version", source = "dto.version")
	@Mapping(target = "name", source = "dto.name")
	@Override
	public E toEntity(D dto);
}

