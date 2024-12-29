package es.bit.api.utils.handlers;

import es.bit.api.persistence.model.components.Case;
import es.bit.api.rest.dto.components.CaseDTO;
import es.bit.api.rest.mapper.components.CaseMapper;
import org.springframework.stereotype.Service;

@Service
public class CaseHandler implements ComponentHandler<Case, CaseDTO> {

    @Override
    public CaseDTO toDTO(Case component) {
        return CaseMapper.toDTO(component, true, true);
    }

    @Override
    public Case toEntity(CaseDTO componentDto) {
        return CaseMapper.toBD(componentDto, true, true);
    }
}
