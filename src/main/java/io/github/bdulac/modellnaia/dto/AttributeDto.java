package io.github.bdulac.modellnaia.dto;

import io.github.bdulac.modellnaia.enums.Visibility;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO pour les attributs.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AttributeDto extends UmlElementDto {
	
    @NotBlank(message = "Le nom de l'attribut ne peut pas être vide")
    private String name;
    
    @NotBlank(message = "Le type de l'attribut ne peut pas être vide")
    private String type;
    
    @NotNull(message = "La visibilité est obligatoire")
    private Visibility visibility;
    
    private boolean isStatic;
    private boolean isFinal;
    private String defaultValue;
    private Long ownerClassId;
}
