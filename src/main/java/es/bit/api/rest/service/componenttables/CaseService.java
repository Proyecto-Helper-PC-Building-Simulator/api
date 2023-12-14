package es.bit.api.rest.service.componenttables;

import es.bit.api.persistence.model.componenttables.Case;
import es.bit.api.persistence.repository.jpa.componenttables.ICaseJPARepository;
import es.bit.api.rest.dto.componenttables.CaseDTO;
import es.bit.api.rest.mapper.componenttables.CaseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CaseService {
    @Autowired
    ICaseJPARepository caseObjectJPARepository;

    public Long count() {
        return this.caseObjectJPARepository.count();
    }

    public List<CaseDTO> findAll(int page, int size, Boolean withMotherboardFormFactors) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Case> caseObjectPage = this.caseObjectJPARepository.findAll(pageRequest);

        return CaseMapper.toDTO(caseObjectPage.getContent(), withMotherboardFormFactors);
    }

    public CaseDTO findById(Integer id, Boolean withMotherboardFormFactors) {
        Optional<Case> caseObject = this.caseObjectJPARepository.findById(id);

        if (caseObject.isEmpty()) {
            return null;
        }

        return CaseMapper.toDTO(caseObject, withMotherboardFormFactors);
    }



    public CaseDTO create(CaseDTO caseObjectDTO, Boolean withMotherboardFormFactors) {
        Case caseObject = CaseMapper.toBD(caseObjectDTO, withMotherboardFormFactors);
        caseObject = this.caseObjectJPARepository.save(caseObject);

        return CaseMapper.toDTO(caseObject, withMotherboardFormFactors);
    }

    public void update(CaseDTO caseObjectDTO, Boolean withMotherboardFormFactors) {
        Case caseObject = CaseMapper.toBD(caseObjectDTO, withMotherboardFormFactors);
        this.caseObjectJPARepository.save(caseObject);

        CaseMapper.toDTO(caseObject, withMotherboardFormFactors);
    }

    public void delete(CaseDTO caseObjectDTO, Boolean withMotherboardFormFactors) {
        Case caseObject = CaseMapper.toBD(caseObjectDTO, withMotherboardFormFactors);
        this.caseObjectJPARepository.delete(caseObject);
    }
}