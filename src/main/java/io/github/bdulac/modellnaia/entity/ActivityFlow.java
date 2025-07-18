package io.github.bdulac.modellnaia.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.Data;

/*  Flux entre activités. */
@Entity
@Table(name = "activity_flows")
@Data
public class ActivityFlow extends UmlElement {

	@Column(name = "guard_condition", length = 500)
	private String guardCondition; // Condition de garde

	@Size(max = 500)
	@Column(name = "description", length = 500)
	private String description;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "diagram_id", nullable = false)
	private Diagram diagram;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "source_activity_id", nullable = false)
	private Activity sourceActivity;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "target_activity_id", nullable = false)
	private Activity targetActivity;

	// Constructeurs
	public ActivityFlow() {
	}

	public ActivityFlow(Activity sourceActivity, Activity targetActivity, Diagram diagram) {
		this.sourceActivity = sourceActivity;
		this.targetActivity = targetActivity;
		this.diagram = diagram;
	}
}
