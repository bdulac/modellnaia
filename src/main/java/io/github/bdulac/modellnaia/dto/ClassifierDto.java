package io.github.bdulac.modellnaia.dto;

import java.util.ArrayList;
import java.util.List;

import io.github.bdulac.modellnaia.enums.ClassifierType;
import io.github.bdulac.modellnaia.enums.Visibility;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * DTO pour les classes UML.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class ClassifierDto extends UmlElementDto {
	private ClassifierType type;
	private Visibility visbility;
    private boolean isAbstract;
    private boolean isStatic;
    private boolean isFinal;
    private List<AttributeDto> attributes = new ArrayList<>();
    private List<MethodDto> methods = new ArrayList<>();
}
