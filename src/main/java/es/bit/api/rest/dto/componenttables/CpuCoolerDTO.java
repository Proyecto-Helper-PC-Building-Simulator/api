package es.bit.api.rest.dto.componenttables;

import com.fasterxml.jackson.annotation.JsonInclude;
import es.bit.api.persistence.model.componenttables.enums.CoolerTypes;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CpuCoolerDTO extends ComponentDTO {
    private Float airFlow;
    private Integer height;
    private Integer size;
    private CoolerTypes type;


    public CpuCoolerDTO() {
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
