package es.bit.api.persistence.model.components;

import com.fasterxml.jackson.annotation.JsonInclude;
import es.bit.api.persistence.model.components.attributes.CaseFanSize;
import es.bit.api.persistence.model.components.attributes.CaseSize;
import es.bit.api.persistence.model.components.attributes.MotherboardFormFactor;
import es.bit.api.persistence.model.components.attributes.PowerSupplyFormFactor;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "cases")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Case extends Component {
    private Integer maxPsuLength;
    private Integer maxGpuLength;
    private Integer maxCpuFanHeight;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "case_size_id", referencedColumnName = "id")
    private CaseSize caseSize;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "case_fan_size_id", referencedColumnName = "id")
    private CaseFanSize caseFanSize;

    @ManyToMany
    @JoinTable(
            name = "case_motherboard_form_factor_relations",
            joinColumns = @JoinColumn(name = "case_id"),
            inverseJoinColumns = @JoinColumn(name = "motherboard_form_factor_id")
    )
    private List<MotherboardFormFactor> motherboardFormFactors = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "case_psu_form_factor_relations",
            joinColumns = @JoinColumn(name = "case_id"),
            inverseJoinColumns = @JoinColumn(name = "power_supply_form_factor_id")
    )
    private List<PowerSupplyFormFactor> powerSupplyFormFactors = new ArrayList<>();
}
