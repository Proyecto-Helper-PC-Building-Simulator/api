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
@Table(name = "motherboard_form_factors")
public class MotherboardFormFactor extends GenericAttribute {
    @ManyToMany(mappedBy = "motherboardFormFactors")
    private List<Case> cases = new ArrayList<>();
}
