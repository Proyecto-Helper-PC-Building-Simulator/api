package es.bit.api.rest.mapper.componenttables;

import es.bit.api.persistence.model.componenttables.Storage;
import es.bit.api.rest.dto.componenttables.StorageDTO;
import es.bit.api.rest.mapper.basictables.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class StorageMapper {
    public static StorageDTO toDTO(Storage storage) {
        StorageDTO storageDTO = new StorageDTO();
        storageDTO.setComponentId(storage.getComponentId());
        storageDTO.setName(storage.getName());
        storageDTO.setPrice(storage.getPrice());
        storageDTO.setManufacturerDTO(ManufacturerMapper.toDTO(storage.getManufacturer()));
        storageDTO.setLightingDTO(LightingMapper.toDTO(storage.getLighting()));
        storageDTO.setComponentTypeDTO(ComponentTypeMapper.toDTO(storage.getComponentType()));

        storageDTO.setSize(storage.getSize());
        storageDTO.setTransferSpeed(storage.getTransferSpeed());
        storageDTO.setStorageTypes(storage.getType());

        return storageDTO;
    }

    public static StorageDTO toDTO(Optional<Storage> storageOptional) {
        return storageOptional.map(StorageMapper::toDTO).orElse(null);
    }

    public static List<StorageDTO> toDTO(List<Storage> storages) {
        List<StorageDTO> storagesDTO = new ArrayList<>();

        if (storages == null)
            return storagesDTO;

        for (Storage storage : storages) {
            storagesDTO.add(StorageMapper.toDTO(storage));
        }

        return storagesDTO;
    }

    public static Storage toBD(StorageDTO storageDTO) {
        Storage storage = new Storage();
        storage.setComponentId(storageDTO.getComponentId());
        storage.setName(storageDTO.getName());
        storage.setPrice(storageDTO.getPrice());
        storage.setManufacturer(ManufacturerMapper.toBD(storageDTO.getManufacturerDTO()));
        storage.setLighting(LightingMapper.toBD(storageDTO.getLightingDTO()));
        storage.setComponentType(ComponentTypeMapper.toBD(storageDTO.getComponentTypeDTO()));

        storage.setSize(storageDTO.getSize());
        storage.setTransferSpeed(storageDTO.getTransferSpeed());
        storage.setType(storageDTO.getStorageTypes());

        return storage;
    }
}
