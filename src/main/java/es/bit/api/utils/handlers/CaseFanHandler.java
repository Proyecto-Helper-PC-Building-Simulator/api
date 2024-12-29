package es.bit.api.utils.handlers;

import es.bit.api.persistence.model.components.CaseFan;
import es.bit.api.rest.dto.components.CaseFanDTO;
import es.bit.api.rest.mapper.components.CaseFanMapper;
import org.springframework.stereotype.Service;

@Service
public class CaseFanHandler implements ComponentHandler<CaseFan, CaseFanDTO> {

    @Override
    public CaseFanDTO toDTO(CaseFan component) {
        return CaseFanMapper.toDTO(component);
    }

    @Override
    public CaseFan toEntity(CaseFanDTO componentDto) {
        return CaseFanMapper.toBD(componentDto);
    }
}
