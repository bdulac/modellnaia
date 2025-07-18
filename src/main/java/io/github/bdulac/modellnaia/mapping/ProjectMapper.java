package io.github.bdulac.modellnaia.mapping;

import org.mapstruct.Mapping;

import io.github.bdulac.modellnaia.dto.ProjectDto;
import io.github.bdulac.modellnaia.entity.Project;

public interface ProjectMapper extends UmlElementMapper<Project, ProjectDto>  {
	
	@Mapping(target = "id", source = "entity.id")
	@Mapping(target = "createdAt", source = "entity.createdAt")
	@Mapping(target = "updatedAt", source = "entity.updatedAt")
	@Mapping(target = "version", source = "entity.version")
	@Mapping(target = "name", source = "entity.name")
	@Mapping(target = "description", source = "entity.description")
	@Mapping(target = "gitRepositoryPath", source = "entity.gitRepositoryPath")
	@Mapping(target = "diagrams", source = "entity.diagrams")
	public ProjectDto toDto(Project entity);
	
	@Mapping(target = "id", source = "dto.id")
	@Mapping(target = "createdAt", source = "dto.createdAt")
	@Mapping(target = "updatedAt", source = "dto.updatedAt")
	@Mapping(target = "version", source = "dto.version")
	@Mapping(target = "name", source = "dto.name")
	@Mapping(target = "description", source = "dto.description")
	@Mapping(target = "gitRepositoryPath", source = "dto.gitRepositoryPath")
	@Mapping(target = "diagrams", source = "dto.diagrams")
	public Project toEntity(ProjectDto dto);

}
