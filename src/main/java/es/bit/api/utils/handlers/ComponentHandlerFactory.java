package es.bit.api.utils.handlers;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ComponentHandlerFactory {
    private final Map<String, ComponentHandler> handlers = new HashMap<>();


    public ComponentHandlerFactory(List<ComponentHandler> handlerList) {
        for (ComponentHandler handler : handlerList) {
            String handlerName = handler.getClass().getSimpleName().replace("Handler", "");
            handlers.put(handlerName.toUpperCase(), handler);
        }
    }


    public ComponentHandler getHandler(String componentType) {
        ComponentHandler handler = handlers.get(componentType.toUpperCase());

        if (handler == null) {
            throw new IllegalArgumentException("No handler found for type: " + componentType);
        }

        return handler;
    }
}
