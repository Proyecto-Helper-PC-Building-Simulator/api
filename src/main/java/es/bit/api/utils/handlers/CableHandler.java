package es.bit.api.utils.handlers;

import es.bit.api.persistence.model.components.Cable;
import es.bit.api.persistence.repository.jpa.components.ICableJpaRepository;
import es.bit.api.rest.dto.components.CableDTO;
import es.bit.api.rest.mapper.components.CableMapper;
import org.springframework.stereotype.Service;

@Service
public class CableHandler implements ComponentHandler<Cable, CableDTO> {
    private final ICableJpaRepository cableJpaRepository;


    public CableHandler(ICableJpaRepository cableJpaRepository) {
        this.cableJpaRepository = cableJpaRepository;
    }


    @Override
    public CableDTO handleComponent(Cable component) {
        Cable cable = cableJpaRepository.findById(component.getComponentId()).orElseThrow(() -> new IllegalArgumentException("Cable not found"));
        return CableMapper.toDTO(cable, true);
    }
}
