package es.bit.api.persistence.model.components.attributes;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "component_types")
@Setter
@Getter
public class ComponentType extends GenericAttribute {
    private String apiName;
    private String nameIdentifier;
}
