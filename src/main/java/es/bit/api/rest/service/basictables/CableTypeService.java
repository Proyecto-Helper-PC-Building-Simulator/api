package es.bit.api.rest.service.basictables;

import es.bit.api.persistence.model.basictables.CableType;
import es.bit.api.persistence.repository.jpa.basictables.ICableTypeJPARepository;
import es.bit.api.rest.dto.basictables.CableTypeDTO;
import es.bit.api.rest.mapper.basictables.CableTypeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CableTypeService {
    @Autowired
    ICableTypeJPARepository cableTypeJPARepository;

    public Long count() {
        return this.cableTypeJPARepository.count();
    }

    public List<CableTypeDTO> findAll(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<CableType> cableTypePage = this.cableTypeJPARepository.findAll(pageRequest);

        return CableTypeMapper.toDTO(cableTypePage.getContent());
    }

    public CableTypeDTO findById(Integer id) {
        Optional<CableType> cableType = this.cableTypeJPARepository.findById(id);

        if (cableType.isEmpty()) {
            return null;
        }

        return CableTypeMapper.toDTO(cableType);
    }

    public CableTypeDTO create(CableTypeDTO cableTypeDTO) {
        CableType cableType = CableTypeMapper.toBD(cableTypeDTO);
        cableType = this.cableTypeJPARepository.save(cableType);

        return CableTypeMapper.toDTO(cableType);
    }

    public void update(CableTypeDTO cableTypeDTO) {
        CableType cableType = CableTypeMapper.toBD(cableTypeDTO);
        this.cableTypeJPARepository.save(cableType);

        CableTypeMapper.toDTO(cableType);
    }

    public void delete(CableTypeDTO cableTypeDTO) {
        CableType cableType = CableTypeMapper.toBD(cableTypeDTO);
        this.cableTypeJPARepository.delete(cableType);
    }
}
