package io.github.bdulac.modellnaia.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import io.github.bdulac.modellnaia.enums.DiagramType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO pour les diagrammes UML.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DiagramDto extends UmlElementDto {
    
    @NotBlank(message = "Le nom du diagramme ne peut pas être vide")
    @Size(max = 100, message = "Le nom ne peut pas dépasser 100 caractères")
    private String name;
    
    @Size(max = 500, message = "La description ne peut pas dépasser 500 caractères")
    private String description;
    
    @NotNull(message = "Le type de diagramme est obligatoire")
    private DiagramType type;
    
    private Long projectId;
    
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
	private List<ClassifierDto> classifiers = new ArrayList<>();

	private List<ActivityDto> activities = new ArrayList<>();

	private List<RelationshipDto> relationships = new ArrayList<>();
}
