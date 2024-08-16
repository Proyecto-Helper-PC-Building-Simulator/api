package es.bit.api.persistence.model.componenttables;

import com.fasterxml.jackson.annotation.JsonInclude;
import es.bit.api.persistence.model.basictables.ComponentType;
import es.bit.api.persistence.model.basictables.Lighting;
import es.bit.api.persistence.model.basictables.Manufacturer;
import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "components")
@JsonInclude(JsonInclude.Include.NON_NULL)
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


    public Component() {
    }


    public int getComponentId() {
        return componentId;
    }

    public void setComponentId(int componentId) {
        this.componentId = componentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Manufacturer getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(Manufacturer manufacturer) {
        this.manufacturer = manufacturer;
    }

    public Lighting getLighting() {
        return lighting;
    }

    public void setLighting(Lighting lighting) {
        this.lighting = lighting;
    }

    public ComponentType getComponentType() {
        return componentType;
    }

    public void setComponentType(ComponentType componentType) {
        this.componentType = componentType;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }


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
