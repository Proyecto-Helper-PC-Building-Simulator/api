package es.bit.api.persistence.model.components;

import com.fasterxml.jackson.annotation.JsonInclude;
import es.bit.api.persistence.model.components.attributes.CpuSocket;
import es.bit.api.persistence.model.components.enums.CoolerTypes;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "cpu_coolers")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CpuCooler extends Component {
    private Float airFlow;
    private Integer height;
    private Integer size;

    @Enumerated(EnumType.STRING)
    private CoolerTypes type;

    @ManyToMany
    @JoinTable(
            name = "cpu_cooler_socket_relations",
            joinColumns = @JoinColumn(name = "cpu_cooler_id"),
            inverseJoinColumns = @JoinColumn(name = "cpu_socket_id")
    )
    private List<CpuSocket> cpuSockets = new ArrayList<>();
}
