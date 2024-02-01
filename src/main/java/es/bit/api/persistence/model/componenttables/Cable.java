package es.bit.api.persistence.model.componenttables;

import com.fasterxml.jackson.annotation.JsonInclude;
import es.bit.api.persistence.model.basictables.CableColor;
import es.bit.api.persistence.model.basictables.CableType;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "cables")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Cable extends Component {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cable_type_id", referencedColumnName = "id")
    private CableType cableType;

    @ManyToMany
    @JoinTable(
            name = "cable_color_relations",
            joinColumns = @JoinColumn(name = "cable_id"),
            inverseJoinColumns = @JoinColumn(name = "cable_color_id")
    )
    private List<CableColor> cableColors = new ArrayList<>();


    public Cable() {
    }


    public CableType getCableType() {
        return cableType;
    }

    public void setCableType(CableType airFlow) {
        this.cableType = airFlow;
    }

    public List<CableColor> getCableColors() {
        return cableColors;
    }

    public void setCableColors(List<CableColor> motherboardFormFactors) {
        this.cableColors = motherboardFormFactors;
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
