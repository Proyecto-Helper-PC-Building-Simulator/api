package es.bit.api.rest.mapper.basictables;

import es.bit.api.persistence.model.basictables.ComponentType;
import es.bit.api.rest.dto.basictables.ComponentTypeDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ComponentTypeMapper {
    public static ComponentTypeDTO toDTO(ComponentType componentType) {
        ComponentTypeDTO componentTypeDTO = new ComponentTypeDTO();

        if (componentType != null) {
            componentTypeDTO.setId(componentType.getId());
            componentTypeDTO.setName(componentType.getName());
            componentTypeDTO.setApiName(componentType.getApiName());
        }

        return componentTypeDTO;
    }


    public static ComponentTypeDTO toDTO(Optional<ComponentType> componentTypeOptional) {
        return componentTypeOptional.map(ComponentTypeMapper::toDTO).orElse(null);
    }


    public static List<ComponentTypeDTO> toDTO(List<ComponentType> componentTypes) {
        List<ComponentTypeDTO> componentTypesDTO = new ArrayList<>();

        if (componentTypes == null) {
            return componentTypesDTO;
        }

        for (ComponentType componentType : componentTypes) {
            componentTypesDTO.add(ComponentTypeMapper.toDTO(componentType));
        }

        return componentTypesDTO;
    }


    public static ComponentType toBD(ComponentTypeDTO componentTypeDTO) {
        ComponentType componentType = new ComponentType();

        if (componentTypeDTO != null) {
            componentType.setId(componentTypeDTO.getId());
            componentType.setName(componentTypeDTO.getName());
            componentType.setApiName(componentTypeDTO.getApiName());
        }

        return componentType;
    }
}
