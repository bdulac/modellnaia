package io.github.bdulac.modellnaia.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import io.github.bdulac.modellnaia.entity.Attribute;
import io.github.bdulac.modellnaia.enums.Visibility;

/**
 * Repository pour la gestion des attributs.
 */
@Repository
public interface AttributeRepository extends JpaRepository<Attribute, Long> {
    
    List<Attribute> findByClassifierId(Long classId);
    
    List<Attribute> findByVisibility(Visibility visibility);
    
    @Query("SELECT a FROM Attribute a WHERE a.classifier.id = :classId ORDER BY a.name")
    List<Attribute> findByClassifierIdOrderByName(@Param("classId") Long classId);
}