package es.bit.api.rest.dto.basictables;

import com.fasterxml.jackson.annotation.JsonInclude;
import es.bit.api.rest.dto.componenttables.CaseDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class MotherboardFormFactorDTO {
    private int id;
    private String name;
    private List<CaseDTO> cases = new ArrayList<>();


    public MotherboardFormFactorDTO() {}


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

    public List<CaseDTO> getCases() {
        return cases;
    }

    public void setCases(List<CaseDTO> cases) {
        this.cases = cases;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MotherboardFormFactorDTO that = (MotherboardFormFactorDTO) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
