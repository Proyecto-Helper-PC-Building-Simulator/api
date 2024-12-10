package es.bit.api.rest.dto.components.attributes;

import com.fasterxml.jackson.annotation.JsonInclude;
import es.bit.api.rest.dto.components.CableDTO;

import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CableColorDTO extends GenericAttributeDTO {
    private List<CableDTO> cables = new ArrayList<>();


    public CableColorDTO() {}


    public List<CableDTO> getCables() {
        return cables;
    }

    public void setCables(List<CableDTO> cables) {
        this.cables = cables;
    }
}
