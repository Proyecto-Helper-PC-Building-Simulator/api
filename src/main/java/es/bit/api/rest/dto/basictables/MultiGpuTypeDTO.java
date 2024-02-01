package es.bit.api.rest.dto.basictables;

import com.fasterxml.jackson.annotation.JsonInclude;
import es.bit.api.rest.dto.componenttables.MotherboardDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class MultiGpuTypeDTO {
    private int id;
    private String name;
    private List<MotherboardDTO> motherboards = new ArrayList<>();


    public MultiGpuTypeDTO() {}


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

    public List<MotherboardDTO> getMotherboards() {
        return motherboards;
    }

    public void setMotherboards(List<MotherboardDTO> motherboards) {
        this.motherboards = motherboards;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MultiGpuTypeDTO that = (MultiGpuTypeDTO) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
