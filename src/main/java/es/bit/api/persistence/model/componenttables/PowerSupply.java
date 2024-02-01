package es.bit.api.persistence.model.componenttables;

import com.fasterxml.jackson.annotation.JsonInclude;
import es.bit.api.persistence.model.basictables.PowerSupplyFormFactor;
import es.bit.api.persistence.model.componenttables.enums.PowerSupplyTypes;
import jakarta.persistence.*;

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


    public PowerSupply() {
    }


    public Integer getWattage() {
        return wattage;
    }

    public void setWattage(Integer wattage) {
        this.wattage = wattage;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public PowerSupplyTypes getType() {
        return type;
    }

    public void setType(PowerSupplyTypes type) {
        this.type = type;
    }

    public PowerSupplyFormFactor getPowerSupplyFormFactor() {
        return powerSupplyFormFactor;
    }

    public void setPowerSupplyFormFactor(PowerSupplyFormFactor powerSupplyFormFactor) {
        this.powerSupplyFormFactor = powerSupplyFormFactor;
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
