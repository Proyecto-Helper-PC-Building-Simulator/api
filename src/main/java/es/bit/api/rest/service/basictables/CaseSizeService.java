package es.bit.api.rest.service.basictables;

import es.bit.api.persistence.model.basictables.CaseSize;
import es.bit.api.persistence.repository.jpa.basictables.ICaseSizeJPARepository;
import es.bit.api.rest.dto.basictables.CaseSizeDTO;
import es.bit.api.rest.mapper.basictables.CaseSizeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CaseSizeService {
    @Autowired
    ICaseSizeJPARepository caseSizeJPARepository;

    public Long count() {
        return this.caseSizeJPARepository.count();
    }

    public List<CaseSizeDTO> findAll(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<CaseSize> caseSizePage = this.caseSizeJPARepository.findAll(pageRequest);

        return CaseSizeMapper.toDTO(caseSizePage.getContent());
    }

    public CaseSizeDTO findById(Integer id) {
        Optional<CaseSize> caseSize = this.caseSizeJPARepository.findById(id);

        if (caseSize.isEmpty()) {
            return null;
        }

        return CaseSizeMapper.toDTO(caseSize);
    }

    public CaseSizeDTO create(CaseSizeDTO caseSizeDTO) {
        CaseSize caseSize = CaseSizeMapper.toBD(caseSizeDTO);
        caseSize = this.caseSizeJPARepository.save(caseSize);

        return CaseSizeMapper.toDTO(caseSize);
    }

    public void update(CaseSizeDTO caseSizeDTO) {
        CaseSize caseSize = CaseSizeMapper.toBD(caseSizeDTO);
        this.caseSizeJPARepository.save(caseSize);

        CaseSizeMapper.toDTO(caseSize);
    }

    public void delete(CaseSizeDTO caseSizeDTO) {
        CaseSize caseSize = CaseSizeMapper.toBD(caseSizeDTO);
        this.caseSizeJPARepository.delete(caseSize);
    }
}
