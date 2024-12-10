package es.bit.api.rest.service.components.attributes;

import es.bit.api.persistence.model.components.attributes.MotherboardChipset;
import es.bit.api.persistence.repository.jpa.components.attributes.IMotherboardChipsetJPARepository;
import es.bit.api.rest.dto.components.attributes.MotherboardChipsetDTO;
import es.bit.api.rest.mapper.components.attributes.MotherboardChipsetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MotherboardChipsetService {
    @Autowired
    IMotherboardChipsetJPARepository motherboardChipsetJPARepository;

    public Long count() {
        return this.motherboardChipsetJPARepository.count();
    }

    @Cacheable(value = "motherboardChipsets", key = "#page + '-' + #size + '-' + #sortBy + '-' + #sortDir + '-' + #filters")
    public List<MotherboardChipsetDTO> findAll(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<MotherboardChipset> motherboardChipsetPage = this.motherboardChipsetJPARepository.findAll(pageRequest);

        return MotherboardChipsetMapper.toDTO(motherboardChipsetPage.getContent());
    }

    public MotherboardChipsetDTO findById(Integer id) {
        Optional<MotherboardChipset> motherboardChipset = this.motherboardChipsetJPARepository.findById(id);

        if (motherboardChipset.isEmpty()) {
            return null;
        }

        return MotherboardChipsetMapper.toDTO(motherboardChipset);
    }

    public MotherboardChipsetDTO create(MotherboardChipsetDTO motherboardChipsetDTO) {
        MotherboardChipset motherboardChipset = MotherboardChipsetMapper.toBD(motherboardChipsetDTO);
        motherboardChipset = this.motherboardChipsetJPARepository.save(motherboardChipset);

        return MotherboardChipsetMapper.toDTO(motherboardChipset);
    }

    public void update(MotherboardChipsetDTO motherboardChipsetDTO) {
        MotherboardChipset motherboardChipset = MotherboardChipsetMapper.toBD(motherboardChipsetDTO);
        this.motherboardChipsetJPARepository.save(motherboardChipset);

        MotherboardChipsetMapper.toDTO(motherboardChipset);
    }

    public void delete(MotherboardChipsetDTO motherboardChipsetDTO) {
        MotherboardChipset motherboardChipset = MotherboardChipsetMapper.toBD(motherboardChipsetDTO);
        this.motherboardChipsetJPARepository.delete(motherboardChipset);
    }
}
