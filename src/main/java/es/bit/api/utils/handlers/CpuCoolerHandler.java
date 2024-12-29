package es.bit.api.utils.handlers;

import es.bit.api.persistence.model.components.CpuCooler;
import es.bit.api.persistence.repository.jpa.components.ICpuCoolerJpaRepository;
import es.bit.api.rest.dto.components.CpuCoolerDTO;
import es.bit.api.rest.mapper.components.CpuCoolerMapper;
import org.springframework.stereotype.Service;

@Service
public class CpuCoolerHandler implements ComponentHandler<CpuCooler, CpuCoolerDTO> {
    private final ICpuCoolerJpaRepository cpuCoolerJpaRepository;


    public CpuCoolerHandler(ICpuCoolerJpaRepository cpuCoolerJpaRepository) {
        this.cpuCoolerJpaRepository = cpuCoolerJpaRepository;
    }


    @Override
    public CpuCoolerDTO handleComponent(CpuCooler component) {
        CpuCooler cpuCooler = cpuCoolerJpaRepository.findById(component.getComponentId()).orElseThrow(() -> new IllegalArgumentException("Cpu Cooler not found"));
        return CpuCoolerMapper.toDTO(cpuCooler, true);
    }
}
