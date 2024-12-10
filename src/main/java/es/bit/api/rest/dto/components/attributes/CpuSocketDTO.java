package es.bit.api.rest.dto.components.attributes;

import es.bit.api.rest.dto.components.CpuCoolerDTO;

import java.util.ArrayList;
import java.util.List;

public class CpuSocketDTO extends GenericAttributeDTO {
    private List<CpuCoolerDTO> cpuCoolers = new ArrayList<>();


    public CpuSocketDTO() {}


    public List<CpuCoolerDTO> getCpuCoolers() {
        return cpuCoolers;
    }

    public void setCpuCoolers(List<CpuCoolerDTO> cpuCoolers) {
        this.cpuCoolers = cpuCoolers;
    }
}
