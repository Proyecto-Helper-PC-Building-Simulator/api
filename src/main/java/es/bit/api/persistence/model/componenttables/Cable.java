package es.bit.api.persistence.model.componenttables;

import es.bit.api.persistence.model.basictables.CableType;
import jakarta.persistence.*;

@Entity
@Table(name = "cables")
public class Cable extends Component {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cable_type_id", referencedColumnName = "id")
    private CableType cableType;


    public Cable() {
    }


    public CableType getCableType() {
        return cableType;
    }

    public void setCableType(CableType airFlow) {
        this.cableType = airFlow;
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
