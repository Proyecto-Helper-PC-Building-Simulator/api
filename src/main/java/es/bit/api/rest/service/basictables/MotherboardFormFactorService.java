package es.bit.api.rest.service.basictables;

import es.bit.api.persistence.model.basictables.MotherboardFormFactor;
import es.bit.api.persistence.repository.jpa.basictables.IMotherboardFormFactorJPARepository;
import es.bit.api.rest.dto.basictables.MotherboardFormFactorDTO;
import es.bit.api.rest.mapper.basictables.MotherboardFormFactorMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
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

    @Cacheable(value = "motherboardFormFactors", key = "#page + '-' + #size + '-' + #sortBy + '-' + #sortDir + '-' + #filters")
    public List<MotherboardFormFactorDTO> findAll(int page, int size, Boolean withCases) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<MotherboardFormFactor> motherboardFormFactorPage = this.motherboardFormFactorJPARepository.findAll(pageRequest);

        return MotherboardFormFactorMapper.toDTO(motherboardFormFactorPage.getContent(), withCases);
    }

    public MotherboardFormFactorDTO findById(Integer id, Boolean withCases) {
        Optional<MotherboardFormFactor> motherboardFormFactor = this.motherboardFormFactorJPARepository.findById(id);

        if (motherboardFormFactor.isEmpty()) {
            return null;
        }

        return MotherboardFormFactorMapper.toDTO(motherboardFormFactor, withCases);
    }

    public MotherboardFormFactorDTO create(MotherboardFormFactorDTO motherboardFormFactorDTO, Boolean withCases) {
        MotherboardFormFactor motherboardFormFactor = MotherboardFormFactorMapper.toBD(motherboardFormFactorDTO, withCases);
        motherboardFormFactor = this.motherboardFormFactorJPARepository.save(motherboardFormFactor);

        return MotherboardFormFactorMapper.toDTO(motherboardFormFactor, withCases);
    }

    public void update(MotherboardFormFactorDTO motherboardFormFactorDTO, Boolean withCases) {
        MotherboardFormFactor motherboardFormFactor = MotherboardFormFactorMapper.toBD(motherboardFormFactorDTO, withCases);
        this.motherboardFormFactorJPARepository.save(motherboardFormFactor);

        MotherboardFormFactorMapper.toDTO(motherboardFormFactor, withCases);
    }

    public void delete(MotherboardFormFactorDTO motherboardFormFactorDTO, Boolean withCases) {
        MotherboardFormFactor motherboardFormFactor = MotherboardFormFactorMapper.toBD(motherboardFormFactorDTO, withCases);
        this.motherboardFormFactorJPARepository.delete(motherboardFormFactor);
    }
}
