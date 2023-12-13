package es.bit.api.rest.dto.componenttables;

import com.fasterxml.jackson.annotation.JsonInclude;
import es.bit.api.rest.dto.basictables.ComponentTypeDTO;
import es.bit.api.rest.dto.basictables.LightingDTO;
import es.bit.api.rest.dto.basictables.ManufacturerDTO;

import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ComponentDTO {
    private int componentId;
    private String name;
    private int price;
    private ManufacturerDTO manufacturerDTO;
    private LightingDTO lightingDTO;
    private ComponentTypeDTO componentTypeDTO;


    public ComponentDTO() {
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

    public ManufacturerDTO getManufacturerDTO() {
        return manufacturerDTO;
    }

    public void setManufacturerDTO(ManufacturerDTO manufacturer) {
        this.manufacturerDTO = manufacturer;
    }

    public LightingDTO getLightingDTO() {
        return lightingDTO;
    }

    public void setLightingDTO(LightingDTO lighting) {
        this.lightingDTO = lighting;
    }

    public ComponentTypeDTO getComponentTypeDTO() {
        return componentTypeDTO;
    }

    public void setComponentTypeDTO(ComponentTypeDTO componentTypeDTO) {
        this.componentTypeDTO = componentTypeDTO;
    }


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
