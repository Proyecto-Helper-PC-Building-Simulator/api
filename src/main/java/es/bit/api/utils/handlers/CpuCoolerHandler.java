package es.bit.api.utils.handlers;

import es.bit.api.persistence.model.components.CpuCooler;
import es.bit.api.rest.dto.components.CpuCoolerDTO;
import es.bit.api.rest.mapper.components.CpuCoolerMapper;
import org.springframework.stereotype.Service;

@Service
public class CpuCoolerHandler implements ComponentHandler<CpuCooler, CpuCoolerDTO> {

    @Override
    public CpuCoolerDTO toDTO(CpuCooler component) {
        return CpuCoolerMapper.toDTO(component, true);
    }

    @Override
    public CpuCooler toEntity(CpuCoolerDTO componentDto) {
        return CpuCoolerMapper.toBD(componentDto, true);
    }
}
