package es.bit.api.persistence.model.components.attributes;

import es.bit.api.persistence.model.components.Cable;
import jakarta.persistence.*;

import java.util.*;

@Entity
@Table(name = "cable_colors")
public class CableColor extends GenericAttribute {
    @ManyToMany(mappedBy = "cableColors")
    private List<Cable> cables = new ArrayList<>();


    public CableColor() {}


    public List<Cable> getCables() {
        return cables;
    }

    public void setCables(List<Cable> cables) {
        this.cables = cables;
    }
}
