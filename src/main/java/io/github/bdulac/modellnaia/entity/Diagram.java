package io.github.bdulac.modellnaia.entity;

import java.util.ArrayList;
import java.util.List;

import io.github.bdulac.modellnaia.enums.DiagramType;
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

/** Diagramme (classe ou activité). */
@Entity
@Table(name = "diagrams")
@Data
public class Diagram extends UmlElement {

	@Enumerated(EnumType.STRING)
	@Column(name = "type", nullable = false)
	private DiagramType type;

	@Size(max = 500)
	@Column(name = "description", length = 500)
	private String description;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "project_id", nullable = false)
	private Project project;

	@OneToMany(mappedBy = "diagram", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Classifier> classifiers = new ArrayList<>();

	@OneToMany(mappedBy = "diagram", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Activity> activities = new ArrayList<>();

	@OneToMany(mappedBy = "diagram", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Relationship> relationships = new ArrayList<>();

	// Constructeurs
	public Diagram() {
	}

	public Diagram(String name, DiagramType type, Project project) {
		super.setName(name);
		this.type = type;
		this.project = project;
	}
}