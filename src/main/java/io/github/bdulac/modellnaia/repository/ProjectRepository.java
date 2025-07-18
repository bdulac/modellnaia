package io.github.bdulac.modellnaia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.github.bdulac.modellnaia.entity.Project;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

}
