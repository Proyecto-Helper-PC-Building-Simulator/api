package es.bit.api.rest.dto.components;

import com.fasterxml.jackson.annotation.JsonInclude;
import es.bit.api.rest.dto.components.attributes.ComponentTypeDTO;
import es.bit.api.rest.dto.components.attributes.LightingDTO;
import es.bit.api.rest.dto.components.attributes.ManufacturerDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Setter
@Getter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ComponentDTO {
    private int componentId;
    private String name;
    private int price;
    private int level;
    private ManufacturerDTO manufacturerDTO;
    private LightingDTO lightingDTO;
    private ComponentTypeDTO componentTypeDTO;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ComponentDTO that = (ComponentDTO) o;
        return componentId == that.componentId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(componentId);
    }
}
