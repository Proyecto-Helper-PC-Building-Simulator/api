package es.bit.api.rest.service.components;

import es.bit.api.persistence.model.components.Component;
import es.bit.api.persistence.repository.jpa.IGenericJpaRepository;
import es.bit.api.rest.dto.components.ComponentDTO;
import es.bit.api.utils.handlers.ComponentHandler;
import es.bit.api.utils.handlers.ComponentHandlerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ComponentService extends GenericService<ComponentDTO, Component, Integer> {
    @Autowired
    public ComponentService(ComponentHandlerFactory<Component, ComponentDTO> handlerFactory, IGenericJpaRepository<Component, Integer> repository) {
        super(handlerFactory, repository);
    }


    public List<ComponentDTO> findComponentsByIds(List<Integer> ids) {
        List<Component> components = this.repository.findAllById(ids);
        List<ComponentDTO> result = new ArrayList<>();

        for (Component component : components) {
            ComponentHandler<Component, ComponentDTO> handler = handlerFactory.getHandler(component.getComponentType().getNameIdentifier());
            result.add(handler.toDTO(component));
        }

        return result;
    }
}