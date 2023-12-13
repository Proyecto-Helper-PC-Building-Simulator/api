package es.bit.api.persistence.model.componenttables;

import com.fasterxml.jackson.annotation.JsonInclude;
import es.bit.api.persistence.model.basictables.CpuSerie;
import es.bit.api.persistence.model.basictables.CpuSocket;
import jakarta.persistence.*;

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


    public Cpu() {
    }


    public Integer getFrequency() {
        return frequency;
    }

    public void setFrequency(Integer frequency) {
        this.frequency = frequency;
    }

    public Integer getCores() {
        return cores;
    }

    public void setCores(Integer cores) {
        this.cores = cores;
    }

    public Integer getWattage() {
        return wattage;
    }

    public void setWattage(Integer wattage) {
        this.wattage = wattage;
    }

    public CpuSocket getCpuSocket() {
        return cpuSocket;
    }

    public void setCpuSocket(CpuSocket cpuSocket) {
        this.cpuSocket = cpuSocket;
    }

    public CpuSerie getCpuSerie() {
        return cpuSerie;
    }

    public void setCpuSerie(CpuSerie cpuSerie) {
        this.cpuSerie = cpuSerie;
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
