package es.bit.api.utils.handlers;

import es.bit.api.persistence.model.components.PowerSupply;
import es.bit.api.rest.dto.components.PowerSupplyDTO;
import es.bit.api.rest.mapper.components.PowerSupplyMapper;
import org.springframework.stereotype.Service;

@Service
public class PowerSupplyHandler implements ComponentHandler<PowerSupply, PowerSupplyDTO> {

    @Override
    public PowerSupplyDTO toDTO(PowerSupply component) {
        return PowerSupplyMapper.toDTO(component);
    }

    @Override
    public PowerSupply toEntity(PowerSupplyDTO componentDto) {
        return PowerSupplyMapper.toBD(componentDto);
    }
}
