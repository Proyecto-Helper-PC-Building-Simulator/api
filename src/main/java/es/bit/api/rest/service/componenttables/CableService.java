package es.bit.api.rest.service.componenttables;

import es.bit.api.persistence.model.basictables.CableColor;
import es.bit.api.persistence.model.basictables.CableType;
import es.bit.api.persistence.model.basictables.Lighting;
import es.bit.api.persistence.model.basictables.Manufacturer;
import es.bit.api.persistence.model.componenttables.Cable;
import es.bit.api.persistence.repository.jpa.componenttables.ICableJpaRepository;
import es.bit.api.rest.dto.basictables.LightingDTO;
import es.bit.api.rest.dto.basictables.ManufacturerDTO;
import es.bit.api.rest.dto.componenttables.CableDTO;
import es.bit.api.rest.mapper.componenttables.CableMapper;
import es.bit.api.rest.service.GenericService;
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
public class CableService implements GenericService<CableDTO, Cable, Integer> {
    private final ICableJpaRepository cableJPARepository;


    @Autowired
    public CableService(ICableJpaRepository cableJPARepository) {
        this.cableJPARepository = cableJPARepository;
    }


    @Override
    public Long count() {
        return this.cableJPARepository.count();
    }

    @Override
    public CableDTO findById(Integer id) {
        Optional<Cable> cable = this.cableJPARepository.findById(id);

        if (cable.isEmpty()) {
            return null;
        }

        return CableMapper.toDTO(cable, true);
    }

    @Override
    @Cacheable(value = "cables", key = "#page + '-' + #size + '-' + #sortBy + '-' + #sortDir + '-' + #filters")
    public List<CableDTO> findAll(int page, int size, String sortBy, String sortDir, Map<String, String> filters) {
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.fromString(sortDir), sortBy);
        Page<Cable> cablePage = this.cableJPARepository.findAll(getSpecification(filters), pageable);
        return CableMapper.toDTO(cablePage.getContent(), true);
    }

    @Override
    public CableDTO create(CableDTO cableDTO) {
        Cable cable = CableMapper.toBD(cableDTO, true);
        cable = this.cableJPARepository.save(cable);

        return CableMapper.toDTO(cable, true);
    }

    @Override
    public void update(CableDTO cableDTO) {
        Cable cable = CableMapper.toBD(cableDTO, true);
        this.cableJPARepository.save(cable);
    }

    @Override
    public void delete(CableDTO cableDTO) {
        Cable cable = CableMapper.toBD(cableDTO, false);
        this.cableJPARepository.delete(cable);
    }


    @Override
    public Specification<Cable> getSpecification(Map<String, String> filters) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            Predicate commonPredicates = addCommonPredicates(criteriaBuilder, root, filters);
            if (commonPredicates != null) {
                predicates.add(commonPredicates);
            }

            if (filters.containsKey("type")) {
                Join<Cable, CableType> cpuSerieJoin = root.join("cableType", JoinType.INNER);
                predicates.add(criteriaBuilder.like(cpuSerieJoin.get("name"), "%" + filters.get("type") + "%"));
            }
            if (filters.containsKey("color")) {
                String[] colors = filters.get("color").split(",");
                for (String color : colors) {
                    Join<Cable, CableColor> cpuSocketJoin = root.joinList("cableColors", JoinType.INNER);
                    predicates.add(criteriaBuilder.like(cpuSocketJoin.get("name"), "%" + color.trim() + "%"));
                }
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    @Override
    public Map<String, Double> getPriceRange() {
        Double highestPrice = cableJPARepository.findAll()
                .stream()
                .mapToDouble(Cable::getPrice)
                .max()
                .orElse(0.0);

        Double lowestPrice = cableJPARepository.findAll()
                .stream()
                .mapToDouble(Cable::getPrice)
                .min()
                .orElse(0.0);

        Map<String, Double> priceRange = new HashMap<>();
        priceRange.put("highestPrice", highestPrice);
        priceRange.put("lowestPrice", lowestPrice);
        return priceRange;
    }

    @Override
    public Set<ManufacturerDTO> getManufacturers() {
        Set<Manufacturer> manufacturers = cableJPARepository.findAll()
                .stream()
                .map(Cable::getManufacturer)
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
        Set<Lighting> lightings = cableJPARepository.findAll()
                .stream()
                .map(Cable::getLighting)
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