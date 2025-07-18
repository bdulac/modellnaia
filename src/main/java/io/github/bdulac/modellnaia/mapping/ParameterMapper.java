package io.github.bdulac.modellnaia.mapping;

import org.mapstruct.Mapping;

import io.github.bdulac.modellnaia.dto.ParameterDto;
import io.github.bdulac.modellnaia.entity.Parameter;

public interface ParameterMapper extends UmlElementMapper<Parameter, ParameterDto> {
	
	@Mapping(source="entity.id" , target="id")
	@Mapping(source="entity.name" , target="name")
	@Mapping(source="entity.type" , target="type")
	@Mapping(source="entity.order" , target="order")
	@Mapping(source="entity.defaultValue" , target="defaultValue")
	@Mapping(source="entity.method" , target="method")
	@Override
	ParameterDto toDto(Parameter entity);

	@Mapping(source="dto.id" , target="id")
	@Mapping(source="dto.name" , target="name")
	@Mapping(source="dto.type" , target="type")
	@Mapping(source="dto.order" , target="order")
	@Mapping(source="dto.defaultValue" , target="defaultValue")
	@Mapping(source="dto.method" , target="method")
	@Override
	Parameter toEntity(ParameterDto dto);
}
