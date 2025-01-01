package es.bit.api.rest.service.components;

import es.bit.api.persistence.model.components.Cable;
import es.bit.api.persistence.model.components.Case;
import es.bit.api.persistence.model.components.attributes.CableColor;
import es.bit.api.persistence.model.components.attributes.CaseFanSize;
import es.bit.api.persistence.model.components.attributes.CaseSize;
import es.bit.api.persistence.model.components.attributes.MotherboardFormFactor;
import es.bit.api.persistence.repository.jpa.IGenericJpaRepository;
import es.bit.api.rest.dto.components.CaseDTO;
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
public class CaseService extends GenericService<CaseDTO, Case, Integer> {
    public CaseService(ComponentHandlerFactory<Case, CaseDTO> handlerFactory, IGenericJpaRepository<Case, Integer> repository) {
        super(handlerFactory, repository);
    }


    @Override
    public Specification<Case> getSpecification(Map<String, String> filters) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            Predicate commonPredicates = addCommonPredicates(criteriaBuilder, root, filters);
            if (commonPredicates != null) {
                predicates.add(commonPredicates);
            }

            if (filters.containsKey("psuLengthMin")) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("maxPsuLength"), Integer.parseInt(filters.get("psuLengthMin"))));
            }
            if (filters.containsKey("psuLengthMax")) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("maxPsuLength"), Integer.parseInt(filters.get("psuLengthMax"))));
            }
            if (filters.containsKey("gpuLengthMin")) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("maxGpuLength"), Integer.parseInt(filters.get("gpuLengthMin"))));
            }
            if (filters.containsKey("gpuLengthMax")) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("maxGpuLength"), Integer.parseInt(filters.get("gpuLengthMax"))));
            }
            if (filters.containsKey("cpuFanHeightMin")) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("maxCpuFanHeight"), Integer.parseInt(filters.get("cpuFanHeightMin"))));
            }
            if (filters.containsKey("cpuFanHeightMax")) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("maxCpuFanHeight"), Integer.parseInt(filters.get("cpuFanHeightMax"))));
            }
            if (filters.containsKey("caseSize")) {
                Join<Case, CaseSize> caseSizeJoin = root.join("caseSize", JoinType.INNER);
                predicates.add(criteriaBuilder.like(caseSizeJoin.get("name"), "%" + filters.get("caseSize") + "%"));
            }
            if (filters.containsKey("fanSize")) {
                Join<Case, CaseFanSize> caseFanSizeJoin = root.join("caseFanSize", JoinType.INNER);
                predicates.add(criteriaBuilder.like(caseFanSizeJoin.get("name"), "%" + filters.get("fanSize") + "%"));
            }
            if (filters.containsKey("motherboardFormFactor")) {
                String[] motherboardFormFactors = filters.get("motherboardFormFactor").split(",");
                for (String motherboardFormFactor : motherboardFormFactors) {
                    Join<Case, MotherboardFormFactor> cpuSocketJoin = root.joinList("motherboardFormFactors", JoinType.INNER);
                    predicates.add(criteriaBuilder.like(cpuSocketJoin.get("name"), "%" + motherboardFormFactor.trim() + "%"));
                }
            }
            if (filters.containsKey("psuFormFactor")) {
                String[] powerSupplyFormFactors = filters.get("psuFormFactor").split(",");
                for (String powerSupplyFormFactor : powerSupplyFormFactors) {
                    Join<Cable, CableColor> cpuSocketJoin = root.joinList("powerSupplyFormFactors", JoinType.INNER);
                    predicates.add(criteriaBuilder.like(cpuSocketJoin.get("name"), "%" + powerSupplyFormFactor.trim() + "%"));
                }
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}