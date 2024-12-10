package es.bit.api.persistence.model.components.attributes;

import es.bit.api.persistence.model.components.Case;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "motherboard_form_factors")
public class MotherboardFormFactor extends GenericAttribute {
    @ManyToMany(mappedBy = "motherboardFormFactors")
    private List<Case> cases = new ArrayList<>();


    public MotherboardFormFactor() {}


    public List<Case> getCases() {
        return cases;
    }

    public void setCases(List<Case> cases) {
        this.cases = cases;
    }
}
