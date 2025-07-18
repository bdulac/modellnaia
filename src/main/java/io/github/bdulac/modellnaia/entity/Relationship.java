package io.github.bdulac.modellnaia.entity;

import io.github.bdulac.modellnaia.enums.RelationshipType;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/** Relation entre classes (héritage, association, etc.). */
@Entity
@Table(name = "relationships")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Relationship extends UmlElement {

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private RelationshipType type;

    /** Nom de la relation (optionnel). */
    @Column(name = "name", length = 100)
    private String name;

    @Column(name = "source_multiplicity", length = 20)
    private String sourceMultiplicity; // ex: "1", "0..1", "1..*", "*"

    @Column(name = "target_multiplicity", length = 20)
    private String targetMultiplicity;

    @Column(name = "source_role", length = 100)
    private String sourceRole; // Rôle côté source

    @Column(name = "target_role", length = 100)
    private String targetRole; // Rôle côté cible

    @Column(name = "is_navigable_source_to_target")
    private boolean isNavigableSourceToTarget = true;

    @Column(name = "is_navigable_target_to_source")
    private boolean isNavigableTargetToSource = false;

    @Size(max = 500)
    @Column(name = "description", length = 500)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "diagram_id", nullable = false)
    private Diagram diagram;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "source_class_id", nullable = false)
    private Classifier sourceClass;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "target_class_id", nullable = false)
    private Classifier targetClass;

    public Relationship(RelationshipType type, Classifier sourceClass, Classifier targetClass, Diagram diagram) {
        this.type = type;
        this.sourceClass = sourceClass;
        this.targetClass = targetClass;
        this.diagram = diagram;
    }
}
