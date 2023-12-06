package es.bit.api.persistence.model.componenttables;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "case_fans")
public class CaseFan extends Component {
    private Float airFlow;
    private Integer size;


    public CaseFan() {
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
