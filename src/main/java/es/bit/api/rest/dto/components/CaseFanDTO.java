package es.bit.api.rest.dto.components;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CaseFanDTO extends ComponentDTO {
    private Float airFlow;
    private Integer size;
}
