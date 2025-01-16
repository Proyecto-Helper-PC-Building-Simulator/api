package es.bit.api.rest.dto.components;

import com.fasterxml.jackson.annotation.JsonInclude;
import es.bit.api.persistence.model.components.enums.StorageTypes;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StorageDTO extends ComponentDTO {
    private Integer size;
    private Integer transferSpeed;
    private StorageTypes storageTypes;
}
