package es.bit.api.utils.handlers;

import es.bit.api.persistence.model.components.Component;
import es.bit.api.persistence.model.components.RamMemory;
import es.bit.api.persistence.repository.jpa.components.IRamMemoryJpaRepository;
import es.bit.api.rest.dto.components.ComponentDTO;
import es.bit.api.rest.mapper.components.RamMemoryMapper;
import org.springframework.stereotype.Service;

@Service
public class RamHandler implements ComponentHandler {
    private final IRamMemoryJpaRepository ramMemoryJpaRepository;


    public RamHandler(IRamMemoryJpaRepository ramMemoryJpaRepository) {
        this.ramMemoryJpaRepository = ramMemoryJpaRepository;
    }


    @Override
    public ComponentDTO handleComponent(Component component) {
        RamMemory ramMemory = ramMemoryJpaRepository.findById(component.getComponentId()).orElseThrow(() -> new IllegalArgumentException("Ram Memory not found"));
        return RamMemoryMapper.toDTO(ramMemory);
    }
}
