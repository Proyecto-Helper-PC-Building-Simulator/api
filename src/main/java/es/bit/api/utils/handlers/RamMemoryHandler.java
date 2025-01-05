package es.bit.api.utils.handlers;

import es.bit.api.persistence.model.components.RamMemory;
import es.bit.api.rest.dto.components.RamMemoryDTO;
import es.bit.api.rest.mapper.components.RamMemoryMapper;
import org.springframework.stereotype.Service;

@Service
public class RamMemoryHandler implements ComponentHandler<RamMemory, RamMemoryDTO> {

    @Override
    public RamMemoryDTO toDTO(RamMemory component) {
        return RamMemoryMapper.toDTO(component);
    }

    @Override
    public RamMemory toEntity(RamMemoryDTO componentDto) {
        return RamMemoryMapper.toBD(componentDto);
    }
}
