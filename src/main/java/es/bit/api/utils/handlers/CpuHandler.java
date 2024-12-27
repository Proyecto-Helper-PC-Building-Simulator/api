package es.bit.api.utils.handlers;

import es.bit.api.persistence.model.components.Component;
import es.bit.api.persistence.model.components.Cpu;
import es.bit.api.persistence.repository.jpa.components.ICpuJpaRepository;
import es.bit.api.rest.dto.components.ComponentDTO;
import es.bit.api.rest.mapper.components.CpuMapper;
import org.springframework.stereotype.Service;

@Service
public class CpuHandler implements ComponentHandler {
    private final ICpuJpaRepository cpuJpaRepository;


    public CpuHandler(ICpuJpaRepository cpuJpaRepository) {
        this.cpuJpaRepository = cpuJpaRepository;
    }


    @Override
    public ComponentDTO handleComponent(Component component) {
        Cpu cpu = cpuJpaRepository.findById(component.getComponentId()).orElseThrow(() -> new IllegalArgumentException("CPU not found"));
        return CpuMapper.toDTO(cpu);
    }
}
