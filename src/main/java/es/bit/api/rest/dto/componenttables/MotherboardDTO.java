package es.bit.api.rest.dto.componenttables;

import com.fasterxml.jackson.annotation.JsonInclude;
import es.bit.api.rest.dto.basictables.CpuSocketDTO;
import es.bit.api.rest.dto.basictables.MotherboardChipsetDTO;
import es.bit.api.rest.dto.basictables.MotherboardFormFactorDTO;
import es.bit.api.rest.dto.basictables.MultiGpuTypeDTO;

import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class MotherboardDTO extends ComponentDTO {
    private Integer maxRamSpeed;
    private MotherboardChipsetDTO motherboardChipset;
    private MotherboardFormFactorDTO motherboardFormFactor;
    private CpuSocketDTO cpuSocket;
    private List<MultiGpuTypeDTO> multiGpuTypes = new ArrayList<>();


    public MotherboardDTO() {
    }


    public Integer getMaxRamSpeed() {
        return maxRamSpeed;
    }

    public void setMaxRamSpeed(Integer maxRamSpeed) {
        this.maxRamSpeed = maxRamSpeed;
    }

    public MotherboardChipsetDTO getMotherboardChipset() {
        return motherboardChipset;
    }

    public void setMotherboardChipset(MotherboardChipsetDTO motherboardChipset) {
        this.motherboardChipset = motherboardChipset;
    }

    public MotherboardFormFactorDTO getMotherboardFormFactor() {
        return motherboardFormFactor;
    }

    public void setMotherboardFormFactor(MotherboardFormFactorDTO motherboardFormFactor) {
        this.motherboardFormFactor = motherboardFormFactor;
    }

    public CpuSocketDTO getCpuSocket() {
        return cpuSocket;
    }

    public void setCpuSocket(CpuSocketDTO cpuSocket) {
        this.cpuSocket = cpuSocket;
    }

    public List<MultiGpuTypeDTO> getMultiGpuTypes() {
        return multiGpuTypes;
    }

    public void setMultiGpuTypes(List<MultiGpuTypeDTO> multiGpuTypes) {
        this.multiGpuTypes = multiGpuTypes;
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
