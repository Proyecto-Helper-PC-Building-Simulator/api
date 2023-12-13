package es.bit.api.rest.service.componenttables;

import es.bit.api.persistence.model.componenttables.Storage;
import es.bit.api.persistence.repository.jpa.componenttables.IStorageJPARepository;
import es.bit.api.rest.dto.componenttables.StorageDTO;
import es.bit.api.rest.mapper.componenttables.StorageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StorageService {
    @Autowired
    IStorageJPARepository storageJPARepository;

    public Long count() {
        return this.storageJPARepository.count();
    }

    public List<StorageDTO> findAll(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Storage> storagePage = this.storageJPARepository.findAll(pageRequest);

        return StorageMapper.toDTO(storagePage.getContent());
    }

    public StorageDTO findById(Integer id) {
        Optional<Storage> storage = this.storageJPARepository.findById(id);

        if (storage.isEmpty()) {
            return null;
        }

        return StorageMapper.toDTO(storage);
    }



    public StorageDTO create(StorageDTO storageDTO) {
        Storage storage = StorageMapper.toBD(storageDTO);
        storage = this.storageJPARepository.save(storage);

        return StorageMapper.toDTO(storage);
    }

    public void update(StorageDTO storageDTO) {
        Storage storage = StorageMapper.toBD(storageDTO);
        this.storageJPARepository.save(storage);

        StorageMapper.toDTO(storage);
    }

    public void delete(StorageDTO storageDTO) {
        Storage storage = StorageMapper.toBD(storageDTO);
        this.storageJPARepository.delete(storage);
    }
}