package io.github.bdulac.modellnaia.mapping;

import org.mapstruct.Mapping;

import io.github.bdulac.modellnaia.dto.ActivityDto;
import io.github.bdulac.modellnaia.entity.Activity;

public interface ActivityMapper extends UmlElementMapper<Activity, ActivityDto> {

	@Mapping(target = "id", source = "entity.id")
	@Mapping(target = "createdAt", source = "entity.createdAt")
	@Mapping(target = "updatedAt", source = "entity.updatedAt")
	@Mapping(target = "version", source = "entity.version")
	@Mapping(target = "name", source = "entity.name")
	@Mapping(target = "description", source = "entity.description")
	@Mapping(target = "type", source = "entity.type")
	@Mapping(target = "code", source = "entity.code")
	@Mapping(target = "conditionExpression", source = "entity.conditionExpression")
	@Mapping(target = "positionX", source = "entity.positionX")
	@Mapping(target = "positionY", source = "entity.positionY")
	@Mapping(target = "diagramdId", source = "entity.diagram.id")
	public ActivityDto toDto(Activity entity);
	
	@Mapping(target = "id", source = "dto.id")
	@Mapping(target = "createdAt", source = "dto.createdAt")
	@Mapping(target = "updatedAt", source = "dto.updatedAt")
	@Mapping(target = "version", source = "dto.version")
	@Mapping(target = "name", source = "dto.name")
	@Mapping(target = "description", source = "dto.description")
	@Mapping(target = "type", source = "dto.type")
	@Mapping(target = "code", source = "dto.code")
	@Mapping(target = "conditionExpression", source = "dto.conditionExpression")
	@Mapping(target = "positionX", source = "dto.positionX")
	@Mapping(target = "positionY", source = "dto.positionY")
	@Mapping(target = "diagramd", source = "dto.diagram.id")
	public Activity toEntity(ActivityDto dto);
}
