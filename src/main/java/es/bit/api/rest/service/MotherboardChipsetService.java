package es.bit.api.rest.service;

import es.bit.api.persistence.model.MotherboardChipset;
import es.bit.api.persistence.repository.jpa.IMotherboardChipsetJPARepository;
import es.bit.api.rest.dto.MotherboardChipsetDTO;
import es.bit.api.rest.mapper.MotherboardChipsetMapper;
import org.springframework.beans.factory.annotation.Autowired;
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
