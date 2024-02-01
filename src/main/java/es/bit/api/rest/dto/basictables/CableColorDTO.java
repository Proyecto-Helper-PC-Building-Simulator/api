package es.bit.api.rest.dto.basictables;

import com.fasterxml.jackson.annotation.JsonInclude;
import es.bit.api.rest.dto.componenttables.CableDTO;

import java.util.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CableColorDTO {
    private int id;
    private String name;
    private List<CableDTO> cables = new ArrayList<>();


    public CableColorDTO() {}


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<CableDTO> getCables() {
        return cables;
    }

    public void setCables(List<CableDTO> cables) {
        this.cables = cables;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CableColorDTO that = (CableColorDTO) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
