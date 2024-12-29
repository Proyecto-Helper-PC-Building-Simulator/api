package es.bit.api.utils.handlers;

import es.bit.api.persistence.model.components.CaseFan;
import es.bit.api.persistence.repository.jpa.components.ICaseFanJpaRepository;
import es.bit.api.rest.dto.components.CaseFanDTO;
import es.bit.api.rest.mapper.components.CaseFanMapper;
import org.springframework.stereotype.Service;

@Service
public class CaseFanHandler implements ComponentHandler<CaseFan, CaseFanDTO> {
    private final ICaseFanJpaRepository caseFanJpaRepository;


    public CaseFanHandler(ICaseFanJpaRepository caseFanJpaRepository) {
        this.caseFanJpaRepository = caseFanJpaRepository;
    }


    @Override
    public CaseFanDTO handleComponent(CaseFan component) {
        CaseFan caseFan = caseFanJpaRepository.findById(component.getComponentId()).orElseThrow(() -> new IllegalArgumentException("Case Fan not found"));
        return CaseFanMapper.toDTO(caseFan);
    }
}
