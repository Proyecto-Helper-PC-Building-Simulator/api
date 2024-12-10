package es.bit.api.rest.service.components;

import es.bit.api.persistence.model.components.PowerSupply;
import es.bit.api.persistence.model.components.attributes.PowerSupplyFormFactor;
import es.bit.api.persistence.model.components.enums.PowerSupplyTypes;
import es.bit.api.persistence.repository.jpa.components.IPowerSupplyJpaRepository;
import es.bit.api.rest.dto.components.PowerSupplyDTO;
import es.bit.api.rest.mapper.components.PowerSupplyMapper;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
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
public class PowerSupplyService extends GenericService<PowerSupplyDTO, PowerSupply, Integer> {
    private final IPowerSupplyJpaRepository powerSupplyJPARepository;


    @Autowired
    public PowerSupplyService(IPowerSupplyJpaRepository powerSupplyJPARepository) {
        this.powerSupplyJPARepository = powerSupplyJPARepository;
    }


    @Override
    public Long count() {
        return this.powerSupplyJPARepository.count();
    }

    @Override
    public Long countFiltered(Map<String, String> filters) {
        return this.powerSupplyJPARepository.count(getSpecification(filters));
    }

    @Override
    public PowerSupplyDTO findById(Integer id) {
        Optional<PowerSupply> powerSupply = this.powerSupplyJPARepository.findById(id);

        if (powerSupply.isEmpty()) {
            return null;
        }

        return PowerSupplyMapper.toDTO(powerSupply);
    }

    @Override
    @Cacheable(value = "powerSupplies", key = "#page + '-' + #size + '-' + #sortBy + '-' + #sortDir + '-' + #filters")
    public List<PowerSupplyDTO> findAll(int page, int size, String sortBy, String sortDir, Map<String, String> filters) {
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.fromString(sortDir), sortBy);
        Page<PowerSupply> powerSupplyPage = this.powerSupplyJPARepository.findAll(getSpecification(filters), pageable);
        return PowerSupplyMapper.toDTO(powerSupplyPage.getContent());
    }

    @Override
    public PowerSupplyDTO create(PowerSupplyDTO powerSupplyDTO) {
        PowerSupply powerSupply = PowerSupplyMapper.toBD(powerSupplyDTO);
        powerSupply = this.powerSupplyJPARepository.save(powerSupply);

        return PowerSupplyMapper.toDTO(powerSupply);
    }

    @Override
    public void update(PowerSupplyDTO powerSupplyDTO) {
        PowerSupply powerSupply = PowerSupplyMapper.toBD(powerSupplyDTO);
        this.powerSupplyJPARepository.save(powerSupply);

        PowerSupplyMapper.toDTO(powerSupply);
    }

    @Override
    public void delete(PowerSupplyDTO powerSupplyDTO) {
        PowerSupply powerSupply = PowerSupplyMapper.toBD(powerSupplyDTO);
        this.powerSupplyJPARepository.delete(powerSupply);
    }


    @Override
    public Specification<PowerSupply> getSpecification(Map<String, String> filters) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            Predicate commonPredicates = addCommonPredicates(criteriaBuilder, root, filters);
            if (commonPredicates != null) {
                predicates.add(commonPredicates);
            }

            if (filters.containsKey("wattageMin")) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("wattage"), Integer.parseInt(filters.get("wattageMin"))));
            }
            if (filters.containsKey("wattageMax")) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("wattage"), Integer.parseInt(filters.get("wattageMax"))));
            }
            if (filters.containsKey("lengthMin")) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("length"), Integer.parseInt(filters.get("lengthMin"))));
            }
            if (filters.containsKey("lengthMax")) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("length"), Integer.parseInt(filters.get("lengthMax"))));
            }
            if (filters.containsKey("type")) {
                String typeName = filters.get("type");
                predicates.add(criteriaBuilder.equal(root.get("type"), PowerSupplyTypes.valueOf(typeName)));
            }
            if (filters.containsKey("formFactor")) {
                Join<PowerSupply, PowerSupplyFormFactor> powerSupplyFormFactor = root.join("powerSupplyFormFactor", JoinType.INNER);
                predicates.add(criteriaBuilder.like(powerSupplyFormFactor.get("name"), "%" + filters.get("formFactor") + "%"));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}