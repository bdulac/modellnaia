package io.github.bdulac.modellnaia.dto;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO pour les éléments UML.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "elementType")
@JsonSubTypes({
    @JsonSubTypes.Type(value = ClassifierDto.class, name = "classifier"),
    @JsonSubTypes.Type(value = RelationshipDto.class, name = "relationship")
})
public abstract class UmlElementDto extends BaseEntityDto {
    private @NotBlank String version;
    private @NotBlank String name;
}