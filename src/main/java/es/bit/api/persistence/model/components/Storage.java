package es.bit.api.persistence.model.components;

import com.fasterxml.jackson.annotation.JsonInclude;
import es.bit.api.persistence.model.components.enums.StorageTypes;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "storages")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Storage extends Component {
    private Integer size;
    private Integer transferSpeed;

    @Enumerated(EnumType.STRING)
    private StorageTypes type;
}
