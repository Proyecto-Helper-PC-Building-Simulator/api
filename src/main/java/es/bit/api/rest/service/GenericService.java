package es.bit.api.rest.service;

import es.bit.api.persistence.model.basictables.Manufacturer;
import es.bit.api.persistence.model.componenttables.Cpu;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @param <D> Component DTO
 * @param <C> Component
 * @param <I> Integer
 */
public interface GenericService<D, C, I> {
    Long count();
    List<D> findAll(int page, int size, String sortBy, String sortDir, Map<String, String> filters);
    D findById(I id);
    D create(D dto);
    void update(D dto);
    void delete(D dto);

    default Specification<C> getSpecification(Map<String, String> filters) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            Predicate commonPredicates = addCommonPredicates(criteriaBuilder, root, filters);
            if (commonPredicates != null) {
                predicates.add(commonPredicates);
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }


    /**
     * Adds common predicates based on the provided filters.
     *
     * @param criteriaBuilder The CriteriaBuilder used to construct criteria queries.
     * @param root            The Root object representing the entity to which the predicates are applied.
     * @param filters         A Map containing filter parameters.
     * @return A Predicate object representing the combined common predicates.
     */
    default Predicate addCommonPredicates(CriteriaBuilder criteriaBuilder, Root<C> root, Map<String, String> filters) {
        List<Predicate> predicates = new ArrayList<>();

        if (filters.containsKey("name")) {
            String[] searchTerms = filters.get("name").split("\\s+");
            Predicate[] termPredicates = new Predicate[searchTerms.length];
            for (int i = 0; i < searchTerms.length; i++) {
                String searchTerm = "%" + searchTerms[i] + "%";
                termPredicates[i] = criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), criteriaBuilder.lower(criteriaBuilder.literal(searchTerm)));
            }
            predicates.add(criteriaBuilder.and(termPredicates));
        }
        if (filters.containsKey("manufacturer")) {
            Join<Cpu, Manufacturer> cpuSocketJoin = root.join("manufacturer", JoinType.INNER);
            predicates.add(criteriaBuilder.like(cpuSocketJoin.get("name"), "%" + filters.get("manufacturer") + "%"));
        }
        if (filters.containsKey("lighting")) {
            Join<Cpu, Manufacturer> cpuSocketJoin = root.join("lighting", JoinType.INNER);
            predicates.add(criteriaBuilder.like(cpuSocketJoin.get("name"), "%" + filters.get("lighting") + "%"));
        }
        if (filters.containsKey("priceMin")) {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("price"), Integer.parseInt(filters.get("priceMin"))));
        }
        if (filters.containsKey("priceMax")) {
            predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("price"), Integer.parseInt(filters.get("priceMax"))));
        }

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
