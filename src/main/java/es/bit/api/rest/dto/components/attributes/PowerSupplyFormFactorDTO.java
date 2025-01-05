package es.bit.api.rest.dto.components.attributes;

import es.bit.api.rest.dto.components.CaseDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class PowerSupplyFormFactorDTO extends GenericAttributeDTO {
    private List<CaseDTO> cases = new ArrayList<>();
}
