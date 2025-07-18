package io.github.bdulac.modellnaia.mapping;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import io.github.bdulac.modellnaia.dto.RelationshipDto;
import io.github.bdulac.modellnaia.entity.Relationship;

/**
 * Mapper pour les relationships UML.
 */
@Mapper
public interface RelationshipMapper extends UmlElementMapper<Relationship, RelationshipDto>  {
    
	@Mapping(target = "id", source = "entity.id")
	@Mapping(target = "createdAt", source = "entity.createdAt")
	@Mapping(target = "updatedAt", source = "entity.updatedAt")
	@Mapping(target = "version", source = "entity.version")
	@Mapping(target = "relationshipType", source = "entity.type")
	@Mapping(target = "name", source = "entity.name")
	@Mapping(target = "diagramId", source = "entity.diagram.id")
	@Mapping(target = "sourceId", source = "entity.sourceClass.id")
	@Mapping(target = "targetId", source = "entity.targetClass.id")
	public RelationshipDto toDto(Relationship entity); /*{
        if (relationship == null) return null;
        
        RelationshipDto dto = new RelationshipDto();
        dto.setId(relationship.getId());
        dto.setName(relationship.getName());
        dto.setDescription(relationship.getDescription());
        dto.setDiagramId(relationship.getDiagram() != null ? relationship.getDiagram().getId() : null);
        dto.setSourceId(relationship.getSource() != null ? relationship.getSource().getId() : null);
        dto.setTargetId(relationship.getTarget() != null ? relationship.getTarget().getId() : null);
        dto.setSourceMultiplicity(relationship.getSourceMultiplicity());
        dto.setTargetMultiplicity(relationship.getTargetMultiplicity());
        dto.setrelationshipType(relationship.getrelationshipType());
        
        return dto;
    }*/
    
	@Mapping(target = "id", source = "dto.id")
	@Mapping(target = "createdAt", source = "dto.createdAt")
	@Mapping(target = "updatedAt", source = "dto.updatedAt")
	@Mapping(target = "version", source = "dto.version")
	@Mapping(target = "type", source = "dto.relationshipType")
	@Mapping(target = "name", source = "dto.name")
	@Mapping(target = "diagram.id", source = "dto.diagramId")
	@Mapping(target = "sourceClass.id", source = "dto.sourceId")
	@Mapping(target = "targetClass.id", source = "dto.targetId")
    public Relationship toEntity(RelationshipDto dto); /*{
        if (dto == null) return null;
        
        Relationship relationship = new Relationship();
        relationship.setId(dto.getId());
        relationship.setName(dto.getName());
        relationship.setDescription(dto.getDescription());
        relationship.setSourceMultiplicity(dto.getSourceMultiplicity());
        relationship.setTargetMultiplicity(dto.getTargetMultiplicity());
        relationship.setrelationshipType(dto.getrelationshipType());
        return relationship;
    }*/
}   

