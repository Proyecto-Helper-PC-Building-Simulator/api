package es.bit.api.rest.dto.componenttables;

import es.bit.api.rest.dto.basictables.CableTypeDTO;

public class CableDTO extends ComponentDTO {
    private CableTypeDTO cableType;


    public CableDTO() {
    }


    public CableTypeDTO getCableType() {
        return cableType;
    }

    public void setCableType(CableTypeDTO airFlow) {
        this.cableType = airFlow;
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
