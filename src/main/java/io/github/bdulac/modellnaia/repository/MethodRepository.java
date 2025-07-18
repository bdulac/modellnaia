package io.github.bdulac.modellnaia.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import io.github.bdulac.modellnaia.entity.Method;
import io.github.bdulac.modellnaia.enums.Visibility;

/**
 * Repository pour la gestion des méthodes.
 */
@Repository
public interface MethodRepository extends JpaRepository<Method, Long> {
    
    List<Method> findByClassifierId(Long classId);
    
    List<Method> findByVisibility(Visibility visibility);
    
    @Query("SELECT m FROM Method m WHERE m.classifier.id = :classId ORDER BY m.name")
    List<Method> findByClassifierIdOrderByName(@Param("classId") Long classId);
    
    boolean existsByNameAndClassifierId(String name, Long classId);
}
