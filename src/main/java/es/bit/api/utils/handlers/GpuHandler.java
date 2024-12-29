package es.bit.api.utils.handlers;

import es.bit.api.persistence.model.components.Gpu;
import es.bit.api.persistence.repository.jpa.components.IGpuJpaRepository;
import es.bit.api.rest.dto.components.GpuDTO;
import es.bit.api.rest.mapper.components.GpuMapper;
import org.springframework.stereotype.Service;

@Service
public class GpuHandler implements ComponentHandler<Gpu, GpuDTO> {
    private final IGpuJpaRepository gpuJpaRepository;


    public GpuHandler(IGpuJpaRepository gpuJpaRepository) {
        this.gpuJpaRepository = gpuJpaRepository;
    }


    @Override
    public GpuDTO handleComponent(Gpu component) {
        Gpu gpu = gpuJpaRepository.findById(component.getComponentId()).orElseThrow(() -> new IllegalArgumentException("GPU not found"));
        return GpuMapper.toDTO(gpu);
    }
}
