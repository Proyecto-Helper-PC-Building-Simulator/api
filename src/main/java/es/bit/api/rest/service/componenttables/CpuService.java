package es.bit.api.rest.service.componenttables;

import es.bit.api.persistence.model.basictables.CpuSerie;
import es.bit.api.persistence.model.basictables.CpuSocket;
import es.bit.api.persistence.model.basictables.Manufacturer;
import es.bit.api.persistence.model.componenttables.Cpu;
import es.bit.api.persistence.repository.jpa.IGenericJpaRepository;
import es.bit.api.rest.dto.basictables.ManufacturerDTO;
import es.bit.api.rest.dto.componenttables.CpuDTO;
import es.bit.api.rest.mapper.componenttables.CpuMapper;
import es.bit.api.rest.service.GenericService;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
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
public class CpuService implements GenericService<CpuDTO, Cpu, Integer> {
    private final IGenericJpaRepository<Cpu, Integer> cpuJPARepository;


    @Autowired
    public CpuService(IGenericJpaRepository<Cpu, Integer> cpuJPARepository) {
        this.cpuJPARepository = cpuJPARepository;
    }


    @Override
    public Long count() {
        return this.cpuJPARepository.count();
    }

    @Override
    public CpuDTO findById(Integer id) {
        Optional<Cpu> cpu = this.cpuJPARepository.findById(id);

        if (cpu.isEmpty()) {
            return null;
        }

        return CpuMapper.toDTO(cpu);
    }

    @Override
    public List<CpuDTO> findAll(int page, int size, String sortBy, String sortDir, Map<String, String> filters) {
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.fromString(sortDir), sortBy);
        Page<Cpu> cpuPage = this.cpuJPARepository.findAll(getSpecification(filters), pageable);
        return CpuMapper.toDTO(cpuPage.getContent());
    }

    @Override
    public CpuDTO create(CpuDTO cpuDTO) {
        Cpu cpu = CpuMapper.toBD(cpuDTO);
        cpu = this.cpuJPARepository.save(cpu);

        return CpuMapper.toDTO(cpu);
    }

    @Override
    public void update(CpuDTO cpuDTO) {
        Cpu cpu = CpuMapper.toBD(cpuDTO);
        this.cpuJPARepository.save(cpu);

        CpuMapper.toDTO(cpu);
    }

    @Override
    public void delete(CpuDTO cpuDTO) {
        Cpu cpu = CpuMapper.toBD(cpuDTO);
        this.cpuJPARepository.delete(cpu);
    }


    @Override
    public Specification<Cpu> getSpecification(Map<String, String> filters) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            Predicate commonPredicates = addCommonPredicates(criteriaBuilder, root, filters);
            if (commonPredicates != null) {
                predicates.add(commonPredicates);
            }

            if (filters.containsKey("coresMin")) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("cores"), Integer.parseInt(filters.get("coresMin"))));
            }
            if (filters.containsKey("coresMax")) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("cores"), Integer.parseInt(filters.get("coresMax"))));
            }
            if (filters.containsKey("frequencyMin")) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("frequency"), Integer.parseInt(filters.get("frequencyMin"))));
            }
            if (filters.containsKey("frequencyMax")) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("frequency"), Integer.parseInt(filters.get("frequencyMax"))));
            }
            if (filters.containsKey("serie")) {
                Join<Cpu, CpuSerie> cpuSerieJoin = root.join("cpuSerie", JoinType.INNER);
                predicates.add(criteriaBuilder.like(cpuSerieJoin.get("name"), "%" + filters.get("serie") + "%"));
            }
            if (filters.containsKey("socket")) {
                Join<Cpu, CpuSocket> cpuSocketJoin = root.join("cpuSocket", JoinType.INNER);
                predicates.add(criteriaBuilder.like(cpuSocketJoin.get("name"), "%" + filters.get("socket") + "%"));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    @Override
    public Map<String, Double> getPriceRange() {
        Double highestPrice = cpuJPARepository.findAll()
                .stream()
                .mapToDouble(Cpu::getPrice)
                .max()
                .orElse(0.0);

        Double lowestPrice = cpuJPARepository.findAll()
                .stream()
                .mapToDouble(Cpu::getPrice)
                .min()
                .orElse(0.0);

        Map<String, Double> priceRange = new HashMap<>();
        priceRange.put("highestPrice", highestPrice);
        priceRange.put("lowestPrice", lowestPrice);
        return priceRange;
    }

    @Override
    public Set<ManufacturerDTO> getManufacturers() {
        Set<Manufacturer> manufacturers = cpuJPARepository.findAll()
                .stream()
                .map(Cpu::getManufacturer)
                .collect(Collectors.toSet());

        // Convertir los Manufacturer en ManufacturerDTO

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