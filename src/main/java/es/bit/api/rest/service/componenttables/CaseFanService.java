package es.bit.api.rest.service.componenttables;

import es.bit.api.persistence.model.componenttables.CaseFan;
import es.bit.api.persistence.repository.jpa.componenttables.ICaseFanJPARepository;
import es.bit.api.rest.dto.componenttables.CaseFanDTO;
import es.bit.api.rest.mapper.componenttables.CaseFanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CaseFanService {
    @Autowired
    ICaseFanJPARepository caseFanJPARepository;

    public Long count() {
        return this.caseFanJPARepository.count();
    }

    public List<CaseFanDTO> findAll(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<CaseFan> caseFanPage = this.caseFanJPARepository.findAll(pageRequest);

        return CaseFanMapper.toDTO(caseFanPage.getContent());
    }

    public CaseFanDTO findById(Integer id) {
        Optional<CaseFan> caseFan = this.caseFanJPARepository.findById(id);

        if (caseFan.isEmpty()) {
            return null;
        }

        return CaseFanMapper.toDTO(caseFan);
    }



    public CaseFanDTO create(CaseFanDTO caseFanDTO) {
        CaseFan caseFan = CaseFanMapper.toBD(caseFanDTO);
        caseFan = this.caseFanJPARepository.save(caseFan);

        return CaseFanMapper.toDTO(caseFan);
    }

    public void update(CaseFanDTO caseFanDTO) {
        CaseFan caseFan = CaseFanMapper.toBD(caseFanDTO);
        this.caseFanJPARepository.save(caseFan);

        CaseFanMapper.toDTO(caseFan);
    }

    public void delete(CaseFanDTO caseFanDTO) {
        CaseFan caseFan = CaseFanMapper.toBD(caseFanDTO);
        this.caseFanJPARepository.delete(caseFan);
    }
}