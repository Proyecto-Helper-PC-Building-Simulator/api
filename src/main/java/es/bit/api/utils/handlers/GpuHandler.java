package es.bit.api.utils.handlers;

import es.bit.api.persistence.model.components.Gpu;
import es.bit.api.rest.dto.components.GpuDTO;
import es.bit.api.rest.mapper.components.GpuMapper;
import org.springframework.stereotype.Service;

@Service
public class GpuHandler implements ComponentHandler<Gpu, GpuDTO> {

    @Override
    public GpuDTO toDTO(Gpu component) {
        return GpuMapper.toDTO(component);
    }

    @Override
    public Gpu toEntity(GpuDTO componentDto) {
        return GpuMapper.toBD(componentDto);
    }
}
