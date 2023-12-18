package es.bit.api.persistence.model.componenttables;

import com.fasterxml.jackson.annotation.JsonInclude;
import es.bit.api.persistence.model.basictables.CaseFanSize;
import es.bit.api.persistence.model.basictables.CaseSize;
import es.bit.api.persistence.model.basictables.MotherboardFormFactor;
import es.bit.api.persistence.model.basictables.PowerSupplyFormFactor;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

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


    public Case() {
    }


    public Integer getMaxPsuLength() {
        return maxPsuLength;
    }

    public void setMaxPsuLength(Integer maxPsuLength) {
        this.maxPsuLength = maxPsuLength;
    }

    public Integer getMaxGpuLength() {
        return maxGpuLength;
    }

    public void setMaxGpuLength(Integer maxGpuLength) {
        this.maxGpuLength = maxGpuLength;
    }

    public Integer getMaxCpuFanHeight() {
        return maxCpuFanHeight;
    }

    public void setMaxCpuFanHeight(Integer maxCpuFanHeight) {
        this.maxCpuFanHeight = maxCpuFanHeight;
    }

    public CaseSize getCaseSize() {
        return caseSize;
    }

    public void setCaseSize(CaseSize caseSize) {
        this.caseSize = caseSize;
    }

    public CaseFanSize getCaseFanSize() {
        return caseFanSize;
    }

    public void setCaseFanSize(CaseFanSize caseFanSize) {
        this.caseFanSize = caseFanSize;
    }

    public List<MotherboardFormFactor> getMotherboardFormFactors() {
        return motherboardFormFactors;
    }

    public void setMotherboardFormFactors(List<MotherboardFormFactor> motherboardFormFactors) {
        this.motherboardFormFactors = motherboardFormFactors;
    }

    public List<PowerSupplyFormFactor> getPowerSupplyFormFactors() {
        return powerSupplyFormFactors;
    }

    public void setPowerSupplyFormFactors(List<PowerSupplyFormFactor> powerSupplyFormFactors) {
        this.powerSupplyFormFactors = powerSupplyFormFactors;
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
