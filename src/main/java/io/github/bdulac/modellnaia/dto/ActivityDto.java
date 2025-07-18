package io.github.bdulac.modellnaia.dto;

import io.github.bdulac.modellnaia.enums.ActivityType;
import lombok.Data;

@Data
public class ActivityDto extends UmlElementDto {
	private String description;
	
	private ActivityType type;
	
	private String code;
	
	private String conditionExpression;
	
	private Double positionX;
	
	private Double positionY;
	
	private Long diagramId;

}
