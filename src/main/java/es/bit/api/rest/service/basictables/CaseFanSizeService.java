package es.bit.api.rest.service.basictables;

import es.bit.api.persistence.model.basictables.CaseFanSize;
import es.bit.api.persistence.repository.jpa.basictables.ICaseFanSizeJPARepository;
import es.bit.api.rest.dto.basictables.CaseFanSizeDTO;
import es.bit.api.rest.mapper.basictables.CaseFanSizeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CaseFanSizeService {
    @Autowired
    ICaseFanSizeJPARepository caseFanSizeJPARepository;

    public Long count() {
        return this.caseFanSizeJPARepository.count();
    }

    @Cacheable(value = "caseFanSizes", key = "#page + '-' + #size + '-' + #sortBy + '-' + #sortDir + '-' + #filters")
    public List<CaseFanSizeDTO> findAll(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<CaseFanSize> caseFanSizePage = this.caseFanSizeJPARepository.findAll(pageRequest);

        return CaseFanSizeMapper.toDTO(caseFanSizePage.getContent());
    }

    public CaseFanSizeDTO findById(Integer id) {
        Optional<CaseFanSize> caseFanSize = this.caseFanSizeJPARepository.findById(id);

        if (caseFanSize.isEmpty()) {
            return null;
        }

        return CaseFanSizeMapper.toDTO(caseFanSize);
    }

    public CaseFanSizeDTO create(CaseFanSizeDTO caseFanSizeDTO) {
        CaseFanSize caseFanSize = CaseFanSizeMapper.toBD(caseFanSizeDTO);
        caseFanSize = this.caseFanSizeJPARepository.save(caseFanSize);

        return CaseFanSizeMapper.toDTO(caseFanSize);
    }

    public void update(CaseFanSizeDTO caseFanSizeDTO) {
        CaseFanSize caseFanSize = CaseFanSizeMapper.toBD(caseFanSizeDTO);
        this.caseFanSizeJPARepository.save(caseFanSize);

        CaseFanSizeMapper.toDTO(caseFanSize);
    }

    public void delete(CaseFanSizeDTO caseFanSizeDTO) {
        CaseFanSize caseFanSize = CaseFanSizeMapper.toBD(caseFanSizeDTO);
        this.caseFanSizeJPARepository.delete(caseFanSize);
    }
}
