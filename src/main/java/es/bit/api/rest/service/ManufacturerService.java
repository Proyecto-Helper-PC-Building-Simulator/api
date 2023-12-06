package es.bit.api.rest.service;

import es.bit.api.persistence.model.Manufacturer;
import es.bit.api.persistence.repository.jpa.IManufacturerJPARepository;
import es.bit.api.rest.dto.ManufacturerDTO;
import es.bit.api.rest.mapper.ManufacturerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ManufacturerService {
    @Autowired
    IManufacturerJPARepository manufacturerJPARepository;

    public Long count() {
        return this.manufacturerJPARepository.count();
    }

    public List<ManufacturerDTO> findAll(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Manufacturer> manufacturerPage = this.manufacturerJPARepository.findAll(pageRequest);

        return ManufacturerMapper.toDTO(manufacturerPage.getContent());
    }

    public ManufacturerDTO findById(Integer id) {
        Optional<Manufacturer> manufacturer = this.manufacturerJPARepository.findById(id);

        if (manufacturer.isEmpty()) {
            return null;
        }

        return ManufacturerMapper.toDTO(manufacturer);
    }

    public ManufacturerDTO create(ManufacturerDTO manufacturerDTO) {
        Manufacturer manufacturer = ManufacturerMapper.toBD(manufacturerDTO);
        manufacturer = this.manufacturerJPARepository.save(manufacturer);

        return ManufacturerMapper.toDTO(manufacturer);
    }

    public void update(ManufacturerDTO manufacturerDTO) {
        Manufacturer manufacturer = ManufacturerMapper.toBD(manufacturerDTO);
        this.manufacturerJPARepository.save(manufacturer);

        ManufacturerMapper.toDTO(manufacturer);
    }

    public void delete(ManufacturerDTO manufacturerDTO) {
        Manufacturer manufacturer = ManufacturerMapper.toBD(manufacturerDTO);
        this.manufacturerJPARepository.delete(manufacturer);
    }
}
