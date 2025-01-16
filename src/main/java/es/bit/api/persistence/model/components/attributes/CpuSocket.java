package es.bit.api.persistence.model.components.attributes;

import es.bit.api.persistence.model.components.CpuCooler;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "cpu_sockets")
public class CpuSocket extends GenericAttribute {
    @ManyToMany(mappedBy = "cpuSockets")
    private List<CpuCooler> cpuCoolers = new ArrayList<>();
}
