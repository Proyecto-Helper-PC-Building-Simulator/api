package es.bit.api.utils.handlers;

import es.bit.api.persistence.model.components.PowerSupply;
import es.bit.api.persistence.repository.jpa.components.IPowerSupplyJpaRepository;
import es.bit.api.rest.dto.components.PowerSupplyDTO;
import es.bit.api.rest.mapper.components.PowerSupplyMapper;
import org.springframework.stereotype.Service;

@Service
public class PowerSupplyHandler implements ComponentHandler<PowerSupply, PowerSupplyDTO> {
    private final IPowerSupplyJpaRepository powerSupplyJpaRepository;


    public PowerSupplyHandler(IPowerSupplyJpaRepository powerSupplyJpaRepository) {
        this.powerSupplyJpaRepository = powerSupplyJpaRepository;
    }


    @Override
    public PowerSupplyDTO handleComponent(PowerSupply component) {
        PowerSupply powerSupply = powerSupplyJpaRepository.findById(component.getComponentId()).orElseThrow(() -> new IllegalArgumentException("Power Supply not found"));
        return PowerSupplyMapper.toDTO(powerSupply);
    }
}
