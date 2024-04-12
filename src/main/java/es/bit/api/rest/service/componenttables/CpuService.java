package es.bit.api.rest.service.componenttables;

import es.bit.api.persistence.model.basictables.CpuSerie;
import es.bit.api.persistence.model.basictables.CpuSocket;
import es.bit.api.persistence.model.basictables.Manufacturer;
import es.bit.api.persistence.model.componenttables.Cpu;
import es.bit.api.persistence.repository.jpa.componenttables.ICpuJPARepository;
import es.bit.api.rest.dto.componenttables.CpuDTO;
import es.bit.api.rest.mapper.componenttables.CpuMapper;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
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
public class CpuService {
    @Autowired
    ICpuJPARepository cpuJPARepository;

    public Long count() {
        return this.cpuJPARepository.count();
    }

    public List<CpuDTO> findAll(int page, int size, String sortBy, String sortDir, Map<String, String> filters) {
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.fromString(sortDir), sortBy);
        Page<Cpu> cpuPage = this.cpuJPARepository.findAll(getSpecification(filters), pageable);
        return CpuMapper.toDTO(cpuPage.getContent());
    }

    public CpuDTO findById(Integer id) {
        Optional<Cpu> cpu = this.cpuJPARepository.findById(id);

        if (cpu.isEmpty()) {
            return null;
        }

        return CpuMapper.toDTO(cpu);
    }

    public CpuDTO create(CpuDTO cpuDTO) {
        Cpu cpu = CpuMapper.toBD(cpuDTO);
        cpu = this.cpuJPARepository.save(cpu);

        return CpuMapper.toDTO(cpu);
    }

    public void update(CpuDTO cpuDTO) {
        Cpu cpu = CpuMapper.toBD(cpuDTO);
        this.cpuJPARepository.save(cpu);

        CpuMapper.toDTO(cpu);
    }

    public void delete(CpuDTO cpuDTO) {
        Cpu cpu = CpuMapper.toBD(cpuDTO);
        this.cpuJPARepository.delete(cpu);
    }


    private Specification<Cpu> getSpecification(Map<String, String> filters) {
        return (root, query, criteriaBuilder) -> {
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
            if (filters.containsKey("coresMin")) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("cores"), Integer.parseInt(filters.get("coresMin"))));
            }
            if (filters.containsKey("coresMax")) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("cores"), Integer.parseInt(filters.get("coresMax"))));
            }
            if (filters.containsKey("frequencyMin")) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("frequency"), Integer.parseInt(filters.get("frequencyMin"))));
            }
            if (filters.containsKey("frequencyMax")) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("frequency"), Integer.parseInt(filters.get("frequencyMax"))));
            }
            if (filters.containsKey("serie")) {
                Join<Cpu, CpuSerie> cpuSerieJoin = root.join("cpuSerie", JoinType.INNER);
                predicates.add(criteriaBuilder.like(cpuSerieJoin.get("name"), "%" + filters.get("serie") + "%"));
            }
            if (filters.containsKey("socket")) {
                Join<Cpu, CpuSocket> cpuSocketJoin = root.join("cpuSocket", JoinType.INNER);
                predicates.add(criteriaBuilder.like(cpuSocketJoin.get("name"), "%" + filters.get("socket") + "%"));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}