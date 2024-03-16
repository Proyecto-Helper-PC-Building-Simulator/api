package es.bit.api.rest.service.componenttables;

import es.bit.api.persistence.model.componenttables.Component;
import es.bit.api.persistence.repository.jpa.componenttables.IComponentCustomJPARepository;
import es.bit.api.persistence.repository.jpa.componenttables.IComponentJPARepository;
import es.bit.api.rest.dto.componenttables.ComponentDTO;
import es.bit.api.rest.mapper.componenttables.ComponentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.lang.invoke.CallSite;
import java.util.List;
import java.util.Optional;

@Service
public class ComponentService {
    @Autowired
    IComponentJPARepository componentJPARepository;

    @Autowired
    IComponentCustomJPARepository componentCustomJPARepository;

    public Long count() {
        return this.componentJPARepository.count();
    }

    public List<ComponentDTO> findAll(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Component> componentPage = this.componentJPARepository.findAll(pageRequest);

        return ComponentMapper.toDTO(componentPage.getContent());
    }

    public List<ComponentDTO> findAllByComponentType(String componentTypeName, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Component> componentPage = this.componentJPARepository.findAllByComponentType(componentTypeName, pageRequest);

        return ComponentMapper.toDTO(componentPage.getContent());
    }

    public List<ComponentDTO> findAllByName(String name) {
        List<Component> components = this.componentCustomJPARepository.findAll(name);

        return ComponentMapper.toDTO(components);
    }

    public ComponentDTO findById(Integer id) {
        Optional<Component> component = this.componentJPARepository.findById(id);

        if (component.isEmpty()) {
            return null;
        }

        return ComponentMapper.toDTO(component);
    }



    public ComponentDTO create(ComponentDTO componentDTO) {
        Component component = ComponentMapper.toBD(componentDTO);
        component = this.componentJPARepository.save(component);

        return ComponentMapper.toDTO(component);
    }

    public void update(ComponentDTO componentDTO) {
        Component component = ComponentMapper.toBD(componentDTO);
        this.componentJPARepository.save(component);

        ComponentMapper.toDTO(component);
    }

    public void delete(ComponentDTO componentDTO) {
        Component component = ComponentMapper.toBD(componentDTO);
        this.componentJPARepository.delete(component);
    }
}