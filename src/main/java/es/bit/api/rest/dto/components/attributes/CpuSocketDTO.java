package es.bit.api.rest.dto.components.attributes;

import es.bit.api.rest.dto.components.CpuCoolerDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class CpuSocketDTO extends GenericAttributeDTO {
    private List<CpuCoolerDTO> cpuCoolers = new ArrayList<>();
}
