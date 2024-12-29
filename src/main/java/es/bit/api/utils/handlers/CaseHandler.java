package es.bit.api.utils.handlers;

import es.bit.api.persistence.model.components.Case;
import es.bit.api.persistence.repository.jpa.components.ICaseJpaRepository;
import es.bit.api.rest.dto.components.CaseDTO;
import es.bit.api.rest.mapper.components.CaseMapper;
import org.springframework.stereotype.Service;

@Service
public class CaseHandler implements ComponentHandler<Case, CaseDTO> {
    private final ICaseJpaRepository caseJpaRepository;


    public CaseHandler(ICaseJpaRepository caseJpaRepository) {
        this.caseJpaRepository = caseJpaRepository;
    }


    @Override
    public CaseDTO handleComponent(Case component) {
        Case caseObject = caseJpaRepository.findById(component.getComponentId()).orElseThrow(() -> new IllegalArgumentException("Case not found"));
        return CaseMapper.toDTO(caseObject, true, true);
    }
}
