package es.bit.api.rest.service.basictables;

import es.bit.api.persistence.model.basictables.Lighting;
import es.bit.api.persistence.repository.jpa.basictables.ILightingJPARepository;
import es.bit.api.rest.dto.basictables.LightingDTO;
import es.bit.api.rest.mapper.basictables.LightingMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LightingService {
    @Autowired
    ILightingJPARepository lightingJPARepository;

    public Long count() {
        return this.lightingJPARepository.count();
    }

    public List<LightingDTO> findAll(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Lighting> lightingPage = this.lightingJPARepository.findAll(pageRequest);

        return LightingMapper.toDTO(lightingPage.getContent());
    }

    public LightingDTO findById(Integer id) {
        Optional<Lighting> lighting = this.lightingJPARepository.findById(id);

        if (lighting.isEmpty()) {
            return null;
        }

        return LightingMapper.toDTO(lighting);
    }

    public LightingDTO create(LightingDTO lightingDTO) {
        Lighting lighting = LightingMapper.toBD(lightingDTO);
        lighting = this.lightingJPARepository.save(lighting);

        return LightingMapper.toDTO(lighting);
    }

    public void update(LightingDTO lightingDTO) {
        Lighting lighting = LightingMapper.toBD(lightingDTO);
        this.lightingJPARepository.save(lighting);

        LightingMapper.toDTO(lighting);
    }

    public void delete(LightingDTO lightingDTO) {
        Lighting lighting = LightingMapper.toBD(lightingDTO);
        this.lightingJPARepository.delete(lighting);
    }
}
