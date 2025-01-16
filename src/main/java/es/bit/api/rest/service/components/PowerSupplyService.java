package es.bit.api.rest.service.components;

import es.bit.api.persistence.model.components.PowerSupply;
import es.bit.api.persistence.model.components.attributes.PowerSupplyFormFactor;
import es.bit.api.persistence.model.components.enums.PowerSupplyTypes;
import es.bit.api.rest.dto.components.PowerSupplyDTO;
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
public class PowerSupplyService extends GenericService<PowerSupplyDTO, PowerSupply, Integer> {

    @Override
    public Specification<PowerSupply> getSpecification(Map<String, String> filters) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            Predicate commonPredicates = addCommonPredicates(criteriaBuilder, root, filters);
            if (commonPredicates != null) {
                predicates.add(commonPredicates);
            }

            if (filters.containsKey("wattageMin")) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("wattage"), Integer.parseInt(filters.get("wattageMin"))));
            }
            if (filters.containsKey("wattageMax")) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("wattage"), Integer.parseInt(filters.get("wattageMax"))));
            }
            if (filters.containsKey("lengthMin")) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("length"), Integer.parseInt(filters.get("lengthMin"))));
            }
            if (filters.containsKey("lengthMax")) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("length"), Integer.parseInt(filters.get("lengthMax"))));
            }
            if (filters.containsKey("type")) {
                String typeName = filters.get("type");
                predicates.add(criteriaBuilder.equal(root.get("type"), PowerSupplyTypes.valueOf(typeName)));
            }
            if (filters.containsKey("formFactor")) {
                Join<PowerSupply, PowerSupplyFormFactor> powerSupplyFormFactor = root.join("powerSupplyFormFactor", JoinType.INNER);
                predicates.add(criteriaBuilder.like(powerSupplyFormFactor.get("name"), "%" + filters.get("formFactor") + "%"));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}