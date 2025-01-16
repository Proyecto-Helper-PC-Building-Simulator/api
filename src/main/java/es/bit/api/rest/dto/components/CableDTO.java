package es.bit.api.rest.dto.components;

import com.fasterxml.jackson.annotation.JsonInclude;
import es.bit.api.rest.dto.components.attributes.CableColorDTO;
import es.bit.api.rest.dto.components.attributes.CableTypeDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CableDTO extends ComponentDTO {
    private CableTypeDTO cableType;
    private List<CableColorDTO> cableColors = new ArrayList<>();
}
