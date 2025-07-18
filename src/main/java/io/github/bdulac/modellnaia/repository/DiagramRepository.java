package io.github.bdulac.modellnaia.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import io.github.bdulac.modellnaia.entity.Diagram;
import io.github.bdulac.modellnaia.enums.DiagramType;

/**
* Repository pour la gestion des diagrammes UML.
* Fournit les opérations CRUD et requêtes spécialisées pour les diagrammes.
*/
@Repository
public interface DiagramRepository extends JpaRepository<Diagram, Long> {
 
 List<Diagram> findByNameContainingIgnoreCase(String name);
 
 List<Diagram> findByType(DiagramType type);
 
 @Query("SELECT d FROM Diagram d WHERE d.createdAt >= :startDate")
 List<Diagram> findRecentDiagrams(@Param("startDate") LocalDateTime startDate);
 
 @Query("SELECT d FROM Diagram d LEFT JOIN FETCH d.project WHERE d.id = :id")
 Optional<Diagram> findByIdWithProject(@Param("id") Long id);
 
 boolean existsByNameAndType(String name, DiagramType type);
}
