package io.github.bdulac.modellnaia.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/** Package UML. */
@Entity
@Table(name = "packages")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Package extends UmlElement {

	@NotBlank(message = "Le nom du package est obligatoire")
	@Column(name = "name", nullable = false, length = 100)
	private String name;

	@Column(name = "full_name", nullable = false, length = 255)
	private String fullName; // ex: com.example.model

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "project_id", nullable = false)
	private Project project;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "parent_package_id")
	private Package parentPackage;

	@OneToMany(mappedBy = "parentPackage", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Package> subPackages = new ArrayList<>();

	@OneToMany(mappedBy = "packageElement", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Classifier> classifiers = new ArrayList<>();
}
