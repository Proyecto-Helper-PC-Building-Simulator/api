package es.bit.api.persistence.model.components;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "ram_memories")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RamMemory extends Component {
    private Integer size;
    private Integer frequency;
}
