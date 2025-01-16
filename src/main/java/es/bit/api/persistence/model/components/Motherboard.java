package es.bit.api.persistence.model.components;

import com.fasterxml.jackson.annotation.JsonInclude;
import es.bit.api.persistence.model.components.attributes.CpuSocket;
import es.bit.api.persistence.model.components.attributes.MotherboardChipset;
import es.bit.api.persistence.model.components.attributes.MotherboardFormFactor;
import es.bit.api.persistence.model.components.attributes.MultiGpuType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
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

    @ManyToMany
    @JoinTable(
            name = "motherboard_multi_gpu_relations",
            joinColumns = @JoinColumn(name = "motherboard_id"),
            inverseJoinColumns = @JoinColumn(name = "multi_gpu_id")
    )
    private List<MultiGpuType> multiGpuTypes = new ArrayList<>();
}
