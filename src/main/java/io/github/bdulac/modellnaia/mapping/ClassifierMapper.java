package io.github.bdulac.modellnaia.mapping;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import io.github.bdulac.modellnaia.dto.ClassifierDto;
import io.github.bdulac.modellnaia.entity.Classifier;

/**
 * Mapper pour les Classifiers UML.
 */
@Mapper
public interface ClassifierMapper extends UmlElementMapper<Classifier, ClassifierDto> {
    
	@Mapping(target = "id", source = "entity.id")
	@Mapping(target = "createdAt", source = "entity.createdAt")
	@Mapping(target = "updatedAt", source = "entity.updatedAt")
	@Mapping(target = "version", source = "entity.version")
	@Mapping(target = "name", source = "entity.name")
	@Mapping(target = "description", source = "entity.description")
	@Mapping(target = "visibility", source = "entity.visibility")
	@Mapping(target = "type", source = "entity.type")
	@Mapping(target = "diagramId", source = "entity.diagram.id")
	@Mapping(target = "isAbstract", source = "entity.isAbstract")
	@Mapping(target = "isFinal", source = "entity.isFinal")
	@Mapping(target = "attributes", source = "entity.attributes")
	@Mapping(target = "methods", source = "entity.methods")
    public ClassifierDto toDto(Classifier clazz); /*{
        if (clazz == null) return null;
        
        ClassifierDto dto = new ClassifierDto();
        dto.setId(clazz.getId());
        dto.setName(clazz.getName());
        dto.setDescription(clazz.getDescription());
        dto.setDiagramId(clazz.getDiagram() != null ? clazz.getDiagram().getId() : null);
        dto.setAbstract(clazz.isAbstract());
        
        if (clazz.getAttributes() != null) {
            dto.setAttributes(clazz.getAttributes().stream()
                .map(AttributeMapper::toDTO)
                .collect(Collectors.toList()));
        }
        
        if (clazz.getMethods() != null) {
            dto.setMethods(clazz.getMethods().stream()
                .map(MethodMapper::toDTO)
                .collect(Collectors.toList()));
        }
        
        return dto;
    }*/
    
	@Mapping(target = "id", source = "dto.id")
	@Mapping(target = "createdAt", source = "dto.createdAt")
	@Mapping(target = "updatedAt", source = "dto.updatedAt")
	@Mapping(target = "version", source = "dto.version")
	@Mapping(target = "name", source = "dto.name")
	@Mapping(target = "description", source = "dto.description")
	@Mapping(target = "visibility", source = "dto.visibility")
	@Mapping(target = "type", source = "dto.type")
	@Mapping(target = "diagramId", source = "dto.diagram.id")
	@Mapping(target = "isAbstract", source = "dto.isAbstract")
	@Mapping(target = "isFinal", source = "dto.isFinal")
	@Mapping(target = "attributes", source = "dto.attributes")
	@Mapping(target = "methods", source = "dto.methods")
    public Classifier toEntity(ClassifierDto dto); /*{
        if (dto == null) return null;
        
        Classifier clazz = new Classifier();
        clazz.setId(dto.getId());
        clazz.setName(dto.getName());
        clazz.setDescription(dto.getDescription());
        clazz.setAbstract(dto.isAbstract());
        clazz.setAttributes(dto.getAttributes());
        clazz.setMethods(dto.getMethods());
        return clazz;
    }*/
}
