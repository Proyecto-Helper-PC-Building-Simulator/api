package es.bit.api.utils.handlers;

import es.bit.api.persistence.model.components.Component;
import es.bit.api.persistence.model.components.Storage;
import es.bit.api.persistence.repository.jpa.components.IStorageJpaRepository;
import es.bit.api.rest.dto.components.ComponentDTO;
import es.bit.api.rest.mapper.components.StorageMapper;
import org.springframework.stereotype.Service;

@Service
public class StorageHandler implements ComponentHandler {
    private final IStorageJpaRepository storageJpaRepository;


    public StorageHandler(IStorageJpaRepository storageJpaRepository) {
        this.storageJpaRepository = storageJpaRepository;
    }


    @Override
    public ComponentDTO handleComponent(Component component) {
        Storage storage = storageJpaRepository.findById(component.getComponentId()).orElseThrow(() -> new IllegalArgumentException("Storage not found"));
        return StorageMapper.toDTO(storage);
    }
}
