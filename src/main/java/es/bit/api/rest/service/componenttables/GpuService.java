package es.bit.api.rest.service.componenttables;

import es.bit.api.persistence.model.basictables.GpuChipsetSerie;
import es.bit.api.persistence.model.basictables.Manufacturer;
import es.bit.api.persistence.model.basictables.MultiGpuType;
import es.bit.api.persistence.model.componenttables.Gpu;
import es.bit.api.persistence.model.componenttables.enums.ChipsetBrands;
import es.bit.api.persistence.repository.jpa.componenttables.IGpuJpaRepository;
import es.bit.api.rest.dto.basictables.ManufacturerDTO;
import es.bit.api.rest.dto.componenttables.GpuDTO;
import es.bit.api.rest.mapper.componenttables.GpuMapper;
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
public class GpuService implements GenericService<GpuDTO, Gpu, Integer> {
    private final IGpuJpaRepository gpuJPARepository;


    @Autowired
    public GpuService(IGpuJpaRepository gpuJPARepository) {
        this.gpuJPARepository = gpuJPARepository;
    }


    @Override
    public Long count() {
        return this.gpuJPARepository.count();
    }

    @Override
    public List<GpuDTO> findAll(int page, int size, String sortBy, String sortDir, Map<String, String> filters) {
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.fromString(sortDir), sortBy);
        Page<Gpu> cpuPage = this.gpuJPARepository.findAll(getSpecification(filters), pageable);
        return GpuMapper.toDTO(cpuPage.getContent());
    }

    @Override
    public GpuDTO findById(Integer id) {
        Optional<Gpu> gpu = this.gpuJPARepository.findById(id);

        if (gpu.isEmpty()) {
            return null;
        }

        return GpuMapper.toDTO(gpu);
    }

    @Override
    public GpuDTO create(GpuDTO gpuDTO) {
        Gpu gpu = GpuMapper.toBD(gpuDTO);
        gpu = this.gpuJPARepository.save(gpu);

        return GpuMapper.toDTO(gpu);
    }

    @Override
    public void update(GpuDTO gpuDTO) {
        Gpu gpu = GpuMapper.toBD(gpuDTO);
        this.gpuJPARepository.save(gpu);

        GpuMapper.toDTO(gpu);
    }

    @Override
    public void delete(GpuDTO gpuDTO) {
        Gpu gpu = GpuMapper.toBD(gpuDTO);
        this.gpuJPARepository.delete(gpu);
    }


    @Override
    public Specification<Gpu> getSpecification(Map<String, String> filters) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            Predicate commonPredicates = addCommonPredicates(criteriaBuilder, root, filters);
            if (commonPredicates != null) {
                predicates.add(commonPredicates);
            }

            if (filters.containsKey("chipsetBrand")) {
                String chipsetBrandName = filters.get("chipsetBrand");
                predicates.add(criteriaBuilder.equal(root.get("chipsetBrand"), ChipsetBrands.valueOf(chipsetBrandName)));
            }
            if (filters.containsKey("vramMin")) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("vram"), Integer.parseInt(filters.get("vramMin"))));
            }
            if (filters.containsKey("vramMax")) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("vram"), Integer.parseInt(filters.get("vramMax"))));
            }
            if (filters.containsKey("memoryFrequencyMin")) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("memoryFrequency"), Integer.parseInt(filters.get("memoryFrequencyMin"))));
            }
            if (filters.containsKey("memoryFrequencyMax")) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("memoryFrequency"), Integer.parseInt(filters.get("memoryFrequencyMax"))));
            }
            if (filters.containsKey("coreFrequencyMin")) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("coreFrequency"), Integer.parseInt(filters.get("coreFrequencyMin"))));
            }
            if (filters.containsKey("coreFrequencyMax")) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("coreFrequency"), Integer.parseInt(filters.get("coreFrequencyMax"))));
            }
            if (filters.containsKey("lengthMin")) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("length"), Integer.parseInt(filters.get("lengthMin"))));
            }
            if (filters.containsKey("lengthMax")) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("length"), Integer.parseInt(filters.get("lengthMax"))));
            }
            if (filters.containsKey("wattageMin")) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("wattage"), Integer.parseInt(filters.get("wattageMin"))));
            }
            if (filters.containsKey("wattageMax")) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("wattage"), Integer.parseInt(filters.get("wattageMax"))));
            }
            if (filters.containsKey("chipsetSerie")) {
                Join<Gpu, GpuChipsetSerie> cpuSerieJoin = root.join("gpuChipsetSerie", JoinType.INNER);
                predicates.add(criteriaBuilder.like(cpuSerieJoin.get("name"), "%" + filters.get("chipsetSerie") + "%"));
            }
            if (filters.containsKey("multiGpuType")) {
                Join<Gpu, MultiGpuType> cpuSocketJoin = root.join("multiGpuType", JoinType.INNER);
                predicates.add(criteriaBuilder.like(cpuSocketJoin.get("name"), "%" + filters.get("multiGpuType") + "%"));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    @Override
    public Map<String, Double> getPriceRange() {
        Double highestPrice = gpuJPARepository.findAll()
                .stream()
                .mapToDouble(Gpu::getPrice)
                .max()
                .orElse(0.0);

        Double lowestPrice = gpuJPARepository.findAll()
                .stream()
                .mapToDouble(Gpu::getPrice)
                .min()
                .orElse(0.0);

        Map<String, Double> priceRange = new HashMap<>();
        priceRange.put("highestPrice", highestPrice);
        priceRange.put("lowestPrice", lowestPrice);
        return priceRange;
    }

    @Override
    public Set<ManufacturerDTO> getManufacturers() {
        Set<Manufacturer> manufacturers = gpuJPARepository.findAll()
                .stream()
                .map(Gpu::getManufacturer)
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