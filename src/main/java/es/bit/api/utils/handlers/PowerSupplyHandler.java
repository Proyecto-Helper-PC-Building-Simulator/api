package es.bit.api.utils.handlers;

import es.bit.api.persistence.model.components.Component;
import es.bit.api.persistence.model.components.PowerSupply;
import es.bit.api.persistence.repository.jpa.components.IPowerSupplyJpaRepository;
import es.bit.api.rest.dto.components.ComponentDTO;
import es.bit.api.rest.mapper.components.PowerSupplyMapper;
import org.springframework.stereotype.Service;

@Service
public class PowerSupplyHandler implements ComponentHandler {
    private final IPowerSupplyJpaRepository powerSupplyJpaRepository;


    public PowerSupplyHandler(IPowerSupplyJpaRepository powerSupplyJpaRepository) {
        this.powerSupplyJpaRepository = powerSupplyJpaRepository;
    }


    @Override
    public ComponentDTO handleComponent(Component component) {
        PowerSupply powerSupply = powerSupplyJpaRepository.findById(component.getComponentId()).orElseThrow(() -> new IllegalArgumentException("Power Supply not found"));
        return PowerSupplyMapper.toDTO(powerSupply);
    }
}
