package es.bit.api.persistence.model.components;

import com.fasterxml.jackson.annotation.JsonInclude;
import es.bit.api.persistence.model.components.attributes.CpuSerie;
import es.bit.api.persistence.model.components.attributes.CpuSocket;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "cpus")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Cpu extends Component {
    private Integer frequency;
    private Integer cores;
    private Integer wattage;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cpu_socket_id", referencedColumnName = "id")
    private CpuSocket cpuSocket;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cpu_serie_id", referencedColumnName = "id")
    private CpuSerie cpuSerie;
}
