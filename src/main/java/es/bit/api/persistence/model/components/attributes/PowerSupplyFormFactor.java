package es.bit.api.persistence.model.components.attributes;

import es.bit.api.persistence.model.components.Case;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "power_supply_form_factors")
public class PowerSupplyFormFactor extends GenericAttribute {
    @ManyToMany(mappedBy = "powerSupplyFormFactors")
    private List<Case> cases = new ArrayList<>();
}
