package es.bit.api.persistence.model.components.attributes;

import es.bit.api.persistence.model.components.Cable;
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
@Table(name = "cable_colors")
public class CableColor extends GenericAttribute {
    @ManyToMany(mappedBy = "cableColors")
    private List<Cable> cables = new ArrayList<>();
}
