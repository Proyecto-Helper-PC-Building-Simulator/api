package es.bit.api.rest.dto.components;

import com.fasterxml.jackson.annotation.JsonInclude;
import es.bit.api.persistence.model.components.enums.CoolerTypes;
import es.bit.api.rest.dto.components.attributes.CpuSocketDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CpuCoolerDTO extends ComponentDTO {
    private Float airFlow;
    private Integer height;
    private Integer size;
    private CoolerTypes type;
    private List<CpuSocketDTO> cpuSockets = new ArrayList<>();
}
