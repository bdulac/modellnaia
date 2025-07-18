package io.github.bdulac.modellnaia.mapping;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import io.github.bdulac.modellnaia.dto.DiagramDto;
import io.github.bdulac.modellnaia.entity.Diagram;

/**
 * Mapper pour la conversion entre Diagram et DiagramDTO.
 */
@Mapper
public interface DiagramMapper extends UmlElementMapper<Diagram, DiagramDto> {
    
	@Mapping(target = "id", source = "entity.id")
	@Mapping(target = "createdAt", source = "entity.createdAt")
	@Mapping(target = "updatedAt", source = "entity.updatedAt")
	@Mapping(target = "name", source = "entity.name")
	@Mapping(target = "description", source = "entity.description")
	@Mapping(target = "type", source = "entity.type")
	@Mapping(target = "version", source = "entity.version")
	@Mapping(target = "classifiers", source = "entity.classifiers")
	@Mapping(target = "activities", source = "entity.activities")
	@Mapping(target = "relationships", source = "entity.relationships")
	@Mapping(target = "projectId", source = "entity.project.id")
    public DiagramDto toDto(Diagram entity);
    
	@Mapping(target = "id", source = "dto.id")
	@Mapping(target = "createdAt", source = "dto.createdAt")
	@Mapping(target = "updatedAt", source = "dto.updatedAt")
	@Mapping(target = "name", source = "dto.name")
	@Mapping(target = "description", source = "dto.description")
	@Mapping(target = "type", source = "dto.type")
	@Mapping(target = "version", source = "dto.version")
	@Mapping(target = "classifiers", source = "dto.classifiers")
	@Mapping(target = "activities", source = "dto.activities")
	@Mapping(target = "relationships", source = "dto.relationships")
    public Diagram toEntity(DiagramDto dto);
}
