package es.bit.api.utils.handlers;

import es.bit.api.persistence.model.components.Cpu;
import es.bit.api.rest.dto.components.CpuDTO;
import es.bit.api.rest.mapper.components.CpuMapper;
import org.springframework.stereotype.Service;

@Service
public class CpuHandler implements ComponentHandler<Cpu, CpuDTO> {

    @Override
    public CpuDTO toDTO(Cpu component) {
        return CpuMapper.toDTO(component);
    }

    @Override
    public Cpu toEntity(CpuDTO componentDto) {
        return CpuMapper.toBD(componentDto);
    }
}
