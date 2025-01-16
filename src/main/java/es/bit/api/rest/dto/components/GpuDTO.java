package es.bit.api.rest.dto.components;

import com.fasterxml.jackson.annotation.JsonInclude;
import es.bit.api.persistence.model.components.enums.ChipsetBrands;
import es.bit.api.rest.dto.components.attributes.GpuChipsetSerieDTO;
import es.bit.api.rest.dto.components.attributes.MultiGpuTypeDTO;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GpuDTO extends ComponentDTO {
    private Integer vram;
    private Integer memoryFrequency;
    private Integer coreFrequency;
    private Integer length;
    private Integer wattage;
    private ChipsetBrands chipsetBrand;
    private GpuChipsetSerieDTO gpuChipsetSerie;
    private MultiGpuTypeDTO multiGpuType;
}
