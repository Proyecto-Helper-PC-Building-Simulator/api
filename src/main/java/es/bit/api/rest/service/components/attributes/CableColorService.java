package es.bit.api.rest.service.components.attributes;

import es.bit.api.persistence.model.components.attributes.CableColor;
import es.bit.api.persistence.repository.jpa.components.attributes.ICableColorJPARepository;
import es.bit.api.rest.dto.components.attributes.CableColorDTO;
import es.bit.api.rest.mapper.components.attributes.CableColorMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
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

    @Cacheable(value = "cableColors", key = "#page + '-' + #size + '-' + #sortBy + '-' + #sortDir + '-' + #filters")
    public List<CableColorDTO> findAll(int page, int size, Boolean withCables) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<CableColor> cableColorPage = this.cableColorJPARepository.findAll(pageRequest);

        return CableColorMapper.toDTO(cableColorPage.getContent(), withCables);
    }

    public CableColorDTO findById(Integer id, Boolean withCables) {
        Optional<CableColor> cableColor = this.cableColorJPARepository.findById(id);

        if (cableColor.isEmpty()) {
            return null;
        }

        return CableColorMapper.toDTO(cableColor, withCables);
    }

    public CableColorDTO create(CableColorDTO cableColorDTO, Boolean withCables) {
        CableColor cableColor = CableColorMapper.toBD(cableColorDTO, withCables);
        cableColor = this.cableColorJPARepository.save(cableColor);

        return CableColorMapper.toDTO(cableColor, withCables);
    }

    public void update(CableColorDTO cableColorDTO, Boolean withCables) {
        CableColor cableColor = CableColorMapper.toBD(cableColorDTO, withCables);
        this.cableColorJPARepository.save(cableColor);
    }

    public void delete(CableColorDTO cableColorDTO, Boolean withCables) {
        CableColor cableColor = CableColorMapper.toBD(cableColorDTO, withCables);
        this.cableColorJPARepository.delete(cableColor);
    }
}
