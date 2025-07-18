package io.github.bdulac.modellnaia.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

import io.github.bdulac.modellnaia.enums.ClassifierType;
import io.github.bdulac.modellnaia.enums.Visibility;

/** Élément de classification (classe, interface, enum, etc.). */
@Entity
@Table(name = "classifiers")
@Data
public class Classifier extends UmlElement {

    @Enumerated(EnumType.STRING)
    @Column(name = "visibility", nullable = false)
    private Visibility visibility = Visibility.PUBLIC;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private ClassifierType type;

    @Column(name = "is_abstract")
    private boolean isAbstract = false;

    @Column(name = "is_final")
    private boolean isFinal = false;

    @Column(name = "is_static")
    private boolean isStatic = false;

    @Size(max = 500)
    @Column(name = "description", length = 500)
    private String description;

    // Position dans le diagramme pour l'éditeur graphique
    @Column(name = "position_x")
    private Double positionX;

    @Column(name = "position_y")
    private Double positionY;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "diagram_id", nullable = false)
    private Diagram diagram;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "package_id")
    private Package packageElement;

    @OneToMany(mappedBy = "classifier", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Attribute> attributes = new ArrayList<>();

    @OneToMany(mappedBy = "classifier", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Method> methods = new ArrayList<>();

    // Relations où cette classe est la source
    @OneToMany(mappedBy = "sourceClass", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Relationship> outgoingRelationships = new ArrayList<>();

    // Relations où cette classe est la cible
    @OneToMany(mappedBy = "targetClass", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Relationship> incomingRelationships = new ArrayList<>();

    // Constructeurs
    public Classifier() {}

    public Classifier(String name, ClassifierType type, Diagram diagram) {
    	super.setName(name);
        this.type = type;
        this.diagram = diagram;
    }
}


