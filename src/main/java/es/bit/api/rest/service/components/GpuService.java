package es.bit.api.rest.service.components;

import es.bit.api.persistence.model.components.Gpu;
import es.bit.api.persistence.model.components.attributes.GpuChipsetSerie;
import es.bit.api.persistence.model.components.attributes.MultiGpuType;
import es.bit.api.persistence.model.components.enums.ChipsetBrands;
import es.bit.api.persistence.repository.jpa.IGenericJpaRepository;
import es.bit.api.rest.dto.components.GpuDTO;
import es.bit.api.rest.service.GenericService;
import es.bit.api.utils.handlers.ComponentHandlerFactory;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class GpuService extends GenericService<GpuDTO, Gpu, Integer> {
    public GpuService(ComponentHandlerFactory<Gpu, GpuDTO> handlerFactory, IGenericJpaRepository<Gpu, Integer> repository) {
        super(handlerFactory, repository);
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
}