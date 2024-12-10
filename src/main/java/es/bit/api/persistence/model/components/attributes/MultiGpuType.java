package es.bit.api.persistence.model.components.attributes;

import es.bit.api.persistence.model.components.Motherboard;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "multi_gpu_types")
public class MultiGpuType extends GenericAttribute {
    @ManyToMany(mappedBy = "multiGpuTypes")
    private List<Motherboard> motherboards = new ArrayList<>();


    public MultiGpuType() {}


    public List<Motherboard> getMotherboards() {
        return motherboards;
    }

    public void setMotherboards(List<Motherboard> motherboards) {
        this.motherboards = motherboards;
    }
}
