package io.github.bdulac.modellnaia.service;

import java.time.LocalDateTime;
import java.util.Set;

import io.github.bdulac.modellnaia.entity.Classifier;
import io.github.bdulac.modellnaia.enums.CodeGenerationOption;
import io.github.bdulac.modellnaia.enums.Language;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CodeGenerationContext {
    private Long projectId;
    private Language targetLanguage;
    private Classifier classifier;
    private String generatedCode;
    private String template;
    private LocalDateTime generationTimestamp;
    private Set<CodeGenerationOption> options;
    public boolean hasOption(CodeGenerationOption option) {
    	return options.contains(option);
    }
}
