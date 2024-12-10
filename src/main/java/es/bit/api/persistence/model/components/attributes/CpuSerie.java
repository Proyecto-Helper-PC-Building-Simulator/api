package es.bit.api.persistence.model.components.attributes;

import jakarta.persistence.*;

@Entity
@Table(name = "cpu_series")
public class CpuSerie extends GenericAttribute {
    public CpuSerie() {}
}
