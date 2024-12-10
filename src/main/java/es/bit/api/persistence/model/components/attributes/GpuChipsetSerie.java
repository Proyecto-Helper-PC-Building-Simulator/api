package es.bit.api.persistence.model.components.attributes;

import jakarta.persistence.*;

@Entity
@Table(name = "gpu_chipset_series")
public class GpuChipsetSerie extends GenericAttribute {
    public GpuChipsetSerie() {}
}
