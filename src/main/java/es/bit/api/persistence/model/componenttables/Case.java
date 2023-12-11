package es.bit.api.persistence.model.componenttables;

import com.fasterxml.jackson.annotation.JsonInclude;
import es.bit.api.persistence.model.basictables.CaseFanSize;
import es.bit.api.persistence.model.basictables.CaseSize;
import jakarta.persistence.*;

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


    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
