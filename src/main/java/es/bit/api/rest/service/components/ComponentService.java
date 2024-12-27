package es.bit.api.rest.service.components;

import es.bit.api.persistence.model.components.Component;
import es.bit.api.persistence.repository.jpa.components.IComponentJpaRepository;
import es.bit.api.rest.dto.components.ComponentDTO;
import es.bit.api.rest.mapper.components.ComponentMapper;
import es.bit.api.utils.handlers.ComponentHandler;
import es.bit.api.utils.handlers.ComponentHandlerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ComponentService extends GenericService<ComponentDTO, Component, Integer> {
    private final IComponentJpaRepository componentJPARepository;
    private final ComponentHandlerFactory handlerFactory;


    @Autowired
    public ComponentService(IComponentJpaRepository componentJPARepository, ComponentHandlerFactory handlerFactory) {
        this.componentJPARepository = componentJPARepository;
        this.handlerFactory = handlerFactory;
    }


    @Override
    public Long count() {
        return this.componentJPARepository.count();
    }

    @Override
    public Long countFiltered(Map<String, String> filters) {
        return this.componentJPARepository.count(getSpecification(filters));
    }

    @Override
    public ComponentDTO findById(Integer id) {
        Optional<Component> component = this.componentJPARepository.findById(id);

        if (component.isEmpty()) {
            return null;
        }

        return ComponentMapper.toDTO(component);
    }

    public List<ComponentDTO> findComponentsByIds(List<Integer> ids) {
        List<Component> components = componentJPARepository.findAllById(ids);
        List<ComponentDTO> result = new ArrayList<>();

        for (Component component : components) {
            ComponentHandler handler = handlerFactory.getHandler(component.getComponentType().getNameIdentifier());
            result.add(handler.handleComponent(component));
        }

        return result;
    }

    @Cacheable("components")
    @Override
    public List<ComponentDTO> findAll(int page, int size, String sortBy, String sortDir, Map<String, String> filters) {
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.fromString(sortDir), sortBy);
        Page<Component> cpuPage = this.componentJPARepository.findAll(getSpecification(filters), pageable);
        return ComponentMapper.toDTO(cpuPage.getContent());
    }

    @Override
    public ComponentDTO create(ComponentDTO componentDTO) {
        Component component = ComponentMapper.toBD(componentDTO);
        component = this.componentJPARepository.save(component);

        return ComponentMapper.toDTO(component);
    }

    @Override
    public void update(ComponentDTO componentDTO) {
        Component component = ComponentMapper.toBD(componentDTO);
        this.componentJPARepository.save(component);

        ComponentMapper.toDTO(component);
    }

    @Override
    public void delete(ComponentDTO componentDTO) {
        Component component = ComponentMapper.toBD(componentDTO);
        this.componentJPARepository.delete(component);
    }
}