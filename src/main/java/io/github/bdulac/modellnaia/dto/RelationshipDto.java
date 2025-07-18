package io.github.bdulac.modellnaia.dto;

import io.github.bdulac.modellnaia.enums.RelationshipType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * DTO pour les associations UML.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class RelationshipDto extends UmlElementDto {
	private Long diagramId;
    private Long sourceId;
    private Long targetId;
    private String sourceMultiplicity;
    private String targetMultiplicity;
    private RelationshipType relationshipType;
}
