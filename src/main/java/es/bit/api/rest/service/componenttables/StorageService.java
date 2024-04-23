package es.bit.api.rest.service.componenttables;

import es.bit.api.persistence.model.basictables.Manufacturer;
import es.bit.api.persistence.model.componenttables.Storage;
import es.bit.api.persistence.model.componenttables.enums.StorageTypes;
import es.bit.api.persistence.repository.jpa.componenttables.IStorageJpaRepository;
import es.bit.api.rest.dto.basictables.ManufacturerDTO;
import es.bit.api.rest.dto.componenttables.StorageDTO;
import es.bit.api.rest.mapper.componenttables.StorageMapper;
import es.bit.api.rest.service.GenericService;
import jakarta.persistence.criteria.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class StorageService implements GenericService<StorageDTO, Storage, Integer> {
    private final IStorageJpaRepository storageJPARepository;


    @Autowired
    public StorageService(IStorageJpaRepository storageJPARepository) {
        this.storageJPARepository = storageJPARepository;
    }


    @Override
    public Long count() {
        return this.storageJPARepository.count();
    }

    @Override
    public StorageDTO findById(Integer id) {
        Optional<Storage> storage = this.storageJPARepository.findById(id);

        if (storage.isEmpty()) {
            return null;
        }

        return StorageMapper.toDTO(storage);
    }

    @Override
    public List<StorageDTO> findAll(int page, int size, String sortBy, String sortDir, Map<String, String> filters) {
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.fromString(sortDir), sortBy);
        Page<Storage> storagePage = this.storageJPARepository.findAll(getSpecification(filters), pageable);
        return StorageMapper.toDTO(storagePage.getContent());
    }

    @Override
    public StorageDTO create(StorageDTO storageDTO) {
        Storage storage = StorageMapper.toBD(storageDTO);
        storage = this.storageJPARepository.save(storage);

        return StorageMapper.toDTO(storage);
    }

    @Override
    public void update(StorageDTO storageDTO) {
        Storage storage = StorageMapper.toBD(storageDTO);
        this.storageJPARepository.save(storage);

        StorageMapper.toDTO(storage);
    }

    @Override
    public void delete(StorageDTO storageDTO) {
        Storage storage = StorageMapper.toBD(storageDTO);
        this.storageJPARepository.delete(storage);
    }


    @Override
    public Specification<Storage> getSpecification(Map<String, String> filters) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            Predicate commonPredicates = addCommonPredicates(criteriaBuilder, root, filters);
            if (commonPredicates != null) {
                predicates.add(commonPredicates);
            }

            if (filters.containsKey("sizeMin")) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("size"), Integer.parseInt(filters.get("sizeMin"))));
            }
            if (filters.containsKey("sizeMax")) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("size"), Integer.parseInt(filters.get("sizeMax"))));
            }
            if (filters.containsKey("transferSpeedMin")) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("transferSpeed"), Integer.parseInt(filters.get("transferSpeedMin"))));
            }
            if (filters.containsKey("transferSpeedMax")) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("transferSpeed"), Integer.parseInt(filters.get("transferSpeedMax"))));
            }
            if (filters.containsKey("type")) {
                String typeName = filters.get("type");
                predicates.add(criteriaBuilder.equal(root.get("type"), StorageTypes.valueOf(typeName)));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    @Override
    public Map<String, Double> getPriceRange() {
        Double highestPrice = storageJPARepository.findAll()
                .stream()
                .mapToDouble(Storage::getPrice)
                .max()
                .orElse(0.0);

        Double lowestPrice = storageJPARepository.findAll()
                .stream()
                .mapToDouble(Storage::getPrice)
                .min()
                .orElse(0.0);

        Map<String, Double> priceRange = new HashMap<>();
        priceRange.put("highestPrice", highestPrice);
        priceRange.put("lowestPrice", lowestPrice);
        return priceRange;
    }

    @Override
    public Set<ManufacturerDTO> getManufacturers() {
        Set<Manufacturer> manufacturers = storageJPARepository.findAll()
                .stream()
                .map(Storage::getManufacturer)
                .collect(Collectors.toSet());

        return manufacturers.stream()
                .map(manufacturer -> {
                    ManufacturerDTO dto = new ManufacturerDTO();
                    dto.setId(manufacturer.getId());
                    dto.setName(manufacturer.getName());
                    return dto;
                })
                .collect(Collectors.toSet());
    }
}