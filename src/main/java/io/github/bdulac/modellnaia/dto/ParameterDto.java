package io.github.bdulac.modellnaia.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO pour les paramètres de méthode.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParameterDto extends UmlElementDto {
    
    @NotBlank(message = "Le type du paramètre ne peut pas être vide")
    private String type;
    
    private String defaultValue;
}
