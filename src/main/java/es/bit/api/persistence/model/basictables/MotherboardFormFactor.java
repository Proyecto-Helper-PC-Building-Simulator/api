package es.bit.api.persistence.model.basictables;

import com.fasterxml.jackson.annotation.JsonInclude;
import es.bit.api.persistence.model.componenttables.Case;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "motherboard_form_factors")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MotherboardFormFactor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;

    @ManyToMany(mappedBy = "motherboardFormFactors")
    private List<Case> cases = new ArrayList<>();


    public MotherboardFormFactor() {}


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

    public List<Case> getCases() {
        return cases;
    }

    public void setCases(List<Case> cases) {
        this.cases = cases;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MotherboardFormFactor that = (MotherboardFormFactor) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
