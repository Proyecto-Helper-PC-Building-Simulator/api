package es.bit.api.rest.service.componenttables;

import es.bit.api.persistence.model.componenttables.Cpu;
import es.bit.api.persistence.repository.jpa.componenttables.ICpuJPARepository;
import es.bit.api.rest.dto.componenttables.CpuDTO;
import es.bit.api.rest.mapper.componenttables.CpuMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CpuService {
    @Autowired
    ICpuJPARepository cpuJPARepository;

    public Long count() {
        return this.cpuJPARepository.count();
    }

    public List<CpuDTO> findAll(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Cpu> cpuPage = this.cpuJPARepository.findAll(pageRequest);

        return CpuMapper.toDTO(cpuPage.getContent());
    }

    public CpuDTO findById(Integer id) {
        Optional<Cpu> cpu = this.cpuJPARepository.findById(id);

        if (cpu.isEmpty()) {
            return null;
        }

        return CpuMapper.toDTO(cpu);
    }



    public CpuDTO create(CpuDTO cpuDTO) {
        Cpu cpu = CpuMapper.toBD(cpuDTO);
        cpu = this.cpuJPARepository.save(cpu);

        return CpuMapper.toDTO(cpu);
    }

    public void update(CpuDTO cpuDTO) {
        Cpu cpu = CpuMapper.toBD(cpuDTO);
        this.cpuJPARepository.save(cpu);

        CpuMapper.toDTO(cpu);
    }

    public void delete(CpuDTO cpuDTO) {
        Cpu cpu = CpuMapper.toBD(cpuDTO);
        this.cpuJPARepository.delete(cpu);
    }
}