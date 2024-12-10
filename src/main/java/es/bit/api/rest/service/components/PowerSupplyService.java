package es.bit.api.rest.service.components;

import es.bit.api.persistence.model.components.attributes.Lighting;
import es.bit.api.persistence.model.components.attributes.Manufacturer;
import es.bit.api.persistence.model.components.attributes.PowerSupplyFormFactor;
import es.bit.api.persistence.model.components.PowerSupply;
import es.bit.api.persistence.model.components.enums.PowerSupplyTypes;
import es.bit.api.persistence.repository.jpa.components.IPowerSupplyJpaRepository;
import es.bit.api.rest.dto.components.attributes.LightingDTO;
import es.bit.api.rest.dto.components.attributes.ManufacturerDTO;
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

import java.util.*;
import java.util.stream.Collectors;

@Service
public class PowerSupplyService implements GenericService<PowerSupplyDTO, PowerSupply, Integer> {
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

    @Override
    public Map<String, Double> getPriceRange() {
        Double highestPrice = powerSupplyJPARepository.findAll()
                .stream()
                .mapToDouble(PowerSupply::getPrice)
                .max()
                .orElse(0.0);

        Double lowestPrice = powerSupplyJPARepository.findAll()
                .stream()
                .mapToDouble(PowerSupply::getPrice)
                .min()
                .orElse(0.0);

        Map<String, Double> priceRange = new HashMap<>();
        priceRange.put("highestPrice", highestPrice);
        priceRange.put("lowestPrice", lowestPrice);
        return priceRange;
    }

    @Override
    public Set<ManufacturerDTO> getManufacturers() {
        Set<Manufacturer> manufacturers = powerSupplyJPARepository.findAll()
                .stream()
                .map(PowerSupply::getManufacturer)
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
        Set<Lighting> lightings = powerSupplyJPARepository.findAll()
                .stream()
                .map(PowerSupply::getLighting)
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