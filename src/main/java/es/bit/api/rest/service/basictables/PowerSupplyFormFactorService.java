package es.bit.api.rest.service.basictables;

import es.bit.api.persistence.model.basictables.PowerSupplyFormFactor;
import es.bit.api.persistence.repository.jpa.basictables.IPowerSupplyFormFactorJPARepository;
import es.bit.api.rest.dto.basictables.PowerSupplyFormFactorDTO;
import es.bit.api.rest.mapper.basictables.PowerSupplyFormFactorMapper;
import org.springframework.beans.factory.annotation.Autowired;
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

    public List<PowerSupplyFormFactorDTO> findAll(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<PowerSupplyFormFactor> powerSupplyFormFactorPage = this.powerSupplyFormFactorJPARepository.findAll(pageRequest);

        return PowerSupplyFormFactorMapper.toDTO(powerSupplyFormFactorPage.getContent());
    }

    public PowerSupplyFormFactorDTO findById(Integer id) {
        Optional<PowerSupplyFormFactor> powerSupplyFormFactor = this.powerSupplyFormFactorJPARepository.findById(id);

        if (powerSupplyFormFactor.isEmpty()) {
            return null;
        }

        return PowerSupplyFormFactorMapper.toDTO(powerSupplyFormFactor);
    }

    public PowerSupplyFormFactorDTO create(PowerSupplyFormFactorDTO powerSupplyFormFactorDTO) {
        PowerSupplyFormFactor powerSupplyFormFactor = PowerSupplyFormFactorMapper.toBD(powerSupplyFormFactorDTO);
        powerSupplyFormFactor = this.powerSupplyFormFactorJPARepository.save(powerSupplyFormFactor);

        return PowerSupplyFormFactorMapper.toDTO(powerSupplyFormFactor);
    }

    public void update(PowerSupplyFormFactorDTO powerSupplyFormFactorDTO) {
        PowerSupplyFormFactor powerSupplyFormFactor = PowerSupplyFormFactorMapper.toBD(powerSupplyFormFactorDTO);
        this.powerSupplyFormFactorJPARepository.save(powerSupplyFormFactor);

        PowerSupplyFormFactorMapper.toDTO(powerSupplyFormFactor);
    }

    public void delete(PowerSupplyFormFactorDTO powerSupplyFormFactorDTO) {
        PowerSupplyFormFactor powerSupplyFormFactor = PowerSupplyFormFactorMapper.toBD(powerSupplyFormFactorDTO);
        this.powerSupplyFormFactorJPARepository.delete(powerSupplyFormFactor);
    }
}
