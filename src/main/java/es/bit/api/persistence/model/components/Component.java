package es.bit.api.persistence.model.components;

import com.fasterxml.jackson.annotation.JsonInclude;
import es.bit.api.persistence.model.components.attributes.ComponentType;
import es.bit.api.persistence.model.components.attributes.Lighting;
import es.bit.api.persistence.model.components.attributes.Manufacturer;
import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Builder
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "components")
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
public class Component {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int componentId;

    private String name;
    private int price;
    private int level;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manufacturer_id", referencedColumnName = "id")
    private Manufacturer manufacturer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lighting_id", referencedColumnName = "id")
    private Lighting lighting;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "component_type_id", referencedColumnName = "id")
    private ComponentType componentType;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Component that = (Component) o;
        return componentId == that.componentId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(componentId);
    }
}
