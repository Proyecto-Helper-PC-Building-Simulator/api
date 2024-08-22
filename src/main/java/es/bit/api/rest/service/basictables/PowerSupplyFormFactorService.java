package es.bit.api.rest.service.basictables;

import es.bit.api.persistence.model.basictables.PowerSupplyFormFactor;
import es.bit.api.persistence.repository.jpa.basictables.IPowerSupplyFormFactorJPARepository;
import es.bit.api.rest.dto.basictables.PowerSupplyFormFactorDTO;
import es.bit.api.rest.mapper.basictables.PowerSupplyFormFactorMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PowerSupplyFormFactorService {
    @Autowired
    IPowerSupplyFormFactorJPARepository powerSupplyFormFactorJPARepository;

    public Long count() {
        return this.powerSupplyFormFactorJPARepository.count();
    }

    @Cacheable(value = "powerSupplyFormFactors", key = "#page + '-' + #size + '-' + #sortBy + '-' + #sortDir + '-' + #filters")
    public List<PowerSupplyFormFactorDTO> findAll(int page, int size, Boolean withCases) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<PowerSupplyFormFactor> powerSupplyFormFactorPage = this.powerSupplyFormFactorJPARepository.findAll(pageRequest);

        return PowerSupplyFormFactorMapper.toDTO(powerSupplyFormFactorPage.getContent(), withCases);
    }

    public PowerSupplyFormFactorDTO findById(Integer id, Boolean withCases) {
        Optional<PowerSupplyFormFactor> powerSupplyFormFactor = this.powerSupplyFormFactorJPARepository.findById(id);

        if (powerSupplyFormFactor.isEmpty()) {
            return null;
        }

        return PowerSupplyFormFactorMapper.toDTO(powerSupplyFormFactor, withCases);
    }

    public PowerSupplyFormFactorDTO create(PowerSupplyFormFactorDTO powerSupplyFormFactorDTO, Boolean withCases) {
        PowerSupplyFormFactor powerSupplyFormFactor = PowerSupplyFormFactorMapper.toBD(powerSupplyFormFactorDTO, withCases);
        powerSupplyFormFactor = this.powerSupplyFormFactorJPARepository.save(powerSupplyFormFactor);

        return PowerSupplyFormFactorMapper.toDTO(powerSupplyFormFactor, withCases);
    }

    public void update(PowerSupplyFormFactorDTO powerSupplyFormFactorDTO, Boolean withCases) {
        PowerSupplyFormFactor powerSupplyFormFactor = PowerSupplyFormFactorMapper.toBD(powerSupplyFormFactorDTO, withCases);
        this.powerSupplyFormFactorJPARepository.save(powerSupplyFormFactor);
    }

    public void delete(PowerSupplyFormFactorDTO powerSupplyFormFactorDTO, Boolean withCases) {
        PowerSupplyFormFactor powerSupplyFormFactor = PowerSupplyFormFactorMapper.toBD(powerSupplyFormFactorDTO, withCases);
        this.powerSupplyFormFactorJPARepository.delete(powerSupplyFormFactor);
    }
}
