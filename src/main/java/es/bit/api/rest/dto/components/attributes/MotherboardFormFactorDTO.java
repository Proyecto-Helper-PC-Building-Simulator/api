package es.bit.api.rest.dto.components.attributes;

import es.bit.api.rest.dto.components.CaseDTO;

import java.util.ArrayList;
import java.util.List;

public class MotherboardFormFactorDTO extends GenericAttributeDTO {
    private List<CaseDTO> cases = new ArrayList<>();


    public MotherboardFormFactorDTO() {}


    public List<CaseDTO> getCases() {
        return cases;
    }

    public void setCases(List<CaseDTO> cases) {
        this.cases = cases;
    }
}
