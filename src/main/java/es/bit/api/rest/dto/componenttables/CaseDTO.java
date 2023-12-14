package es.bit.api.rest.dto.componenttables;

import com.fasterxml.jackson.annotation.JsonInclude;
import es.bit.api.rest.dto.basictables.CaseFanSizeDTO;
import es.bit.api.rest.dto.basictables.CaseSizeDTO;
import es.bit.api.rest.dto.basictables.MotherboardFormFactorDTO;

import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CaseDTO extends ComponentDTO {
    private Integer maxPsuLength;
    private Integer maxGpuLength;
    private Integer maxCpuFanHeight;
    private CaseSizeDTO caseSize;
    private CaseFanSizeDTO caseFanSize;
    private List<MotherboardFormFactorDTO> motherboardFormFactors = new ArrayList<>();


    public CaseDTO() {
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

    public CaseSizeDTO getCaseSize() {
        return caseSize;
    }

    public void setCaseSize(CaseSizeDTO caseSize) {
        this.caseSize = caseSize;
    }

    public CaseFanSizeDTO getCaseFanSize() {
        return caseFanSize;
    }

    public void setCaseFanSize(CaseFanSizeDTO caseFanSize) {
        this.caseFanSize = caseFanSize;
    }

    public List<MotherboardFormFactorDTO> getMotherboardFormFactors() {
        return motherboardFormFactors;
    }

    public void setMotherboardFormFactors(List<MotherboardFormFactorDTO> motherboardFormFactors) {
        this.motherboardFormFactors = motherboardFormFactors;
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
