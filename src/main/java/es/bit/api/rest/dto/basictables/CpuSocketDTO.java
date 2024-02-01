package es.bit.api.rest.dto.basictables;

import com.fasterxml.jackson.annotation.JsonInclude;
import es.bit.api.rest.dto.componenttables.CpuCoolerDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CpuSocketDTO {
    private int id;
    private String name;
    private List<CpuCoolerDTO> cpuCoolers = new ArrayList<>();


    public CpuSocketDTO() {}


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

    public List<CpuCoolerDTO> getCpuCoolers() {
        return cpuCoolers;
    }

    public void setCpuCoolers(List<CpuCoolerDTO> cpuCoolers) {
        this.cpuCoolers = cpuCoolers;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CpuSocketDTO that = (CpuSocketDTO) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
