package es.bit.api.persistence.model.components.attributes;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "component_types")
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ComponentType extends GenericAttribute {
    private String apiName;
    private String nameIdentifier;
}
