package es.bit.api.rest.service.componenttables;

import es.bit.api.persistence.model.basictables.Manufacturer;
import es.bit.api.persistence.model.componenttables.CpuCooler;
import es.bit.api.persistence.model.componenttables.enums.CoolerTypes;
import es.bit.api.persistence.repository.jpa.componenttables.ICpuCoolerJpaRepository;
import es.bit.api.rest.dto.basictables.ManufacturerDTO;
import es.bit.api.rest.dto.componenttables.CpuCoolerDTO;
import es.bit.api.rest.mapper.componenttables.CpuCoolerMapper;
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
public class CpuCoolerService implements GenericService<CpuCoolerDTO, CpuCooler, Integer> {
    private final ICpuCoolerJpaRepository cpuCoolerJPARepository;


    @Autowired
    public CpuCoolerService(ICpuCoolerJpaRepository cpuCoolerJPARepository) {
        this.cpuCoolerJPARepository = cpuCoolerJPARepository;
    }


    @Override
    public Long count() {
        return this.cpuCoolerJPARepository.count();
    }

    @Override
    public List<CpuCoolerDTO> findAll(int page, int size, String sortBy, String sortDir, Map<String, String> filters) {
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.fromString(sortDir), sortBy);
        Page<CpuCooler> cpuPage = this.cpuCoolerJPARepository.findAll(getSpecification(filters), pageable);
        return CpuCoolerMapper.toDTO(cpuPage.getContent(), true);
    }

    @Override
    public CpuCoolerDTO findById(Integer id) {
        Optional<CpuCooler> cpuCooler = this.cpuCoolerJPARepository.findById(id);

        if (cpuCooler.isEmpty()) {
            return null;
        }

        return CpuCoolerMapper.toDTO(cpuCooler, true);
    }

    @Override
    public CpuCoolerDTO create(CpuCoolerDTO cpuCoolerDTO) {
        CpuCooler cpuCooler = CpuCoolerMapper.toBD(cpuCoolerDTO, true);
        cpuCooler = this.cpuCoolerJPARepository.save(cpuCooler);

        return CpuCoolerMapper.toDTO(cpuCooler, true);
    }

    @Override
    public void update(CpuCoolerDTO cpuCoolerDTO) {
        CpuCooler cpuCooler = CpuCoolerMapper.toBD(cpuCoolerDTO, true);
        this.cpuCoolerJPARepository.save(cpuCooler);
    }

    @Override
    public void delete(CpuCoolerDTO cpuCoolerDTO) {
        CpuCooler cpuCooler = CpuCoolerMapper.toBD(cpuCoolerDTO, false);
        this.cpuCoolerJPARepository.delete(cpuCooler);
    }


    @Override
    public Specification<CpuCooler> getSpecification(Map<String, String> filters) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            Predicate commonPredicates = addCommonPredicates(criteriaBuilder, root, filters);
            if (commonPredicates != null) {
                predicates.add(commonPredicates);
            }

            if (filters.containsKey("type")) {
                String typeName = filters.get("type");
                predicates.add(criteriaBuilder.equal(root.get("type"), CoolerTypes.valueOf(typeName)));
            }
            if (filters.containsKey("airFlowMin")) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("airFlow"), Float.parseFloat(filters.get("airFlowMin"))));
            }
            if (filters.containsKey("airFlowMax")) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("airFlow"), Float.parseFloat(filters.get("airFlowMax"))));
            }
            if (filters.containsKey("heightMin")) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("height"), Integer.parseInt(filters.get("heightMin"))));
            }
            if (filters.containsKey("heightMax")) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("height"), Integer.parseInt(filters.get("heightMax"))));
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
        Double highestPrice = cpuCoolerJPARepository.findAll()
                .stream()
                .mapToDouble(CpuCooler::getPrice)
                .max()
                .orElse(0.0);

        Double lowestPrice = cpuCoolerJPARepository.findAll()
                .stream()
                .mapToDouble(CpuCooler::getPrice)
                .min()
                .orElse(0.0);

        Map<String, Double> priceRange = new HashMap<>();
        priceRange.put("highestPrice", highestPrice);
        priceRange.put("lowestPrice", lowestPrice);
        return priceRange;
    }

    @Override
    public Set<ManufacturerDTO> getManufacturers() {
        Set<Manufacturer> manufacturers = cpuCoolerJPARepository.findAll()
                .stream()
                .map(CpuCooler::getManufacturer)
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