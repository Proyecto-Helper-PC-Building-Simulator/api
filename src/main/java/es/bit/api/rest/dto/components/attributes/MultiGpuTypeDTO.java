package es.bit.api.rest.dto.components.attributes;

import es.bit.api.rest.dto.components.MotherboardDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class MultiGpuTypeDTO extends GenericAttributeDTO {
    private List<MotherboardDTO> motherboards = new ArrayList<>();
}
