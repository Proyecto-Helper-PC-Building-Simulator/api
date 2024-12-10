package es.bit.api.rest.service.components;

import es.bit.api.persistence.model.components.attributes.CpuSerie;
import es.bit.api.persistence.model.components.attributes.CpuSocket;
import es.bit.api.persistence.model.components.attributes.Lighting;
import es.bit.api.persistence.model.components.attributes.Manufacturer;
import es.bit.api.persistence.model.components.Cpu;
import es.bit.api.persistence.repository.jpa.IGenericJpaRepository;
import es.bit.api.rest.dto.components.attributes.LightingDTO;
import es.bit.api.rest.dto.components.attributes.ManufacturerDTO;
import es.bit.api.rest.dto.components.CpuDTO;
import es.bit.api.rest.mapper.components.CpuMapper;
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
    public Long countFiltered(Map<String, String> filters) {
        return this.cpuJPARepository.count(getSpecification(filters));
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
    @Cacheable(value = "cpus", key = "#page + '-' + #size + '-' + #sortBy + '-' + #sortDir + '-' + #filters")
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

            filters.forEach((key, value) -> {
                switch (key) {
                    case "coresMin":
                        predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("cores"), Integer.parseInt(value)));
                        break;
                    case "coresMax":
                        predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("cores"), Integer.parseInt(value)));
                        break;
                    case "frequencyMin":
                        predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("frequency"), Integer.parseInt(value)));
                        break;
                    case "frequencyMax":
                        predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("frequency"), Integer.parseInt(value)));
                        break;
                    case "wattageMin":
                        predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("wattage"), Integer.parseInt(value)));
                        break;
                    case "wattageMax":
                        predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("wattage"), Integer.parseInt(value)));
                        break;
                    case "serie":
                        Join<Cpu, CpuSerie> cpuSerieJoin = root.join("cpuSerie", JoinType.INNER);
                        predicates.add(criteriaBuilder.like(cpuSerieJoin.get("name"), "%" + value + "%"));
                        break;
                    case "socket":
                        Join<Cpu, CpuSocket> cpuSocketJoin = root.join("cpuSocket", JoinType.INNER);
                        predicates.add(criteriaBuilder.like(cpuSocketJoin.get("name"), "%" + value + "%"));
                        break;
                    default:
                        // Ignore unknown filters
                        break;
                }
            });

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

    @Override
    public Set<LightingDTO> getLightings() {
        Set<Lighting> lightings = cpuJPARepository.findAll()
                .stream()
                .map(Cpu::getLighting)
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