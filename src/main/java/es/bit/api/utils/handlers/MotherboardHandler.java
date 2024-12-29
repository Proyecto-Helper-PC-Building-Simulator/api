package es.bit.api.utils.handlers;

import es.bit.api.persistence.model.components.Motherboard;
import es.bit.api.rest.dto.components.MotherboardDTO;
import es.bit.api.rest.mapper.components.MotherboardMapper;
import org.springframework.stereotype.Service;

@Service
public class MotherboardHandler implements ComponentHandler<Motherboard, MotherboardDTO> {

    @Override
    public MotherboardDTO toDTO(Motherboard component) {
        return MotherboardMapper.toDTO(component, true);
    }

    @Override
    public Motherboard toEntity(MotherboardDTO componentDto) {
        return MotherboardMapper.toBD(componentDto, true);
    }
}
