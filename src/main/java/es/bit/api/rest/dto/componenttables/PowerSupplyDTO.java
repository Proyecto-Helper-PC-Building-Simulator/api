package es.bit.api.rest.dto.componenttables;

import com.fasterxml.jackson.annotation.JsonInclude;
import es.bit.api.persistence.model.componenttables.enums.PowerSupplyTypes;
import es.bit.api.rest.dto.basictables.PowerSupplyFormFactorDTO;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PowerSupplyDTO extends ComponentDTO {
    private Integer wattage;
    private Integer length;
    private PowerSupplyTypes type;
    private PowerSupplyFormFactorDTO powerSupplyFormFactor;


    public PowerSupplyDTO() {
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

    public PowerSupplyFormFactorDTO getPowerSupplyFormFactor() {
        return powerSupplyFormFactor;
    }

    public void setPowerSupplyFormFactor(PowerSupplyFormFactorDTO powerSupplyFormFactor) {
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
