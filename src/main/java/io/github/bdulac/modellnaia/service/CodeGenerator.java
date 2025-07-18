package io.github.bdulac.modellnaia.service;

import io.github.bdulac.modellnaia.entity.Classifier;
import io.github.bdulac.modellnaia.entity.Project;
import io.github.bdulac.modellnaia.enums.Language;

/**
 * Interface pour les générateurs de code spécifiques à un langage.
 */
public interface CodeGenerator {
    
    /**
     * Génère le code pour une entité de génération de code.
     * @param context Contexte de génération de code.
     * @return Code généré.
     */
    String generate(CodeGenerationContext context);
    
    /**
     * Retourne le langage supporté par ce générateur.
     */
    Language getSupportedLanguage();
    
    /**
     * Retourne le template utilisé pour un type d'élément.
     */
    String getTemplate(Classifier classifier);
}
