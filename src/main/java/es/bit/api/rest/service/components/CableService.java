package es.bit.api.rest.service.components;

import es.bit.api.persistence.model.components.Cable;
import es.bit.api.persistence.model.components.attributes.CableColor;
import es.bit.api.persistence.model.components.attributes.CableType;
import es.bit.api.persistence.repository.jpa.components.ICableJpaRepository;
import es.bit.api.rest.dto.components.CableDTO;
import es.bit.api.rest.mapper.components.CableMapper;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class CableService extends GenericService<CableDTO, Cable, Integer> {
    private final ICableJpaRepository cableJPARepository;


    @Autowired
    public CableService(ICableJpaRepository cableJPARepository) {
        this.cableJPARepository = cableJPARepository;
    }


    @Override
    public Long count() {
        return this.cableJPARepository.count();
    }

    @Override
    public Long countFiltered(Map<String, String> filters) {
        return this.cableJPARepository.count(getSpecification(filters));
    }

    @Override
    public CableDTO findById(Integer id) {
        Optional<Cable> cable = this.cableJPARepository.findById(id);

        if (cable.isEmpty()) {
            return null;
        }

        return CableMapper.toDTO(cable, true);
    }

    @Override
    @Cacheable(value = "cables", key = "#page + '-' + #size + '-' + #sortBy + '-' + #sortDir + '-' + #filters")
    public List<CableDTO> findAll(int page, int size, String sortBy, String sortDir, Map<String, String> filters) {
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.fromString(sortDir), sortBy);
        Page<Cable> cablePage = this.cableJPARepository.findAll(getSpecification(filters), pageable);
        return CableMapper.toDTO(cablePage.getContent(), true);
    }

    @Override
    public CableDTO create(CableDTO cableDTO) {
        Cable cable = CableMapper.toBD(cableDTO, true);
        cable = this.cableJPARepository.save(cable);

        return CableMapper.toDTO(cable, true);
    }

    @Override
    public void update(CableDTO cableDTO) {
        Cable cable = CableMapper.toBD(cableDTO, true);
        this.cableJPARepository.save(cable);
    }

    @Override
    public void delete(CableDTO cableDTO) {
        Cable cable = CableMapper.toBD(cableDTO, false);
        this.cableJPARepository.delete(cable);
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