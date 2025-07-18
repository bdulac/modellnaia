package io.github.bdulac.modellnaia.dto;

import java.util.ArrayList;
import java.util.List;

import io.github.bdulac.modellnaia.enums.Visibility;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO pour les méthodes.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MethodDto extends UmlElementDto {

    private String returnType;
    
    @NotNull(message = "La visibilité est obligatoire")
    private Visibility visibility;
    
    private boolean isStatic;
    private boolean isAbstract;
    private boolean isFinal;
    private List<ParameterDto> parameters = new ArrayList<>();
    private Long ownerClassId;
}