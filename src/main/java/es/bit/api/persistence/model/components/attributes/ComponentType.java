package es.bit.api.persistence.model.components.attributes;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "component_types")
@Setter
@Getter
@NoArgsConstructor
public class ComponentType extends GenericAttribute {
    private String apiName;
    private String nameIdentifier;
}
