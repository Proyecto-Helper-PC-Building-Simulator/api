package es.bit.api.rest.service.components;

import es.bit.api.persistence.model.components.Component;
import es.bit.api.persistence.model.components.attributes.Manufacturer;
import es.bit.api.persistence.repository.jpa.IGenericJpaRepository;
import jakarta.persistence.criteria.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @param <D> Component DTO
 * @param <C> Component
 * @param <I> Integer
 */
public abstract class GenericService<D, C, I extends Serializable> {
    @Autowired
    private IGenericJpaRepository<C, I> repository;

    abstract public Long count();
    abstract public Long countFiltered(Map<String, String> filters);
    abstract public List<D> findAll(int page, int size, String sortBy, String sortDir, Map<String, String> filters);
    abstract public D findById(I id);
    abstract public D create(D dto);
    abstract public void update(D dto);
    abstract public void delete(D dto);

    public Specification<C> getSpecification(Map<String, String> filters) {
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
    public Predicate addCommonPredicates(CriteriaBuilder criteriaBuilder, Root<C> root, Map<String, String> filters) {
        List<Predicate> predicates = new ArrayList<>();

        filters.forEach((key, value) -> {
            // TODO: Change Manufacturer for parent class (to implement)
            Join<Component, Manufacturer> componentJoin;

            switch (key) {
                case "name":
                    String[] searchTerms = filters.get("name").split("\\s+");
                    Predicate[] termPredicates = new Predicate[searchTerms.length];
                    for (int i = 0; i < searchTerms.length; i++) {
                        String searchTerm = "%" + searchTerms[i] + "%";
                        termPredicates[i] = criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), criteriaBuilder.lower(criteriaBuilder.literal(searchTerm)));
                    }
                    predicates.add(criteriaBuilder.and(termPredicates));
                    break;
                case "manufacturer":
                    componentJoin = root.join("manufacturer", JoinType.INNER);
                    predicates.add(criteriaBuilder.like(componentJoin.get("name"), "%" + filters.get("manufacturer") + "%"));
                    break;
                case "lighting":
                    componentJoin = root.join("lighting", JoinType.INNER);
                    predicates.add(criteriaBuilder.like(componentJoin.get("name"), "%" + filters.get("lighting") + "%"));
                    break;
                case "priceMin":
                    predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("price"), Integer.parseInt(filters.get("priceMin"))));
                    break;
                case "priceMax":
                    predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("price"), Integer.parseInt(filters.get("priceMax"))));
                    break;
                case "level":
                    predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("level"), Integer.parseInt(filters.get("level"))));
                    break;
                default:
                    // Ignores unknown filters
                    break;
            }
        });

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }

    public Map<String, Object> getCommonFilters() {
        Map<String, Object> filters = new HashMap<>();

        filters.put("manufacturers", repository.findDistinctManufacturers());

        List<Object[]> priceRange = repository.findMinMaxPrice();
        if (!priceRange.isEmpty()) {
            filters.put("price", Map.of("min", priceRange.get(0)[0], "max", priceRange.get(0)[1]));
        }

        filters.put("lightings", repository.findDistinctLightings());

        List<Object[]> levelRange = repository.findMinMaxLevel();
        if (!levelRange.isEmpty()) {
            filters.put("level", Map.of("min", levelRange.get(0)[0], "max", levelRange.get(0)[1]));
        }

        return filters;
    }
}
