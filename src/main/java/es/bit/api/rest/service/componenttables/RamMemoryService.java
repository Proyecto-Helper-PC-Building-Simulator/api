package es.bit.api.rest.service.componenttables;

import es.bit.api.persistence.model.basictables.Lighting;
import es.bit.api.persistence.model.basictables.Manufacturer;
import es.bit.api.persistence.model.componenttables.RamMemory;
import es.bit.api.persistence.repository.jpa.componenttables.IRamMemoryJpaRepository;
import es.bit.api.rest.dto.basictables.LightingDTO;
import es.bit.api.rest.dto.basictables.ManufacturerDTO;
import es.bit.api.rest.dto.componenttables.RamMemoryDTO;
import es.bit.api.rest.mapper.componenttables.RamMemoryMapper;
import es.bit.api.rest.service.GenericService;
import jakarta.persistence.criteria.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class RamMemoryService implements GenericService<RamMemoryDTO, RamMemory, Integer> {
    private final IRamMemoryJpaRepository ramMemoryJPARepository;


    @Autowired
    public RamMemoryService(IRamMemoryJpaRepository ramMemoryJPARepository) {
        this.ramMemoryJPARepository = ramMemoryJPARepository;
    }


    @Override
    public Long count() {
        return this.ramMemoryJPARepository.count();
    }

    @Override
    public RamMemoryDTO findById(Integer id) {
        Optional<RamMemory> ramMemory = this.ramMemoryJPARepository.findById(id);

        if (ramMemory.isEmpty()) {
            return null;
        }

        return RamMemoryMapper.toDTO(ramMemory);
    }

    @Override
    @Cacheable(value = "ramMemories", key = "#page + '-' + #size + '-' + #sortBy + '-' + #sortDir + '-' + #filters")
    public List<RamMemoryDTO> findAll(int page, int size, String sortBy, String sortDir, Map<String, String> filters) {
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.fromString(sortDir), sortBy);
        Page<RamMemory> ramMemoryPage = this.ramMemoryJPARepository.findAll(getSpecification(filters), pageable);
        return RamMemoryMapper.toDTO(ramMemoryPage.getContent());
    }

    @Override
    public RamMemoryDTO create(RamMemoryDTO ramMemoryDTO) {
        RamMemory ramMemory = RamMemoryMapper.toBD(ramMemoryDTO);
        ramMemory = this.ramMemoryJPARepository.save(ramMemory);

        return RamMemoryMapper.toDTO(ramMemory);
    }

    @Override
    public void update(RamMemoryDTO ramMemoryDTO) {
        RamMemory ramMemory = RamMemoryMapper.toBD(ramMemoryDTO);
        this.ramMemoryJPARepository.save(ramMemory);

        RamMemoryMapper.toDTO(ramMemory);
    }

    @Override
    public void delete(RamMemoryDTO ramMemoryDTO) {
        RamMemory ramMemory = RamMemoryMapper.toBD(ramMemoryDTO);
        this.ramMemoryJPARepository.delete(ramMemory);
    }


    @Override
    public Specification<RamMemory> getSpecification(Map<String, String> filters) {
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
            if (filters.containsKey("frequencyMin")) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("frequency"), Integer.parseInt(filters.get("frequencyMin"))));
            }
            if (filters.containsKey("frequencyMax")) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("frequency"), Integer.parseInt(filters.get("frequencyMax"))));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    @Override
    public Map<String, Double> getPriceRange() {
        Double highestPrice = ramMemoryJPARepository.findAll()
                .stream()
                .mapToDouble(RamMemory::getPrice)
                .max()
                .orElse(0.0);

        Double lowestPrice = ramMemoryJPARepository.findAll()
                .stream()
                .mapToDouble(RamMemory::getPrice)
                .min()
                .orElse(0.0);

        Map<String, Double> priceRange = new HashMap<>();
        priceRange.put("highestPrice", highestPrice);
        priceRange.put("lowestPrice", lowestPrice);
        return priceRange;
    }

    @Override
    public Set<ManufacturerDTO> getManufacturers() {
        Set<Manufacturer> manufacturers = ramMemoryJPARepository.findAll()
                .stream()
                .map(RamMemory::getManufacturer)
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

    @Override
    public Set<LightingDTO> getLightings() {
        Set<Lighting> lightings = ramMemoryJPARepository.findAll()
                .stream()
                .map(RamMemory::getLighting)
                .collect(Collectors.toSet());

        return lightings.stream()
                .map(lighting -> {
                    LightingDTO dto = new LightingDTO();
                    dto.setId(lighting.getId());
                    dto.setName(lighting.getName());
                    return dto;
                })
                .collect(Collectors.toSet());
    }
}