package io.github.bdulac.modellnaia.entity;

import java.util.ArrayList;
import java.util.List;

import io.github.bdulac.modellnaia.enums.Visibility;
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

/** Méthode d'une classe. */
@Entity
@Table(name = "methods")
@Data
public class Method extends UmlElement {

 @NotBlank(message = "Le nom de la méthode est obligatoire")
 @Column(name = "name", nullable = false, length = 100)
 private String name;

 @Column(name = "return_type", length = 100)
 private String returnType = "void";

 @Enumerated(EnumType.STRING)
 @Column(name = "visibility", nullable = false)
 private Visibility visibility = Visibility.PUBLIC;

 @Column(name = "is_static")
 private boolean isStatic = false;

 @Column(name = "is_final")
 private boolean isFinal = false;

 @Column(name = "is_abstract")
 private boolean isAbstract = false;

 @Size(max = 2000)
 @Column(name = "body", length = 2000)
 private String body; // Corps de la méthode pour génération

 @Size(max = 500)
 @Column(name = "description", length = 500)
 private String description;

 @ManyToOne(fetch = FetchType.LAZY)
 @JoinColumn(name = "classifier_id", nullable = false)
 private Classifier classifier;

 @OneToMany(mappedBy = "method", cascade = CascadeType.ALL, orphanRemoval = true)
 private List<Parameter> parameters = new ArrayList<>();

 // Constructeurs
 public Method() {}

 public Method(String name, String returnType, Classifier classElement) {
     this.name = name;
     this.returnType = returnType;
     this.classifier = classElement;
 }
}
