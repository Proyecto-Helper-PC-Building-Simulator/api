package es.bit.api.persistence.model.components;

import com.fasterxml.jackson.annotation.JsonInclude;
import es.bit.api.persistence.model.components.attributes.GpuChipsetSerie;
import es.bit.api.persistence.model.components.attributes.MultiGpuType;
import es.bit.api.persistence.model.components.enums.ChipsetBrands;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "gpus")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Gpu extends Component {
    private Integer vram;
    private Integer memoryFrequency;
    private Integer coreFrequency;
    private Integer length;
    private Integer wattage;

    @Enumerated(EnumType.STRING)
    private ChipsetBrands chipsetBrand;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gpu_chipset_serie_id", referencedColumnName = "id")
    private GpuChipsetSerie gpuChipsetSerie;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "multi_gpu_type_id", referencedColumnName = "id")
    private MultiGpuType multiGpuType;
}
