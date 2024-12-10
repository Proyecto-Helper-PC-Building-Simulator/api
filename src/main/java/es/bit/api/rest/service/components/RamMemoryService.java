package es.bit.api.rest.service.components;

import es.bit.api.persistence.model.components.RamMemory;
import es.bit.api.persistence.repository.jpa.components.IRamMemoryJpaRepository;
import es.bit.api.rest.dto.components.RamMemoryDTO;
import es.bit.api.rest.mapper.components.RamMemoryMapper;
import jakarta.persistence.criteria.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class RamMemoryService extends GenericService<RamMemoryDTO, RamMemory, Integer> {
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
    public Long countFiltered(Map<String, String> filters) {
        return this.ramMemoryJPARepository.count(getSpecification(filters));
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
}