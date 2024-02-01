package es.bit.api.rest.service.componenttables;

import es.bit.api.persistence.model.componenttables.RamMemory;
import es.bit.api.persistence.repository.jpa.componenttables.IRamMemoryJPARepository;
import es.bit.api.rest.dto.componenttables.RamMemoryDTO;
import es.bit.api.rest.mapper.componenttables.RamMemoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RamMemoryService {
    @Autowired
    IRamMemoryJPARepository ramMemoryJPARepository;

    public Long count() {
        return this.ramMemoryJPARepository.count();
    }

    public List<RamMemoryDTO> findAll(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<RamMemory> ramMemoryPage = this.ramMemoryJPARepository.findAll(pageRequest);

        return RamMemoryMapper.toDTO(ramMemoryPage.getContent());
    }

    public RamMemoryDTO findById(Integer id) {
        Optional<RamMemory> ramMemory = this.ramMemoryJPARepository.findById(id);

        if (ramMemory.isEmpty()) {
            return null;
        }

        return RamMemoryMapper.toDTO(ramMemory);
    }



    public RamMemoryDTO create(RamMemoryDTO ramMemoryDTO) {
        RamMemory ramMemory = RamMemoryMapper.toBD(ramMemoryDTO);
        ramMemory = this.ramMemoryJPARepository.save(ramMemory);

        return RamMemoryMapper.toDTO(ramMemory);
    }

    public void update(RamMemoryDTO ramMemoryDTO) {
        RamMemory ramMemory = RamMemoryMapper.toBD(ramMemoryDTO);
        this.ramMemoryJPARepository.save(ramMemory);

        RamMemoryMapper.toDTO(ramMemory);
    }

    public void delete(RamMemoryDTO ramMemoryDTO) {
        RamMemory ramMemory = RamMemoryMapper.toBD(ramMemoryDTO);
        this.ramMemoryJPARepository.delete(ramMemory);
    }
}