package es.bit.api.rest.mapper.componenttables;

import es.bit.api.persistence.model.componenttables.Component;
import es.bit.api.rest.dto.componenttables.ComponentDTO;
import es.bit.api.rest.mapper.basictables.ComponentTypeMapper;
import es.bit.api.rest.mapper.basictables.LightingMapper;
import es.bit.api.rest.mapper.basictables.ManufacturerMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ComponentMapper {
    public static ComponentDTO toDTO(Component component) {
        ComponentDTO componentDTO = new ComponentDTO();
        componentDTO.setComponentId(component.getComponentId());
        componentDTO.setName(component.getName());
        componentDTO.setPrice(component.getPrice());
        componentDTO.setManufacturerDTO(ManufacturerMapper.toDTO(component.getManufacturer()));
        componentDTO.setLightingDTO(LightingMapper.toDTO(component.getLighting()));
        componentDTO.setComponentTypeDTO(ComponentTypeMapper.toDTO(component.getComponentType()));

        return componentDTO;
    }

    public static ComponentDTO toDTO(Optional<Component> componentOptional) {
        return componentOptional.map(ComponentMapper::toDTO).orElse(null);
    }

    public static List<ComponentDTO> toDTO(List<Component> components) {
        List<ComponentDTO> componentsDTO = new ArrayList<>();

        if (components == null)
            return componentsDTO;

        for (Component component : components) {
            componentsDTO.add(ComponentMapper.toDTO(component));
        }

        return componentsDTO;
    }

    public static Component toBD(ComponentDTO componentDTO) {
        Component component = new Component();
        component.setComponentId(componentDTO.getComponentId());
        component.setName(componentDTO.getName());
        component.setPrice(componentDTO.getPrice());
        component.setManufacturer(ManufacturerMapper.toBD(componentDTO.getManufacturerDTO()));
        component.setLighting(LightingMapper.toBD(componentDTO.getLightingDTO()));
        component.setComponentType(ComponentTypeMapper.toBD(componentDTO.getComponentTypeDTO()));

        return component;
    }
}
