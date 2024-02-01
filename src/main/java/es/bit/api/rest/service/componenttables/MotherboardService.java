package es.bit.api.rest.service.componenttables;

import es.bit.api.persistence.model.componenttables.Motherboard;
import es.bit.api.persistence.repository.jpa.componenttables.IMotherboardJPARepository;
import es.bit.api.rest.dto.componenttables.MotherboardDTO;
import es.bit.api.rest.mapper.componenttables.MotherboardMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MotherboardService {
    @Autowired
    IMotherboardJPARepository motherboardJPARepository;

    public Long count() {
        return this.motherboardJPARepository.count();
    }

    public List<MotherboardDTO> findAll(int page, int size, Boolean withMultiGpuTypes) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Motherboard> motherboardPage = this.motherboardJPARepository.findAll(pageRequest);

        return MotherboardMapper.toDTO(motherboardPage.getContent(), withMultiGpuTypes);
    }

    public MotherboardDTO findById(Integer id, Boolean withMultiGpuTypes) {
        Optional<Motherboard> motherboard = this.motherboardJPARepository.findById(id);

        if (motherboard.isEmpty()) {
            return null;
        }

        return MotherboardMapper.toDTO(motherboard, withMultiGpuTypes);
    }



    public MotherboardDTO create(MotherboardDTO motherboardDTO, Boolean withMultiGpuTypes) {
        Motherboard motherboard = MotherboardMapper.toBD(motherboardDTO, withMultiGpuTypes);
        motherboard = this.motherboardJPARepository.save(motherboard);

        return MotherboardMapper.toDTO(motherboard, withMultiGpuTypes);
    }

    public void update(MotherboardDTO motherboardDTO, Boolean withMultiGpuTypes) {
        Motherboard motherboard = MotherboardMapper.toBD(motherboardDTO, withMultiGpuTypes);
        this.motherboardJPARepository.save(motherboard);
    }

    public void delete(MotherboardDTO motherboardDTO, Boolean withMultiGpuTypes) {
        Motherboard motherboard = MotherboardMapper.toBD(motherboardDTO, withMultiGpuTypes);
        this.motherboardJPARepository.delete(motherboard);
    }
}