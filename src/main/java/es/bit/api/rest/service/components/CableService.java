package es.bit.api.rest.service.components;

import es.bit.api.persistence.model.components.Cable;
import es.bit.api.persistence.model.components.attributes.CableColor;
import es.bit.api.persistence.model.components.attributes.CableType;
import es.bit.api.persistence.repository.jpa.IGenericJpaRepository;
import es.bit.api.rest.dto.components.CableDTO;
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
public class CableService extends GenericService<CableDTO, Cable, Integer> {
    public CableService(ComponentHandlerFactory<Cable, CableDTO> handlerFactory, IGenericJpaRepository<Cable, Integer> repository) {
        super(handlerFactory, repository);
    }


    @Override
    public Specification<Cable> getSpecification(Map<String, String> filters) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            Predicate commonPredicates = addCommonPredicates(criteriaBuilder, root, filters);
            if (commonPredicates != null) {
                predicates.add(commonPredicates);
            }

            if (filters.containsKey("type")) {
                Join<Cable, CableType> cpuSerieJoin = root.join("cableType", JoinType.INNER);
                predicates.add(criteriaBuilder.like(cpuSerieJoin.get("name"), "%" + filters.get("type") + "%"));
            }
            if (filters.containsKey("color")) {
                String[] colors = filters.get("color").split(",");
                for (String color : colors) {
                    Join<Cable, CableColor> cpuSocketJoin = root.joinList("cableColors", JoinType.INNER);
                    predicates.add(criteriaBuilder.like(cpuSocketJoin.get("name"), "%" + color.trim() + "%"));
                }
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}