package es.bit.api.persistence.model.components.attributes;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;

import java.util.Objects;

@MappedSuperclass
@JsonInclude(JsonInclude.Include.NON_NULL)
public abstract class GenericAttribute {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    String name;


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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GenericAttribute that = (GenericAttribute) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
