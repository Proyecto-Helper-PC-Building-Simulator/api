package es.bit.api.persistence.model.components.attributes;

import es.bit.api.persistence.model.components.Motherboard;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "multi_gpu_types")
public class MultiGpuType extends GenericAttribute {
    @ManyToMany(mappedBy = "multiGpuTypes")
    private List<Motherboard> motherboards = new ArrayList<>();
}
