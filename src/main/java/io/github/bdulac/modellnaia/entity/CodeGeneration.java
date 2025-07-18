package io.github.bdulac.modellnaia.entity;

import java.util.HashSet;
import java.util.Set;

import io.github.bdulac.modellnaia.enums.CodeGenerationOption;
import io.github.bdulac.modellnaia.enums.Language;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "code_generations")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CodeGeneration extends BaseEntity {
    
    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "target_language", nullable = false)
    private Language targetLanguage;
    
    @Column(name = "generated_code", nullable = false)
    @Lob
    private String generatedCode;
    
    @Column(name = "template", nullable = false)
    @Lob
    private String template;
    
    @ElementCollection
    @Enumerated(EnumType.STRING)
    private Set<CodeGenerationOption> options = new HashSet<>();
}
