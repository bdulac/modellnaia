package io.github.bdulac.modellnaia.dto;

import java.util.List;

public class ProjectDto extends UmlElementDto {
	
	private String description;
	private String gitRepositoryPath;
	private List<DiagramDto> diagrams;

}
