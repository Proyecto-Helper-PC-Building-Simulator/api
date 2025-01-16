package es.bit.api.rest.dto.components;

import com.fasterxml.jackson.annotation.JsonInclude;
import es.bit.api.rest.dto.components.attributes.CpuSerieDTO;
import es.bit.api.rest.dto.components.attributes.CpuSocketDTO;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CpuDTO extends ComponentDTO {
    private Integer frequency;
    private Integer cores;
    private Integer wattage;
    private CpuSocketDTO cpuSocket;
    private CpuSerieDTO cpuSerie;
}
