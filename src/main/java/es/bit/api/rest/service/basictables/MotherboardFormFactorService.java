package es.bit.api.rest.service.basictables;

import es.bit.api.persistence.model.basictables.MotherboardFormFactor;
import es.bit.api.persistence.repository.jpa.basictables.IMotherboardFormFactorJPARepository;
import es.bit.api.rest.dto.basictables.MotherboardFormFactorDTO;
import es.bit.api.rest.mapper.basictables.MotherboardFormFactorMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MotherboardFormFactorService {
    @Autowired
    IMotherboardFormFactorJPARepository motherboardFormFactorJPARepository;

    public Long count() {
        return this.motherboardFormFactorJPARepository.count();
    }

    public List<MotherboardFormFactorDTO> findAll(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<MotherboardFormFactor> motherboardFormFactorPage = this.motherboardFormFactorJPARepository.findAll(pageRequest);

        return MotherboardFormFactorMapper.toDTO(motherboardFormFactorPage.getContent());
    }

    public MotherboardFormFactorDTO findById(Integer id) {
        Optional<MotherboardFormFactor> motherboardFormFactor = this.motherboardFormFactorJPARepository.findById(id);

        if (motherboardFormFactor.isEmpty()) {
            return null;
        }

        return MotherboardFormFactorMapper.toDTO(motherboardFormFactor);
    }

    public MotherboardFormFactorDTO create(MotherboardFormFactorDTO motherboardFormFactorDTO) {
        MotherboardFormFactor motherboardFormFactor = MotherboardFormFactorMapper.toBD(motherboardFormFactorDTO);
        motherboardFormFactor = this.motherboardFormFactorJPARepository.save(motherboardFormFactor);

        return MotherboardFormFactorMapper.toDTO(motherboardFormFactor);
    }

    public void update(MotherboardFormFactorDTO motherboardFormFactorDTO) {
        MotherboardFormFactor motherboardFormFactor = MotherboardFormFactorMapper.toBD(motherboardFormFactorDTO);
        this.motherboardFormFactorJPARepository.save(motherboardFormFactor);

        MotherboardFormFactorMapper.toDTO(motherboardFormFactor);
    }

    public void delete(MotherboardFormFactorDTO motherboardFormFactorDTO) {
        MotherboardFormFactor motherboardFormFactor = MotherboardFormFactorMapper.toBD(motherboardFormFactorDTO);
        this.motherboardFormFactorJPARepository.delete(motherboardFormFactor);
    }
}
