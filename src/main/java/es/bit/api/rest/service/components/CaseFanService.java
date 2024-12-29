package es.bit.api.rest.service.components;

import es.bit.api.persistence.model.components.CaseFan;
import es.bit.api.persistence.repository.jpa.IGenericJpaRepository;
import es.bit.api.rest.dto.components.CaseFanDTO;
import es.bit.api.utils.handlers.ComponentHandlerFactory;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class CaseFanService extends GenericService<CaseFanDTO, CaseFan, Integer> {
    public CaseFanService(ComponentHandlerFactory<CaseFan, CaseFanDTO> handlerFactory, IGenericJpaRepository<CaseFan, Integer> repository) {
        super(handlerFactory, repository);
    }


    public Long countFiltered(Map<String, String> filters) {
        return this.repository.count(getSpecification(filters));
    }


    @Override
    public Specification<CaseFan> getSpecification(Map<String, String> filters) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            Predicate commonPredicates = addCommonPredicates(criteriaBuilder, root, filters);
            if (commonPredicates != null) {
                predicates.add(commonPredicates);
            }

            if (filters.containsKey("airFlowMin")) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("airFlow"), Float.parseFloat(filters.get("airFlowMin"))));
            }
            if (filters.containsKey("airFlowMax")) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("airFlow"), Float.parseFloat(filters.get("airFlowMax"))));
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
}