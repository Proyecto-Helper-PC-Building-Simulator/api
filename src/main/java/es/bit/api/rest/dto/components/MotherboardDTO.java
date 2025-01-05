package es.bit.api.rest.dto.components;

import com.fasterxml.jackson.annotation.JsonInclude;
import es.bit.api.rest.dto.components.attributes.CpuSocketDTO;
import es.bit.api.rest.dto.components.attributes.MotherboardChipsetDTO;
import es.bit.api.rest.dto.components.attributes.MotherboardFormFactorDTO;
import es.bit.api.rest.dto.components.attributes.MultiGpuTypeDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MotherboardDTO extends ComponentDTO {
    private Integer maxRamSpeed;
    private MotherboardChipsetDTO motherboardChipset;
    private MotherboardFormFactorDTO motherboardFormFactor;
    private CpuSocketDTO cpuSocket;
    private List<MultiGpuTypeDTO> multiGpuTypes = new ArrayList<>();
}
