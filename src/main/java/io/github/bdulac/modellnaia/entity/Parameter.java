package io.github.bdulac.modellnaia.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

//Paramètre d'une méthode
@Entity
@Table(name = "parameters")
@Data
public class Parameter extends UmlElement {

	@NotBlank(message = "Le type du paramètre est obligatoire")
	@Column(name = "type", nullable = false, length = 100)
	private String type;

	@Column(name = "parameter_order")
	private Integer order;

	@Column(name = "default_value", length = 255)
	private String defaultValue;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "method_id", nullable = false)
	private Method method;

	// Constructeurs
	public Parameter() {
	}

	public Parameter(String name, String type, Integer order, Method method) {
		super.setName(name);
		this.type = type;
		this.order = order;
		this.method = method;
	}
}