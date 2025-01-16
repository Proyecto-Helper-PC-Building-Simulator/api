package es.bit.api.rest.dto.components.attributes;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ComponentTypeDTO extends GenericAttributeDTO {
    private String apiName;
    private String nameIdentifier;
}
