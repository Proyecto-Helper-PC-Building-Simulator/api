package es.bit.api.rest.service.basictables;

import es.bit.api.persistence.model.basictables.CableColor;
import es.bit.api.persistence.repository.jpa.basictables.ICableColorJPARepository;
import es.bit.api.rest.dto.basictables.CableColorDTO;
import es.bit.api.rest.mapper.basictables.CableColorMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CableColorService {
    @Autowired
    ICableColorJPARepository cableColorJPARepository;

    public Long count() {
        return this.cableColorJPARepository.count();
    }

    public List<CableColorDTO> findAll(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<CableColor> cableColorPage = this.cableColorJPARepository.findAll(pageRequest);

        return CableColorMapper.toDTO(cableColorPage.getContent());
    }

    public CableColorDTO findById(Integer id) {
        Optional<CableColor> cableColor = this.cableColorJPARepository.findById(id);

        if (cableColor.isEmpty()) {
            return null;
        }

        return CableColorMapper.toDTO(cableColor);
    }

    public CableColorDTO create(CableColorDTO cableColorDTO) {
        CableColor cableColor = CableColorMapper.toBD(cableColorDTO);
        cableColor = this.cableColorJPARepository.save(cableColor);

        return CableColorMapper.toDTO(cableColor);
    }

    public void update(CableColorDTO cableColorDTO) {
        CableColor cableColor = CableColorMapper.toBD(cableColorDTO);
        this.cableColorJPARepository.save(cableColor);

        CableColorMapper.toDTO(cableColor);
    }

    public void delete(CableColorDTO cableColorDTO) {
        CableColor cableColor = CableColorMapper.toBD(cableColorDTO);
        this.cableColorJPARepository.delete(cableColor);
    }
}
