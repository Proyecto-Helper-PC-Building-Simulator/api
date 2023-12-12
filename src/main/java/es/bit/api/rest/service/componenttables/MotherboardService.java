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

    public List<MotherboardDTO> findAll(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Motherboard> motherboardPage = this.motherboardJPARepository.findAll(pageRequest);

        return MotherboardMapper.toDTO(motherboardPage.getContent());
    }

    public MotherboardDTO findById(Integer id) {
        Optional<Motherboard> motherboard = this.motherboardJPARepository.findById(id);

        if (motherboard.isEmpty()) {
            return null;
        }

        return MotherboardMapper.toDTO(motherboard);
    }



    public MotherboardDTO create(MotherboardDTO motherboardDTO) {
        Motherboard motherboard = MotherboardMapper.toBD(motherboardDTO);
        motherboard = this.motherboardJPARepository.save(motherboard);

        return MotherboardMapper.toDTO(motherboard);
    }

    public void update(MotherboardDTO motherboardDTO) {
        Motherboard motherboard = MotherboardMapper.toBD(motherboardDTO);
        this.motherboardJPARepository.save(motherboard);

        MotherboardMapper.toDTO(motherboard);
    }

    public void delete(MotherboardDTO motherboardDTO) {
        Motherboard motherboard = MotherboardMapper.toBD(motherboardDTO);
        this.motherboardJPARepository.delete(motherboard);
    }
}