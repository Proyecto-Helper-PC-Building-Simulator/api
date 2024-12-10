package es.bit.api.rest.dto.components.attributes;

import es.bit.api.rest.dto.components.MotherboardDTO;

import java.util.ArrayList;
import java.util.List;

public class MultiGpuTypeDTO extends GenericAttributeDTO {
    private List<MotherboardDTO> motherboards = new ArrayList<>();


    public MultiGpuTypeDTO() {}


    public List<MotherboardDTO> getMotherboards() {
        return motherboards;
    }

    public void setMotherboards(List<MotherboardDTO> motherboards) {
        this.motherboards = motherboards;
    }
}
