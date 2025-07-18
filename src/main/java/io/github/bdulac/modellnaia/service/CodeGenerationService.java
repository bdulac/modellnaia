package io.github.bdulac.modellnaia.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import io.github.bdulac.modellnaia.entity.Attribute;
import io.github.bdulac.modellnaia.entity.Classifier;
import io.github.bdulac.modellnaia.entity.CodeGeneration;
import io.github.bdulac.modellnaia.entity.Diagram;
import io.github.bdulac.modellnaia.entity.Method;
import io.github.bdulac.modellnaia.entity.Parameter;
import io.github.bdulac.modellnaia.entity.Project;
import io.github.bdulac.modellnaia.entity.Relationship;
import io.github.bdulac.modellnaia.entity.UmlElement;
import io.github.bdulac.modellnaia.enums.ClassifierType;
import io.github.bdulac.modellnaia.enums.CodeGenerationOption;
import io.github.bdulac.modellnaia.enums.Language;
import io.github.bdulac.modellnaia.enums.Visibility;
import io.github.bdulac.modellnaia.repository.CodeGenerationRepository;
import io.github.bdulac.modellnaia.repository.DiagramRepository;
import io.github.bdulac.modellnaia.repository.ProjectRepository;
import io.github.bdulac.modellnaia.service.MustacheTemplateEngine.UnsupportedLanguageException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Service de génération de code à partir d'éléments UML.
 * Orchestres la transformation d'éléments UML en code source dans différents langages.
 */
@Service
@Transactional
@Slf4j
public class CodeGenerationService {
    
    private final CodeGenerationRepository codeGenerationRepository;
    private final Map<Language, CodeGenerator> generators = new HashMap<>();
    private final TemplateEngine templateEngine;
    
    public CodeGenerationService(CodeGenerationRepository codeGenerationRepository,
    						   DiagramRepository diagramRepository,
                               ProjectRepository projectRepository,
                               TemplateEngine templateEngine) {
        this.codeGenerationRepository = codeGenerationRepository;
        this.templateEngine = templateEngine;
        generators.put(Language.JAVA, new JavaCodeGenerator());
    }
    
    /**
     * Génère du code pour un élément UML donné dans le langage spécifié.
     */
    public Project generate(Project project, Language language) {
        return generate(project, language, Set.of());
    }
    
    /**
     * Génère du code avec des options spécifiques.
     */
    public Project generate(Project project, Language language, Set<CodeGenerationOption> options) {
    	if(project != null && project.getName() != null) {
    		log.info("Génération de code pour le projet {} en {}", project.getName(), language);

    		validateElement(project);
    		try {
    		CodeGenerator generator = getGenerator(language);

    		for(Diagram diagram : project.getDiagrams()) {
    			for(Classifier classifier : diagram.getClassifiers()) {
    				String template = generator.getTemplate(classifier);    
    				CodeGenerationContext context = new CodeGenerationContext();
    				context.setProjectId(project.getId());
    				context.setTargetLanguage(language);
    				context.setTemplate(template);
    				String generatedCode = generator.generate(context);
    				CodeGeneration codeGeneration = new CodeGeneration(project, language, generatedCode, context.getTemplate(), context.getOptions());
    				codeGeneration.setGeneratedCode(generatedCode);            
    				codeGeneration.setCreatedAt(LocalDateTime.now());
    				codeGeneration.setUpdatedAt(codeGeneration.getCreatedAt());
    				codeGenerationRepository.save(codeGeneration);
    			}
    		}
    		} catch (UnsupportedLanguageException e) {
    			throw new IllegalStateException(e);
    		}
    		return project;
    	}
    	return null;
    }
    
    /**
     * Récupère les générations existantes pour un élément.
     */
    public List<CodeGeneration> getGenerationsForElement(Long projectId) {
        return codeGenerationRepository.findByProjectId(projectId);
    }
    
    /**
     * Récupère une génération spécifique par élément et langage.
     */
    public Optional<CodeGeneration> getGeneration(Long elementId, Language language) {
        return codeGenerationRepository.findBySourceElementAndLanguage(elementId, language);
    }
    
    /**
     * Régénère le code pour une génération existante.
     */
    public Project regenerate(Long generationId) {
        CodeGeneration existing = codeGenerationRepository.findById(generationId).get();
        
        return generate(existing.getProject(), existing.getTargetLanguage(), existing.getOptions());
    }
    
    /**
     * Supprime une génération de code.
     */
    public void deleteGeneration(Long generationId) {
        if (!codeGenerationRepository.findById(generationId).isPresent()) {
            throw new IllegalStateException("Identifiant de génération introuvable : " + generationId);
        }
        codeGenerationRepository.deleteById(generationId);
    }
    
    /**
     * Récupère les générations récentes.
     */
    public List<CodeGeneration> getRecentGenerations(int days) {
        LocalDateTime since = LocalDateTime.now().minusDays(days);
        return codeGenerationRepository.findRecentGenerations(since);
    }
    
    /**
     * Valide qu'un élément peut être utilisé pour la génération de code.
     */
    private void validateElement(Project project) {
        if (project == null) {
            throw new IllegalArgumentException("Le projet UML ne peut pas être null");
        }
        
        if (project.getName() == null || project.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Le projet UML doit avoir un nom");
        }
        
        List<Diagram> diagrams = project.getDiagrams();
        for(Diagram diagram : diagrams) {
        	List<Classifier> classifiers = diagram.getClassifiers();
        	for(Classifier classifier : classifiers) {
        		validateClass(classifier);
        	}
        }
    }
    
    private void validateClass(Classifier classifier) {
        // Validation des attributs
        if (classifier.getAttributes() != null) {
            for (Attribute attr : classifier.getAttributes()) {
                if (attr.getName() == null || attr.getType() == null) {
                    throw new IllegalArgumentException("Attribut invalide dans la classe " + classifier.getName());
                }
            }
        }
        
        // Validation des méthodes
        if (classifier.getMethods() != null) {
            for (Method method : classifier.getMethods()) {
                if (method.getName() == null) {
                    throw new IllegalArgumentException("Méthode invalide dans la classe " + classifier.getName());
                }
            }
        }
    }
    
    private void validateInterface(Classifier classifier) {
        // Validation des méthodes d'interface
        if (classifier.getMethods() != null) {
            for (Method method : classifier.getMethods()) {
                if (method.getName() == null) {
                    throw new IllegalArgumentException("Méthode invalide dans l'interface " + classifier.getName());
                }
                if (!method.isAbstract()) {
                    log.warn("Les méthodes d'interface devraient être abstraites: {}", method.getName());
                }
            }
        }
    }
    
    private CodeGenerator getGenerator(Language language) {
        return generators.get(language);
    }
    
    /**
     * Récupère la liste des langages supportés.
     */
    public Set<Language> getSupportedLanguages() {
        return generators.keySet();
    }
}