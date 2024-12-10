package es.bit.api.rest.service.components;

import es.bit.api.persistence.model.components.Cable;
import es.bit.api.persistence.model.components.Case;
import es.bit.api.persistence.model.components.attributes.CableColor;
import es.bit.api.persistence.model.components.attributes.CaseFanSize;
import es.bit.api.persistence.model.components.attributes.CaseSize;
import es.bit.api.persistence.model.components.attributes.MotherboardFormFactor;
import es.bit.api.persistence.repository.jpa.components.ICaseJpaRepository;
import es.bit.api.rest.dto.components.CaseDTO;
import es.bit.api.rest.mapper.components.CaseMapper;
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
public class CaseService extends GenericService<CaseDTO, Case, Integer> {
    private final ICaseJpaRepository caseObjectJPARepository;


    @Autowired
    public CaseService(ICaseJpaRepository caseObjectJPARepository) {
        this.caseObjectJPARepository = caseObjectJPARepository;
    }


    @Override
    public Long count() {
        return this.caseObjectJPARepository.count();
    }

    @Override
    public Long countFiltered(Map<String, String> filters) {
        return this.caseObjectJPARepository.count(getSpecification(filters));
    }

    @Override
    public CaseDTO findById(Integer id) {
        Optional<Case> caseObject = this.caseObjectJPARepository.findById(id);

        if (caseObject.isEmpty()) {
            return null;
        }

        return CaseMapper.toDTO(caseObject, true, true);
    }

    @Override
    @Cacheable(value = "cases", key = "#page + '-' + #size + '-' + #sortBy + '-' + #sortDir + '-' + #filters")
    public List<CaseDTO> findAll(int page, int size, String sortBy, String sortDir, Map<String, String> filters) {
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.fromString(sortDir), sortBy);
        Page<Case> cpuPage = this.caseObjectJPARepository.findAll(getSpecification(filters), pageable);
        return CaseMapper.toDTO(cpuPage.getContent(), true, true);
    }

    @Override
    public CaseDTO create(CaseDTO caseObjectDTO) {
        Case caseObject = CaseMapper.toBD(caseObjectDTO, true, true);
        caseObject = this.caseObjectJPARepository.save(caseObject);

        return CaseMapper.toDTO(caseObject, true, true);
    }

    @Override
    public void update(CaseDTO caseObjectDTO) {
        Case caseObject = CaseMapper.toBD(caseObjectDTO, true, true);
        this.caseObjectJPARepository.save(caseObject);
    }

    @Override
    public void delete(CaseDTO caseObjectDTO) {
        Case caseObject = CaseMapper.toBD(caseObjectDTO, false, false);
        this.caseObjectJPARepository.delete(caseObject);
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