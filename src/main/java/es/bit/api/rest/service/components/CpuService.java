package es.bit.api.rest.service.components;

import es.bit.api.persistence.model.components.Cpu;
import es.bit.api.persistence.model.components.attributes.CpuSerie;
import es.bit.api.persistence.model.components.attributes.CpuSocket;
import es.bit.api.persistence.repository.jpa.IGenericJpaRepository;
import es.bit.api.rest.dto.components.CpuDTO;
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
public class CpuService extends GenericService<CpuDTO, Cpu, Integer> {
    public CpuService(ComponentHandlerFactory<Cpu, CpuDTO> handlerFactory, IGenericJpaRepository<Cpu, Integer> repository) {
        super(handlerFactory, repository);
    }


    @Override
    public Long countFiltered(Map<String, String> filters) {
        return this.repository.count(getSpecification(filters));
    }


    @Override
    public Specification<Cpu> getSpecification(Map<String, String> filters) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            Predicate commonPredicates = addCommonPredicates(criteriaBuilder, root, filters);
            if (commonPredicates != null) {
                predicates.add(commonPredicates);
            }

            filters.forEach((key, value) -> {
                switch (key) {
                    case "coresMin":
                        predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("cores"), Integer.parseInt(value)));
                        break;
                    case "coresMax":
                        predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("cores"), Integer.parseInt(value)));
                        break;
                    case "frequencyMin":
                        predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("frequency"), Integer.parseInt(value)));
                        break;
                    case "frequencyMax":
                        predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("frequency"), Integer.parseInt(value)));
                        break;
                    case "wattageMin":
                        predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("wattage"), Integer.parseInt(value)));
                        break;
                    case "wattageMax":
                        predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("wattage"), Integer.parseInt(value)));
                        break;
                    case "serie":
                        Join<Cpu, CpuSerie> cpuSerieJoin = root.join("cpuSerie", JoinType.INNER);
                        predicates.add(criteriaBuilder.like(cpuSerieJoin.get("name"), "%" + value + "%"));
                        break;
                    case "socket":
                        Join<Cpu, CpuSocket> cpuSocketJoin = root.join("cpuSocket", JoinType.INNER);
                        predicates.add(criteriaBuilder.like(cpuSocketJoin.get("name"), "%" + value + "%"));
                        break;
                    default:
                        // Ignore unknown filters
                        break;
                }
            });

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}