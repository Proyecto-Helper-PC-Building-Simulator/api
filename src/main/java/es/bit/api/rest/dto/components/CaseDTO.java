package es.bit.api.rest.dto.components;

import com.fasterxml.jackson.annotation.JsonInclude;
import es.bit.api.rest.dto.components.attributes.CaseFanSizeDTO;
import es.bit.api.rest.dto.components.attributes.CaseSizeDTO;
import es.bit.api.rest.dto.components.attributes.MotherboardFormFactorDTO;
import es.bit.api.rest.dto.components.attributes.PowerSupplyFormFactorDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CaseDTO extends ComponentDTO {
    private Integer maxPsuLength;
    private Integer maxGpuLength;
    private Integer maxCpuFanHeight;
    private CaseSizeDTO caseSize;
    private CaseFanSizeDTO caseFanSize;
    private List<MotherboardFormFactorDTO> motherboardFormFactors = new ArrayList<>();
    private List<PowerSupplyFormFactorDTO> powerSupplyFormFactors = new ArrayList<>();
}
