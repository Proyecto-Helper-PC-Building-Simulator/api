package es.bit.api.utils.handlers;

import es.bit.api.persistence.model.components.Component;
import es.bit.api.rest.dto.components.ComponentDTO;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ComponentHandlerFactory<C extends Component, D extends ComponentDTO> {
    private final Map<String, ComponentHandler<C, D>> handlers = new HashMap<>();


    public ComponentHandlerFactory(List<ComponentHandler<C, D>> handlerList) {
        for (ComponentHandler<C, D> handler : handlerList) {
            String handlerName = handler.getClass().getSimpleName().replace("Handler", "");
            handlers.put(handlerName.toUpperCase(), handler);
        }
    }


    public ComponentHandler<C, D> getHandler(String componentType) {
        ComponentHandler<C, D> handler = handlers.get(componentType.toUpperCase());

        if (handler == null) {
            throw new IllegalArgumentException("No handler found for type: " + componentType);
        }

        return handler;
    }
}
