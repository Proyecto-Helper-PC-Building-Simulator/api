package es.bit.api.persistence.model.components;

import com.fasterxml.jackson.annotation.JsonInclude;
import es.bit.api.persistence.model.components.attributes.CableColor;
import es.bit.api.persistence.model.components.attributes.CableType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "cables")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Cable extends Component {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cable_type_id", referencedColumnName = "id")
    private CableType cableType;

    @ManyToMany
    @JoinTable(
            name = "cable_color_relations",
            joinColumns = @JoinColumn(name = "cable_id"),
            inverseJoinColumns = @JoinColumn(name = "cable_color_id")
    )
    private List<CableColor> cableColors = new ArrayList<>();
}