package es.bit.api.utils.handlers;

import es.bit.api.persistence.model.components.Storage;
import es.bit.api.rest.dto.components.StorageDTO;
import es.bit.api.rest.mapper.components.StorageMapper;
import org.springframework.stereotype.Service;

@Service
public class StorageHandler implements ComponentHandler<Storage, StorageDTO> {

    @Override
    public StorageDTO toDTO(Storage component) {
        return StorageMapper.toDTO(component);
    }

    @Override
    public Storage toEntity(StorageDTO componentDto) {
        return StorageMapper.toBD(componentDto);
    }
}
