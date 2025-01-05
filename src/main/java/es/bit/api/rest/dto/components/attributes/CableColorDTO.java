package es.bit.api.rest.dto.components.attributes;

import com.fasterxml.jackson.annotation.JsonInclude;
import es.bit.api.rest.dto.components.CableDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CableColorDTO extends GenericAttributeDTO {
    private List<CableDTO> cables = new ArrayList<>();
}
