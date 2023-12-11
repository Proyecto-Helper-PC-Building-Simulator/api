package es.bit.api.rest.dto.componenttables;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CaseFanDTO extends ComponentDTO {
    private Float airFlow;
    private Integer size;


    public CaseFanDTO() {
    }


    public Float getAirFlow() {
        return airFlow;
    }

    public void setAirFlow(Float airFlow) {
        this.airFlow = airFlow;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
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
