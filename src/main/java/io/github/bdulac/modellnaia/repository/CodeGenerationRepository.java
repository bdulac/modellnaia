package io.github.bdulac.modellnaia.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import io.github.bdulac.modellnaia.entity.CodeGeneration;
import io.github.bdulac.modellnaia.enums.Language;

/**
 * Repository pour la gestion de la génération de code.
 */
@Repository
public interface CodeGenerationRepository extends JpaRepository<CodeGeneration, Long> {
    
    List<CodeGeneration> findByProjectId(Long projectId);
    
    List<CodeGeneration> findByTargetLanguage(Language language);
    
    @Query("SELECT cg FROM CodeGeneration cg WHERE cg.project.id = :elementId AND cg.targetLanguage = :language")
    Optional<CodeGeneration> findBySourceElementAndLanguage(@Param("elementId") Long elementId, 
                                                           @Param("language") Language language);
    
    @Query("SELECT cg FROM CodeGeneration cg WHERE cg.updatedAt >= :since")
    List<CodeGeneration> findRecentGenerations(@Param("since") LocalDateTime since);
}
