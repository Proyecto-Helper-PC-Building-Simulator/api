package es.bit.api.rest.dto.componenttables;

import com.fasterxml.jackson.annotation.JsonInclude;
import es.bit.api.persistence.model.componenttables.enums.ChipsetBrands;
import es.bit.api.rest.dto.basictables.GpuChipsetSerieDTO;
import es.bit.api.rest.dto.basictables.MultiGpuTypeDTO;

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


    public GpuDTO() {
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

    public GpuChipsetSerieDTO getGpuChipsetSerie() {
        return gpuChipsetSerie;
    }

    public void setGpuChipsetSerie(GpuChipsetSerieDTO gpuChipsetSerie) {
        this.gpuChipsetSerie = gpuChipsetSerie;
    }

    public MultiGpuTypeDTO getMultiGpuType() {
        return multiGpuType;
    }

    public void setMultiGpuType(MultiGpuTypeDTO multiGpuType) {
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
