package es.bit.api.rest.service.componenttables;

import es.bit.api.persistence.model.basictables.Lighting;
import es.bit.api.persistence.model.basictables.Manufacturer;
import es.bit.api.persistence.model.componenttables.CaseFan;
import es.bit.api.persistence.repository.jpa.componenttables.ICaseFanJpaRepository;
import es.bit.api.rest.dto.basictables.LightingDTO;
import es.bit.api.rest.dto.basictables.ManufacturerDTO;
import es.bit.api.rest.dto.componenttables.CaseFanDTO;
import es.bit.api.rest.mapper.componenttables.CaseFanMapper;
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
public class CaseFanService implements GenericService<CaseFanDTO, CaseFan, Integer> {
    private final ICaseFanJpaRepository caseFanJPARepository;


    @Autowired
    public CaseFanService(ICaseFanJpaRepository caseFanJPARepository) {
        this.caseFanJPARepository = caseFanJPARepository;
    }


    @Override
    public Long count() {
        return this.caseFanJPARepository.count();
    }

    @Override
    public CaseFanDTO findById(Integer id) {
        Optional<CaseFan> caseFan = this.caseFanJPARepository.findById(id);

        if (caseFan.isEmpty()) {
            return null;
        }

        return CaseFanMapper.toDTO(caseFan);
    }

    @Override
    public List<CaseFanDTO> findAll(int page, int size, String sortBy, String sortDir, Map<String, String> filters) {
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.fromString(sortDir), sortBy);
        Page<CaseFan> cpuPage = this.caseFanJPARepository.findAll(getSpecification(filters), pageable);
        return CaseFanMapper.toDTO(cpuPage.getContent());
    }

    @Override
    public CaseFanDTO create(CaseFanDTO caseFanDTO) {
        CaseFan caseFan = CaseFanMapper.toBD(caseFanDTO);
        caseFan = this.caseFanJPARepository.save(caseFan);

        return CaseFanMapper.toDTO(caseFan);
    }

    @Override
    public void update(CaseFanDTO caseFanDTO) {
        CaseFan caseFan = CaseFanMapper.toBD(caseFanDTO);
        this.caseFanJPARepository.save(caseFan);

        CaseFanMapper.toDTO(caseFan);
    }

    @Override
    public void delete(CaseFanDTO caseFanDTO) {
        CaseFan caseFan = CaseFanMapper.toBD(caseFanDTO);
        this.caseFanJPARepository.delete(caseFan);
    }


    @Override
    public Specification<CaseFan> getSpecification(Map<String, String> filters) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            Predicate commonPredicates = addCommonPredicates(criteriaBuilder, root, filters);
            if (commonPredicates != null) {
                predicates.add(commonPredicates);
            }

            if (filters.containsKey("airFlowMin")) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("airFlow"), Float.parseFloat(filters.get("airFlowMin"))));
            }
            if (filters.containsKey("airFlowMax")) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("airFlow"), Float.parseFloat(filters.get("airFlowMax"))));
            }
            if (filters.containsKey("sizeMin")) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("size"), Integer.parseInt(filters.get("sizeMin"))));
            }
            if (filters.containsKey("sizeMax")) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("size"), Integer.parseInt(filters.get("sizeMax"))));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    @Override
    public Map<String, Double> getPriceRange() {
        Double highestPrice = caseFanJPARepository.findAll()
                .stream()
                .mapToDouble(CaseFan::getPrice)
                .max()
                .orElse(0.0);

        Double lowestPrice = caseFanJPARepository.findAll()
                .stream()
                .mapToDouble(CaseFan::getPrice)
                .min()
                .orElse(0.0);

        Map<String, Double> priceRange = new HashMap<>();
        priceRange.put("highestPrice", highestPrice);
        priceRange.put("lowestPrice", lowestPrice);
        return priceRange;
    }

    @Override
    public Set<ManufacturerDTO> getManufacturers() {
        Set<Manufacturer> manufacturers = caseFanJPARepository.findAll()
                .stream()
                .map(CaseFan::getManufacturer)
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
        Set<Lighting> lightings = caseFanJPARepository.findAll()
                .stream()
                .map(CaseFan::getLighting)
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