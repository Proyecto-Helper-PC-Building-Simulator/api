package es.bit.api.rest.service.componenttables;

import es.bit.api.persistence.model.basictables.*;
import es.bit.api.persistence.model.componenttables.Gpu;
import es.bit.api.persistence.model.componenttables.Motherboard;
import es.bit.api.persistence.repository.jpa.IGenericJpaRepository;
import es.bit.api.rest.dto.basictables.LightingDTO;
import es.bit.api.rest.dto.basictables.ManufacturerDTO;
import es.bit.api.rest.dto.componenttables.MotherboardDTO;
import es.bit.api.rest.mapper.componenttables.MotherboardMapper;
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
public class MotherboardService implements GenericService<MotherboardDTO, Motherboard, Integer> {
    private final IGenericJpaRepository<Motherboard, Integer> motherboardJPARepository;


    @Autowired
    public MotherboardService(IGenericJpaRepository<Motherboard, Integer> motherboardJPARepository) {
        this.motherboardJPARepository = motherboardJPARepository;
    }


    @Override
    public Long count() {
        return this.motherboardJPARepository.count();
    }

    @Override
    public MotherboardDTO findById(Integer id) {
        Optional<Motherboard> motherboard = this.motherboardJPARepository.findById(id);

        if (motherboard.isEmpty()) {
            return null;
        }

        return MotherboardMapper.toDTO(motherboard, true);
    }

    @Override
    @Cacheable(value = "motherboards", key = "#page + '-' + #size + '-' + #sortBy + '-' + #sortDir + '-' + #filters")
    public List<MotherboardDTO> findAll(int page, int size, String sortBy, String sortDir, Map<String, String> filters) {
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.fromString(sortDir), sortBy);
        Page<Motherboard> motherboardPage = this.motherboardJPARepository.findAll(getSpecification(filters), pageable);
        return MotherboardMapper.toDTO(motherboardPage.getContent(), true);
    }

    @Override
    public MotherboardDTO create(MotherboardDTO motherboardDTO) {
        Motherboard motherboard = MotherboardMapper.toBD(motherboardDTO, true);
        motherboard = this.motherboardJPARepository.save(motherboard);

        return MotherboardMapper.toDTO(motherboard, true);
    }

    @Override
    public void update(MotherboardDTO motherboardDTO) {
        Motherboard motherboard = MotherboardMapper.toBD(motherboardDTO, true);
        this.motherboardJPARepository.save(motherboard);
    }

    @Override
    public void delete(MotherboardDTO motherboardDTO) {
        Motherboard motherboard = MotherboardMapper.toBD(motherboardDTO, false);
        this.motherboardJPARepository.delete(motherboard);
    }


    @Override
    public Specification<Motherboard> getSpecification(Map<String, String> filters) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            Predicate commonPredicates = addCommonPredicates(criteriaBuilder, root, filters);
            if (commonPredicates != null) {
                predicates.add(commonPredicates);
            }

            if (filters.containsKey("frequencyMin")) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("maxRamSpeed"), Integer.parseInt(filters.get("frequencyMin"))));
            }
            if (filters.containsKey("frequencyMax")) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("maxRamSpeed"), Integer.parseInt(filters.get("frequencyMax"))));
            }
            if (filters.containsKey("formFactor")) {
                Join<Motherboard, MotherboardFormFactor> cpuSerieJoin = root.join("motherboardFormFactor", JoinType.INNER);
                predicates.add(criteriaBuilder.like(cpuSerieJoin.get("name"), "%" + filters.get("formFactor") + "%"));
            }
            if (filters.containsKey("chipset")) {
                Join<Motherboard, MotherboardChipset> cpuSerieJoin = root.join("motherboardChipset", JoinType.INNER);
                predicates.add(criteriaBuilder.like(cpuSerieJoin.get("name"), "%" + filters.get("chipset") + "%"));
            }
            if (filters.containsKey("socket")) {
                Join<Motherboard, CpuSocket> cpuSocketJoin = root.join("cpuSocket", JoinType.INNER);
                predicates.add(criteriaBuilder.like(cpuSocketJoin.get("name"), "%" + filters.get("socket") + "%"));
            }
            if (filters.containsKey("multiGpuType")) {
                Join<Gpu, MultiGpuType> cpuSocketJoin = root.join("multiGpuTypes", JoinType.INNER);
                predicates.add(criteriaBuilder.like(cpuSocketJoin.get("name"), "%" + filters.get("multiGpuType") + "%"));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    @Override
    public Map<String, Double> getPriceRange() {
        Double highestPrice = motherboardJPARepository.findAll()
                .stream()
                .mapToDouble(Motherboard::getPrice)
                .max()
                .orElse(0.0);

        Double lowestPrice = motherboardJPARepository.findAll()
                .stream()
                .mapToDouble(Motherboard::getPrice)
                .min()
                .orElse(0.0);

        Map<String, Double> priceRange = new HashMap<>();
        priceRange.put("highestPrice", highestPrice);
        priceRange.put("lowestPrice", lowestPrice);
        return priceRange;
    }

    @Override
    public Set<ManufacturerDTO> getManufacturers() {
        Set<Manufacturer> manufacturers = motherboardJPARepository.findAll()
                .stream()
                .map(Motherboard::getManufacturer)
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
        Set<Lighting> lightings = motherboardJPARepository.findAll()
                .stream()
                .map(Motherboard::getLighting)
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