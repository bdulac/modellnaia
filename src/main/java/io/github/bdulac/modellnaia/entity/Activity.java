package io.github.bdulac.modellnaia.entity;

import java.util.ArrayList;
import java.util.List;

import io.github.bdulac.modellnaia.enums.ActivityType;
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
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

//Élément d'un diagramme d'activités
@Entity
@Table(name = "activities")
@Data
public class Activity extends UmlElement {
 
 @Size(max = 1000)
 @Column(name = "description", length = 1000)
 private String description;

 @Enumerated(EnumType.STRING)
 @Column(name = "type", nullable = false)
 private ActivityType type;

 @Size(max = 2000)
 @Column(name = "code", length = 2000)
 private String code; // Code Java associé à l'activité

 @Column(name = "condition_expression", length = 500)
 private String conditionExpression; // Pour les décisions

 // Position dans le diagramme
 @Column(name = "position_x")
 private Double positionX;

 @Column(name = "position_y")
 private Double positionY;

 @ManyToOne(fetch = FetchType.LAZY)
 @JoinColumn(name = "diagram_id", nullable = false)
 private Diagram diagram;

 // Flux sortants de cette activité
 @OneToMany(mappedBy = "sourceActivity", cascade = CascadeType.ALL, orphanRemoval = true)
 private List<ActivityFlow> outgoingFlows = new ArrayList<>();

 // Flux entrants vers cette activité
 @OneToMany(mappedBy = "targetActivity", cascade = CascadeType.ALL, orphanRemoval = true)
 private List<ActivityFlow> incomingFlows = new ArrayList<>();

 // Constructeurs
 public Activity() {}

 public Activity(String name, ActivityType type, Diagram diagram) {
     setName(name);
     this.type = type;
     this.diagram = diagram;
 }
}
