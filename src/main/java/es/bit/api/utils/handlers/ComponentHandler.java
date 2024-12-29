package es.bit.api.utils.handlers;

import es.bit.api.persistence.model.components.Component;
import es.bit.api.rest.dto.components.ComponentDTO;

public interface ComponentHandler<C extends Component, D extends ComponentDTO> {
    D handleComponent(C component);
}
