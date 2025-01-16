package es.bit.api.rest.dto.components;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RamMemoryDTO extends ComponentDTO {
    private Integer size;
    private Integer frequency;
}
