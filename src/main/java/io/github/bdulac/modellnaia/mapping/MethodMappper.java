package io.github.bdulac.modellnaia.mapping;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import io.github.bdulac.modellnaia.dto.MethodDto;
import io.github.bdulac.modellnaia.entity.Method;

@Mapper
public interface MethodMappper extends UmlElementMapper<Method, MethodDto> {
	
	@Mapping(target = "id", source = "entity.id")
	@Mapping(target = "createdAt", source = "entity.createdAt")
	@Mapping(target = "updatedAt", source = "entity.updatedAt")
	@Mapping(target = "version", source = "entity.version")
	@Mapping(target = "name", source = "entity.name")
	@Mapping(target = "returnType", source = "entity.returnType")
	@Mapping(target = "visibility", source = "entity.visibility")
	@Mapping(target = "isStatic", source = "entity.isStatic")
	@Mapping(target = "isFinal", source = "entity.isFinal")
	@Mapping(target = "body", source = "entity.body")
	@Mapping(target = "description", source = "entity.description")
	@Mapping(target = "parameters", source = "entity.parameters")
	@Override
	public MethodDto toDto(Method entity);
	
	@Mapping(target = "id", source = "dto.id")
	@Mapping(target = "createdAt", source = "dto.createdAt")
	@Mapping(target = "updatedAt", source = "dto.updatedAt")
	@Mapping(target = "version", source = "dto.version")
	@Mapping(target = "name", source = "dto.name")
	@Mapping(target = "returnType", source = "dto.returnType")
	@Mapping(target = "visibility", source = "dto.visibility")
	@Mapping(target = "isStatic", source = "dto.isStatic")
	@Mapping(target = "isFinal", source = "dto.isFinal")
	@Mapping(target = "body", source = "dto.body")
	@Mapping(target = "description", source = "dto.description")
	@Mapping(target = "parameters", source = "dto.parameters")
	@Override
	public Method toEntity(MethodDto dto);

}

