package es.bit.api.persistence.model.componenttables;

import com.fasterxml.jackson.annotation.JsonInclude;
import es.bit.api.persistence.model.basictables.CpuSocket;
import es.bit.api.persistence.model.basictables.MotherboardChipset;
import es.bit.api.persistence.model.basictables.MotherboardFormFactor;
import jakarta.persistence.*;

@Entity
@Table(name = "motherboards")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Motherboard extends Component {
    private Integer maxRamSpeed;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "motherboard_chipset_id", referencedColumnName = "id")
    private MotherboardChipset motherboardChipset;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "motherboard_form_factor_id", referencedColumnName = "id")
    private MotherboardFormFactor motherboardFormFactor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cpu_socket_id", referencedColumnName = "id")
    private CpuSocket cpuSocket;


    public Motherboard() {
    }


    public Integer getMaxRamSpeed() {
        return maxRamSpeed;
    }

    public void setMaxRamSpeed(Integer maxRamSpeed) {
        this.maxRamSpeed = maxRamSpeed;
    }

    public MotherboardChipset getMotherboardChipset() {
        return motherboardChipset;
    }

    public void setMotherboardChipset(MotherboardChipset motherboardChipset) {
        this.motherboardChipset = motherboardChipset;
    }

    public MotherboardFormFactor getMotherboardFormFactor() {
        return motherboardFormFactor;
    }

    public void setMotherboardFormFactor(MotherboardFormFactor motherboardFormFactor) {
        this.motherboardFormFactor = motherboardFormFactor;
    }

    public CpuSocket getCpuSocket() {
        return cpuSocket;
    }

    public void setCpuSocket(CpuSocket cpuSocket) {
        this.cpuSocket = cpuSocket;
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
