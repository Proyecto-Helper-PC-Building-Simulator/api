package es.bit.api.persistence.model.componenttables;

import com.fasterxml.jackson.annotation.JsonInclude;
import es.bit.api.persistence.model.componenttables.enums.CoolerTypes;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;

@Entity
@Table(name = "cpu_coolers")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CpuCooler extends Component {
    private Float airFlow;
    private Integer height;
    private Integer size;

    @Enumerated(EnumType.STRING)
    private CoolerTypes type;


    public CpuCooler() {
    }


    public Float getAirFlow() {
        return airFlow;
    }

    public void setAirFlow(Float air_flow) {
        this.airFlow = air_flow;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public CoolerTypes getType() {
        return type;
    }

    public void setType(CoolerTypes type) {
        this.type = type;
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
