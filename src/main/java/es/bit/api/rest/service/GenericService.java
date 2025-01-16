package es.bit.api.rest.service;

import es.bit.api.persistence.model.components.Component;
import es.bit.api.persistence.model.components.attributes.GenericAttribute;
import es.bit.api.persistence.repository.jpa.IGenericJpaRepository;
import es.bit.api.rest.dto.components.ComponentDTO;
import es.bit.api.utils.PagedResponse;
import es.bit.api.utils.handlers.ComponentHandler;
import es.bit.api.utils.handlers.ComponentHandlerFactory;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @param <D> Component DTO
 * @param <C> Component
 * @param <I> Integer
 */
public abstract class GenericService<D extends ComponentDTO, C extends Component, I extends Serializable> {
    @Autowired
    protected IGenericJpaRepository<C, I> repository;

    @Autowired
    protected ComponentHandlerFactory<C, D> handlerFactory;


    public Optional<D> findById(I id) {
        return this.repository.findById(id)
                .map(component -> {
                    ComponentHandler<C, D> handler = handlerFactory.getHandler(component.getComponentType().getNameIdentifier());

                    return handler.toDTO(component);
                });
    }

    @Cacheable(value = "components", key = "#componentType + '-' + #pageable + '-' + #filters")
    public PagedResponse<D> findAll(String componentType, Pageable pageable, Map<String, String> filters) {
        Page<D> page = this.repository.findAll(getSpecification(filters), pageable).map(component -> {
            ComponentHandler<C, D> handler = handlerFactory.getHandler(component.getComponentType().getNameIdentifier());
            return handler.toDTO(component);
        });

        return PagedResponse.toCustomPageResponse(page);
    }

    public D save(D dto) {
        ComponentHandler<C, D> handler = handlerFactory.getHandler(dto.getComponentTypeDTO().getNameIdentifier());
        C component = handler.toEntity(dto);

        return handler.toDTO(this.repository.save(component));
    }

    public D update(I id, D dto) {
        return repository.findById(id)
                .map(componentExistent -> this.save(dto))
                .orElseThrow(() -> new EntityNotFoundException("Component not found with ID: " + id));
    }

    public void delete(I id) {
        this.repository.deleteById(id);
    }


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
            Join<Component, GenericAttribute> componentJoin;

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
}
