package es.bit.api.rest.service.componenttables;

import es.bit.api.persistence.model.componenttables.Cable;
import es.bit.api.persistence.repository.jpa.componenttables.ICableJPARepository;
import es.bit.api.rest.dto.componenttables.CableDTO;
import es.bit.api.rest.mapper.componenttables.CableMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CableService {
    @Autowired
    ICableJPARepository cableJPARepository;

    public Long count() {
        return this.cableJPARepository.count();
    }

    public List<CableDTO> findAll(int page, int size, Boolean withCableColors) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Cable> cablePage = this.cableJPARepository.findAll(pageRequest);

        return CableMapper.toDTO(cablePage.getContent(), withCableColors);
    }

    public CableDTO findById(Integer id, Boolean withCableColors) {
        Optional<Cable> cable = this.cableJPARepository.findById(id);

        if (cable.isEmpty()) {
            return null;
        }

        return CableMapper.toDTO(cable, withCableColors);
    }



    public CableDTO create(CableDTO cableDTO, Boolean withCableColors) {
        Cable cable = CableMapper.toBD(cableDTO, withCableColors);
        cable = this.cableJPARepository.save(cable);

        return CableMapper.toDTO(cable, withCableColors);
    }

    public void update(CableDTO cableDTO, Boolean withCableColors) {
        Cable cable = CableMapper.toBD(cableDTO, withCableColors);
        this.cableJPARepository.save(cable);
    }

    public void delete(CableDTO cableDTO, Boolean withCableColors) {
        Cable cable = CableMapper.toBD(cableDTO, withCableColors);
        this.cableJPARepository.delete(cable);
    }
}