package es.bit.api.utils.handlers;

import es.bit.api.persistence.model.components.Cable;
import es.bit.api.rest.dto.components.CableDTO;
import es.bit.api.rest.mapper.components.CableMapper;
import org.springframework.stereotype.Service;

@Service
public class CableHandler implements ComponentHandler<Cable, CableDTO> {

    @Override
    public CableDTO toDTO(Cable component) {
        return CableMapper.toDTO(component, true);
    }

    @Override
    public Cable toEntity(CableDTO componentDto) {
        return CableMapper.toBD(componentDto, true);
    }
}
