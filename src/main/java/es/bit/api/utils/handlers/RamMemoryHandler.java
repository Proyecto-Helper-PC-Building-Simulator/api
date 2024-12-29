package es.bit.api.utils.handlers;

import es.bit.api.persistence.model.components.RamMemory;
import es.bit.api.persistence.repository.jpa.components.IRamMemoryJpaRepository;
import es.bit.api.rest.dto.components.RamMemoryDTO;
import es.bit.api.rest.mapper.components.RamMemoryMapper;
import org.springframework.stereotype.Service;

@Service
public class RamMemoryHandler implements ComponentHandler<RamMemory, RamMemoryDTO> {
    private final IRamMemoryJpaRepository ramMemoryJpaRepository;


    public RamMemoryHandler(IRamMemoryJpaRepository ramMemoryJpaRepository) {
        this.ramMemoryJpaRepository = ramMemoryJpaRepository;
    }


    @Override
    public RamMemoryDTO handleComponent(RamMemory component) {
        RamMemory ramMemory = ramMemoryJpaRepository.findById(component.getComponentId()).orElseThrow(() -> new IllegalArgumentException("Ram Memory not found"));
        return RamMemoryMapper.toDTO(ramMemory);
    }
}
