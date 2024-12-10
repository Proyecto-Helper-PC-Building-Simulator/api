package es.bit.api.persistence.model.components.attributes;

import es.bit.api.persistence.model.components.Case;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "power_supply_form_factors")
public class PowerSupplyFormFactor extends GenericAttribute {
    @ManyToMany(mappedBy = "powerSupplyFormFactors")
    private List<Case> cases = new ArrayList<>();


    public PowerSupplyFormFactor() {}


    public List<Case> getCases() {
        return cases;
    }

    public void setCases(List<Case> cases) {
        this.cases = cases;
    }
}
