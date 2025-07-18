package io.github.bdulac.modellnaia.dto;

import java.time.LocalDateTime;
import java.util.Set;

import io.github.bdulac.modellnaia.enums.CodeGenerationOption;
import io.github.bdulac.modellnaia.enums.Language;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO pour la génération de code.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CodeGenerationDto extends BaseEntityDto {
    private Long projectId;
    private Language targetLanguage;
    private String generatedCode;
    private String template;
    private LocalDateTime generationTimestamp;
    private Set<CodeGenerationOption> options;
}
