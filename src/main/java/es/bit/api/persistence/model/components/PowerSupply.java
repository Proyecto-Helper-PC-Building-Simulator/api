package es.bit.api.persistence.model.components;

import com.fasterxml.jackson.annotation.JsonInclude;
import es.bit.api.persistence.model.components.attributes.PowerSupplyFormFactor;
import es.bit.api.persistence.model.components.enums.PowerSupplyTypes;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "power_supplies")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PowerSupply extends Component {
    private Integer wattage;
    private Integer length;

    @Enumerated(EnumType.STRING)
    private PowerSupplyTypes type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "power_supply_form_factor_id", referencedColumnName = "id")
    private PowerSupplyFormFactor powerSupplyFormFactor;
}
