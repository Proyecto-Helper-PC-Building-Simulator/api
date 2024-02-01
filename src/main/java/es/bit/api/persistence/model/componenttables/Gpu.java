package es.bit.api.persistence.model.componenttables;

import com.fasterxml.jackson.annotation.JsonInclude;
import es.bit.api.persistence.model.basictables.GpuChipsetSerie;
import es.bit.api.persistence.model.basictables.MultiGpuType;
import es.bit.api.persistence.model.componenttables.enums.ChipsetBrands;
import jakarta.persistence.*;

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


    public Gpu() {
    }


    public Integer getVram() {
        return vram;
    }

    public void setVram(Integer vram) {
        this.vram = vram;
    }

    public Integer getMemoryFrequency() {
        return memoryFrequency;
    }

    public void setMemoryFrequency(Integer memoryFrequency) {
        this.memoryFrequency = memoryFrequency;
    }

    public Integer getCoreFrequency() {
        return coreFrequency;
    }

    public void setCoreFrequency(Integer coreFrequency) {
        this.coreFrequency = coreFrequency;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public Integer getWattage() {
        return wattage;
    }

    public void setWattage(Integer wattage) {
        this.wattage = wattage;
    }

    public ChipsetBrands getChipsetBrand() {
        return chipsetBrand;
    }

    public void setChipsetBrand(ChipsetBrands chipsetBrand) {
        this.chipsetBrand = chipsetBrand;
    }

    public GpuChipsetSerie getGpuChipsetSerie() {
        return gpuChipsetSerie;
    }

    public void setGpuChipsetSerie(GpuChipsetSerie gpuChipsetSerie) {
        this.gpuChipsetSerie = gpuChipsetSerie;
    }

    public MultiGpuType getMultiGpuType() {
        return multiGpuType;
    }

    public void setMultiGpuType(MultiGpuType multiGpuType) {
        this.multiGpuType = multiGpuType;
    }


    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
