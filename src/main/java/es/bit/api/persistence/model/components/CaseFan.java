package es.bit.api.persistence.model.components;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "case_fans")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CaseFan extends Component {
    private Float airFlow;
    private Integer size;
}
