package es.bit.api.persistence.model.components.attributes;

import es.bit.api.persistence.model.components.CpuCooler;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "cpu_sockets")
public class CpuSocket extends GenericAttribute {
    @ManyToMany(mappedBy = "cpuSockets")
    private List<CpuCooler> cpuCoolers = new ArrayList<>();


    public CpuSocket() {}


    public List<CpuCooler> getCpuCoolers() {
        return cpuCoolers;
    }

    public void setCpuCoolers(List<CpuCooler> cpuCoolers) {
        this.cpuCoolers = cpuCoolers;
    }
}
