package es.bit.api.rest.service.components;

import es.bit.api.persistence.model.components.CpuCooler;
import es.bit.api.persistence.model.components.attributes.CpuSocket;
import es.bit.api.persistence.model.components.enums.CoolerTypes;
import es.bit.api.rest.dto.components.CpuCoolerDTO;
import es.bit.api.rest.service.GenericService;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class CpuCoolerService extends GenericService<CpuCoolerDTO, CpuCooler, Integer> {

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
            if (filters.containsKey("socket")) {
                Join<CpuCooler, CpuSocket> cpuSocketJoin = root.join("cpuSockets", JoinType.INNER);
                predicates.add(criteriaBuilder.like(cpuSocketJoin.get("name"), "%" + filters.get("socket") + "%"));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}