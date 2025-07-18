package io.github.bdulac.modellnaia.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/** Projet UML (correspond à un repository Git). */
@Entity
@Data
@Table(name = "projects")
public class Project extends UmlElement {

    @Size(max = 500, message = "La description ne peut pas dépasser 500 caractères")
    @Column(name = "description", length = 500)
    private String description;

    @Column(name = "git_repository_path", length = 255)
    private String gitRepositoryPath;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Diagram> diagrams = new ArrayList<>();

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Package> packages = new ArrayList<>();

    public Project() {}

    public Project(String name, String description) {
        super.setName(name);
        this.description = description;
    }
}