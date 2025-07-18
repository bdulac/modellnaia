package io.github.bdulac.modellnaia.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.bdulac.modellnaia.dto.DiagramDto;
import io.github.bdulac.modellnaia.entity.Diagram;
import io.github.bdulac.modellnaia.mapping.DiagramMapper;
import io.github.bdulac.modellnaia.service.DiagramService;
import io.github.bdulac.modellnaia.mapping.DiagramMapperImpl;
import jakarta.validation.Valid;

/**
 * Contrôleur REST pour la gestion des diagrammes UML.
 * Expose les endpoints pour créer, consulter et manipuler les diagrammes.
 */
@RestController
@RequestMapping("/api/v1/diagrams")
@Validated
public class DiagramController {
    
    private final DiagramService diagramService;
    private final DiagramMapper diagramMapper;
    
    public DiagramController(DiagramService diagramService) {
        this.diagramService = diagramService;
        this.diagramMapper = new DiagramMapperImpl();
    }
    
    @PostMapping
    public ResponseEntity<DiagramDto> createDiagram(@Valid @RequestBody DiagramDto diagramDTO) {
        Diagram created = diagramService.createDiagram(diagramDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(diagramMapper.toDto(created));
    }
}