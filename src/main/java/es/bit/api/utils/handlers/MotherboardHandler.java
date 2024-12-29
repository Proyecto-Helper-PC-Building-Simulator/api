package es.bit.api.utils.handlers;

import es.bit.api.persistence.model.components.Motherboard;
import es.bit.api.persistence.repository.jpa.components.IMotherboardJpaRepository;
import es.bit.api.rest.dto.components.MotherboardDTO;
import es.bit.api.rest.mapper.components.MotherboardMapper;
import org.springframework.stereotype.Service;

@Service
public class MotherboardHandler implements ComponentHandler<Motherboard, MotherboardDTO> {
    private final IMotherboardJpaRepository motherboardJpaRepository;


    public MotherboardHandler(IMotherboardJpaRepository motherboardJpaRepository) {
        this.motherboardJpaRepository = motherboardJpaRepository;
    }


    @Override
    public MotherboardDTO handleComponent(Motherboard component) {
        Motherboard motherboard = motherboardJpaRepository.findById(component.getComponentId()).orElseThrow(() -> new IllegalArgumentException("Motherboard not found"));
        return MotherboardMapper.toDTO(motherboard, true);
    }
}
