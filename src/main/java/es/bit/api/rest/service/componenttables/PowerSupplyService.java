package es.bit.api.rest.service.componenttables;

import es.bit.api.persistence.model.componenttables.PowerSupply;
import es.bit.api.persistence.repository.jpa.componenttables.IPowerSupplyJPARepository;
import es.bit.api.rest.dto.componenttables.PowerSupplyDTO;
import es.bit.api.rest.mapper.componenttables.PowerSupplyMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PowerSupplyService {
    @Autowired
    IPowerSupplyJPARepository powerSupplyJPARepository;

    public Long count() {
        return this.powerSupplyJPARepository.count();
    }

    public List<PowerSupplyDTO> findAll(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<PowerSupply> powerSupplyPage = this.powerSupplyJPARepository.findAll(pageRequest);

        return PowerSupplyMapper.toDTO(powerSupplyPage.getContent());
    }

    public PowerSupplyDTO findById(Integer id) {
        Optional<PowerSupply> powerSupply = this.powerSupplyJPARepository.findById(id);

        if (powerSupply.isEmpty()) {
            return null;
        }

        return PowerSupplyMapper.toDTO(powerSupply);
    }



    public PowerSupplyDTO create(PowerSupplyDTO powerSupplyDTO) {
        PowerSupply powerSupply = PowerSupplyMapper.toBD(powerSupplyDTO);
        powerSupply = this.powerSupplyJPARepository.save(powerSupply);

        return PowerSupplyMapper.toDTO(powerSupply);
    }

    public void update(PowerSupplyDTO powerSupplyDTO) {
        PowerSupply powerSupply = PowerSupplyMapper.toBD(powerSupplyDTO);
        this.powerSupplyJPARepository.save(powerSupply);

        PowerSupplyMapper.toDTO(powerSupply);
    }

    public void delete(PowerSupplyDTO powerSupplyDTO) {
        PowerSupply powerSupply = PowerSupplyMapper.toBD(powerSupplyDTO);
        this.powerSupplyJPARepository.delete(powerSupply);
    }
}