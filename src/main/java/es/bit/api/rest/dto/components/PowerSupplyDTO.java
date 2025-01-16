package es.bit.api.rest.dto.components;

import com.fasterxml.jackson.annotation.JsonInclude;
import es.bit.api.persistence.model.components.enums.PowerSupplyTypes;
import es.bit.api.rest.dto.components.attributes.PowerSupplyFormFactorDTO;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PowerSupplyDTO extends ComponentDTO {
    private Integer wattage;
    private Integer length;
    private PowerSupplyTypes type;
    private PowerSupplyFormFactorDTO powerSupplyFormFactor;
}
