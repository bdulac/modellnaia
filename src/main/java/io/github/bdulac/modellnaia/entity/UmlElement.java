package io.github.bdulac.modellnaia.entity;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class UmlElement extends BaseEntity {

    @Version
    private Long version;
    
    @NotBlank(message = "Le nom de la classe est obligatoire")
    @Column(name = "name", nullable = false, length = 100)
    @Getter
    @Setter
    private String name;

}