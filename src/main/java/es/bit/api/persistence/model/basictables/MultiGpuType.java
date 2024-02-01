package es.bit.api.persistence.model.basictables;

import com.fasterxml.jackson.annotation.JsonInclude;
import es.bit.api.persistence.model.componenttables.Motherboard;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "multi_gpu_types")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MultiGpuType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;

    @ManyToMany(mappedBy = "multiGpuTypes")
    private List<Motherboard> motherboards = new ArrayList<>();


    public MultiGpuType() {}


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Motherboard> getMotherboards() {
        return motherboards;
    }

    public void setMotherboards(List<Motherboard> motherboards) {
        this.motherboards = motherboards;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MultiGpuType that = (MultiGpuType) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
