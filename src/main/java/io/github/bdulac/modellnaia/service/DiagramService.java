package io.github.bdulac.modellnaia.service;

import org.springframework.stereotype.Service;

import io.github.bdulac.modellnaia.dto.DiagramDto;
import io.github.bdulac.modellnaia.entity.CodeGeneration;
import io.github.bdulac.modellnaia.entity.Diagram;
import io.github.bdulac.modellnaia.entity.Project;
import io.github.bdulac.modellnaia.enums.Language;
import io.github.bdulac.modellnaia.mapping.DiagramMapper;
import io.github.bdulac.modellnaia.repository.DiagramRepository;
import io.github.bdulac.modellnaia.mapping.DiagramMapperImpl;
import jakarta.transaction.Transactional;

/**
 * Service de gestion des diagrammes UML.
 * Orchestre les opérations métier sur les diagrammes et leurs éléments.
 */
@Service
@Transactional
public class DiagramService {
    
    private final DiagramRepository diagramRepository;
    private final CodeGenerationService codeGenerationService;
    private final DiagramMapper diagramMapper;
    
    public DiagramService(DiagramRepository diagramRepository, 
                         CodeGenerationService codeGenerationService) {
        this.diagramRepository = diagramRepository;
        this.codeGenerationService = codeGenerationService;
        this.diagramMapper = new DiagramMapperImpl();
    }
    
    public Diagram createDiagram(DiagramDto diagramDTO) {
        Diagram diagram = diagramMapper.toEntity(diagramDTO);
        return diagramRepository.save(diagram);
    }
    
 
    
    public Project generateCode(Long diagramId, Language language) {
        Diagram diagram = diagramRepository.findById(diagramId).orElse(null);
		return (diagram != null) ? codeGenerationService.generate(diagram.getProject(), language) : null;
	}
}
