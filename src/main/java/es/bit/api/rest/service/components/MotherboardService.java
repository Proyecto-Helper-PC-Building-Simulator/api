package es.bit.api.rest.service.components;

import es.bit.api.persistence.model.components.Gpu;
import es.bit.api.persistence.model.components.Motherboard;
import es.bit.api.persistence.model.components.attributes.CpuSocket;
import es.bit.api.persistence.model.components.attributes.MotherboardChipset;
import es.bit.api.persistence.model.components.attributes.MotherboardFormFactor;
import es.bit.api.persistence.model.components.attributes.MultiGpuType;
import es.bit.api.persistence.repository.jpa.IGenericJpaRepository;
import es.bit.api.rest.dto.components.MotherboardDTO;
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
public class MotherboardService extends GenericService<MotherboardDTO, Motherboard, Integer> {
    public MotherboardService(ComponentHandlerFactory<Motherboard, MotherboardDTO> handlerFactory, IGenericJpaRepository<Motherboard, Integer> repository) {
        super(handlerFactory, repository);
    }


    @Override
    public Long countFiltered(Map<String, String> filters) {
        return this.repository.count(getSpecification(filters));
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
}