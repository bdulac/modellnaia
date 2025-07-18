package io.github.bdulac.modellnaia.mapping;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import io.github.bdulac.modellnaia.dto.CodeGenerationDto;
import io.github.bdulac.modellnaia.entity.CodeGeneration;

@Mapper
public interface CodeGenerationMapper extends EntityDtoMapper<CodeGeneration, CodeGenerationDto> {
	
	
	@Mapping(target = "id", source = "entity.id")
	@Mapping(target = "createdAt", source = "entity.createdAt")
	@Mapping(target = "updatedAt", source = "entity.updatedAt")
	@Mapping(target = "projectId", source = "entity.project.id")
	@Mapping(target = "targetLanguage", source = "entity.targetLanguage")
	@Mapping(target = "generatedCode", source = "entity.generatedCode")
	@Mapping(target = "template", source = "entity.template")
	@Mapping(target = "generationTimestamp", source = "entity.generationTimestamp")
	@Mapping(target = "options", source = "entity.options")
	public CodeGenerationDto toDto(CodeGeneration entity);

	@Mapping(target = "id", source = "entity.id")
	@Mapping(target = "createdAt", source = "entity.createdAt")
	@Mapping(target = "updatedAt", source = "entity.updatedAt")
	@Mapping(target = "projectId", source = "project")
	@Mapping(target = "targetLanguage", source = "dto.targetLanguage")
	@Mapping(target = "generatedCode", source = "dto.generatedCode")
	@Mapping(target = "template", source = "dto.template")
	@Mapping(target = "generationTimestamp", source = "dto.generationTimestamp")
	@Mapping(target = "options", source = "dto.options")
	public CodeGeneration toEntity(CodeGenerationDto dto);
	
	
	
	/*
	public static CodeGenerationDto toDTO(CodeGeneration codeGeneration) {
		CodeGenerationDto dto = new CodeGenerationDto();
		dto.setId(codeGeneration.getId());
		dto.setProjectId(codeGeneration.getProject().getId());
		dto.setTargetLanguage(codeGeneration.getLanguage());
		dto.setGeneratedCode(codeGeneration.getGeneratedCode());
		dto.setTemplate(codeGeneration.getTemplate());
		dto.setGenerationTimeStamp(codeGeneration.getGenerationTimeStamp());
		dto.setOptions(codeGeneration.getOptions());
		return dto;
	}
	
	public static CodeGeneration toEntity(CodeGenerationDto dto) {
		CodeGeneration codeGeneration = new CodeGeneration();
		codeGeneration.setId(dto.getId());
		codeGeneration.setProject(projectRepository.findById(dto.getProjectId()));
		codeGeneration.setTargetLanguage(dto.getTargetLanguage());
		codeGeneration.setGeneratedCode(dto.getGeneratedCode());
		codeGeneration.setTemplate(dto.getTemplate());
		codeGeneration.setGenerationTimestamp(dto.getGenerationTimestamp());
		return codeGeneration;
	}*/

}
