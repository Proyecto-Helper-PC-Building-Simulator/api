package es.bit.api.rest.service.components;

import es.bit.api.persistence.model.components.Storage;
import es.bit.api.persistence.model.components.enums.StorageTypes;
import es.bit.api.persistence.repository.jpa.IGenericJpaRepository;
import es.bit.api.rest.dto.components.StorageDTO;
import es.bit.api.rest.service.GenericService;
import es.bit.api.utils.handlers.ComponentHandlerFactory;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class StorageService extends GenericService<StorageDTO, Storage, Integer> {
    public StorageService(ComponentHandlerFactory<Storage, StorageDTO> handlerFactory, IGenericJpaRepository<Storage, Integer> repository) {
        super(handlerFactory, repository);
    }


    @Override
    public Specification<Storage> getSpecification(Map<String, String> filters) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            Predicate commonPredicates = addCommonPredicates(criteriaBuilder, root, filters);
            if (commonPredicates != null) {
                predicates.add(commonPredicates);
            }

            if (filters.containsKey("sizeMin")) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("size"), Integer.parseInt(filters.get("sizeMin"))));
            }
            if (filters.containsKey("sizeMax")) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("size"), Integer.parseInt(filters.get("sizeMax"))));
            }
            if (filters.containsKey("transferSpeedMin")) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("transferSpeed"), Integer.parseInt(filters.get("transferSpeedMin"))));
            }
            if (filters.containsKey("transferSpeedMax")) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("transferSpeed"), Integer.parseInt(filters.get("transferSpeedMax"))));
            }
            if (filters.containsKey("type")) {
                String typeName = filters.get("type");
                predicates.add(criteriaBuilder.equal(root.get("type"), StorageTypes.valueOf(typeName)));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}