package es.bit.api.rest.dto.componenttables;

import com.fasterxml.jackson.annotation.JsonInclude;
import es.bit.api.rest.dto.basictables.CpuSerieDTO;
import es.bit.api.rest.dto.basictables.CpuSocketDTO;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CpuDTO extends ComponentDTO {
    private Integer frequency;
    private Integer cores;
    private Integer wattage;
    private CpuSocketDTO cpuSocket;
    private CpuSerieDTO cpuSerie;


    public CpuDTO() {
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

    public CpuSocketDTO getCpuSocket() {
        return cpuSocket;
    }

    public void setCpuSocket(CpuSocketDTO cpuSocket) {
        this.cpuSocket = cpuSocket;
    }

    public CpuSerieDTO getCpuSerie() {
        return cpuSerie;
    }

    public void setCpuSerie(CpuSerieDTO cpuSerie) {
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
