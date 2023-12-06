package es.bit.api.rest.service;

import es.bit.api.persistence.model.ComponentType;
import es.bit.api.persistence.repository.jpa.IComponentTypeJPARepository;
import es.bit.api.rest.dto.ComponentTypeDTO;
import es.bit.api.rest.mapper.ComponentTypeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ComponentTypeService {
    @Autowired
    IComponentTypeJPARepository componentTypeJPARepository;

    public Long count() {
        return this.componentTypeJPARepository.count();
    }

    public List<ComponentTypeDTO> findAll(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<ComponentType> componentTypePage = this.componentTypeJPARepository.findAll(pageRequest);

        return ComponentTypeMapper.toDTO(componentTypePage.getContent());
    }

    public ComponentTypeDTO findById(Integer id) {
        Optional<ComponentType> componentType = this.componentTypeJPARepository.findById(id);

        if (componentType.isEmpty()) {
            return null;
        }

        return ComponentTypeMapper.toDTO(componentType);
    }

    public ComponentTypeDTO create(ComponentTypeDTO componentTypeDTO) {
        ComponentType componentType = ComponentTypeMapper.toBD(componentTypeDTO);
        componentType = this.componentTypeJPARepository.save(componentType);

        return ComponentTypeMapper.toDTO(componentType);
    }

    public void update(ComponentTypeDTO componentTypeDTO) {
        ComponentType componentType = ComponentTypeMapper.toBD(componentTypeDTO);
        this.componentTypeJPARepository.save(componentType);

        ComponentTypeMapper.toDTO(componentType);
    }

    public void delete(ComponentTypeDTO componentTypeDTO) {
        ComponentType componentType = ComponentTypeMapper.toBD(componentTypeDTO);
        this.componentTypeJPARepository.delete(componentType);
    }
}
