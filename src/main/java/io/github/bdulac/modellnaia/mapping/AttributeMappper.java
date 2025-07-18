package io.github.bdulac.modellnaia.mapping;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import io.github.bdulac.modellnaia.dto.AttributeDto;
import io.github.bdulac.modellnaia.entity.Attribute;

@Mapper
public interface AttributeMappper extends UmlElementMapper<Attribute, AttributeDto> {

	@Mapping(target = "id", source = "entity.id")
	@Mapping(target = "createdAt", source = "entity.createdAt")
	@Mapping(target = "updatedAt", source = "entity.updatedAt")
	@Mapping(target = "version", source = "entity.version")
	@Mapping(target = "name", source = "entity.name")
	@Mapping(target = "type", source = "entity.type")
	@Mapping(target = "visibility", source = "entity.visibility")
	@Mapping(target = "isStatic", source = "entity.isStatic")
	@Mapping(target = "isFinal", source = "entity.isFinal")
	@Mapping(target = "defaultValue", source = "entity.defaultValue")
	@Mapping(target = "description", source = "entity.description")
	@Override
	public AttributeDto toDto(Attribute entity);

	@Mapping(target = "id", source = "dto.id")
	@Mapping(target = "createdAt", source = "dto.createdAt")
	@Mapping(target = "updatedAt", source = "dto.updatedAt")
	@Mapping(target = "version", source = "dto.version")
	@Mapping(target = "name", source = "dto.name")
	@Mapping(target = "type", source = "dto.type")
	@Mapping(target = "visibility", source = "dto.visibility")
	@Mapping(target = "isStatic", source = "dto.isStatic")
	@Mapping(target = "isFinal", source = "dto.isFinal")
	@Mapping(target = "defaultValue", source = "dto.defaultValue")
	@Mapping(target = "description", source = "dto.description")
	@Override
	public Attribute toEntity(AttributeDto dto);

}
