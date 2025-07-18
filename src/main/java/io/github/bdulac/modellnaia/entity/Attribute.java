package io.github.bdulac.modellnaia.entity;

import io.github.bdulac.modellnaia.enums.Visibility;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

/** Attribut d'une classe. */
@Entity
@Table(name = "attributes")
@Data
public class Attribute extends UmlElement {

 @NotBlank(message = "Le type de l'attribut est obligatoire")
 @Column(name = "type", nullable = false, length = 100)
 private String type;

 @Enumerated(EnumType.STRING)
 @Column(name = "visibility", nullable = false)
 private Visibility visibility = Visibility.PRIVATE;

 @Column(name = "is_static")
 private boolean isStatic = false;

 @Column(name = "is_final")
 private boolean isFinal = false;

 @Column(name = "default_value", length = 255)
 private String defaultValue;

 @Size(max = 500)
 @Column(name = "description", length = 500)
 private String description;

 @ManyToOne(fetch = FetchType.LAZY)
 @JoinColumn(name = "classifiers.id", nullable = false)
 private Classifier classifier;

 public Attribute() {}

 public Attribute(String name, String type, Classifier classifier) {
     setName(name);
     this.type = type;
     this.classifier = classifier;
 }
}
