package es.bit.api.rest.dto.components;

import com.fasterxml.jackson.annotation.JsonInclude;
import es.bit.api.rest.dto.components.attributes.CableColorDTO;
import es.bit.api.rest.dto.components.attributes.CableTypeDTO;

import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CableDTO extends ComponentDTO {
    private CableTypeDTO cableType;
    private List<CableColorDTO> cableColors = new ArrayList<>();


    public CableDTO() {
    }


    public CableTypeDTO getCableType() {
        return cableType;
    }

    public void setCableType(CableTypeDTO airFlow) {
        this.cableType = airFlow;
    }

    public List<CableColorDTO> getCableColors() {
        return cableColors;
    }

    public void setCableColors(List<CableColorDTO> cableColors) {
        this.cableColors = cableColors;
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
