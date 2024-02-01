package es.bit.api.persistence.model.basictables;

import com.fasterxml.jackson.annotation.JsonInclude;
import es.bit.api.persistence.model.componenttables.CpuCooler;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "cpu_sockets")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CpuSocket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;

    @ManyToMany(mappedBy = "cpuSockets")
    private List<CpuCooler> cpuCoolers = new ArrayList<>();


    public CpuSocket() {}


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

    public List<CpuCooler> getCpuCoolers() {
        return cpuCoolers;
    }

    public void setCpuCoolers(List<CpuCooler> cpuCoolers) {
        this.cpuCoolers = cpuCoolers;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CpuSocket that = (CpuSocket) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
