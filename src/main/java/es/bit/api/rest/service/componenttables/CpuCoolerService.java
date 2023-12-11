package es.bit.api.rest.service.componenttables;

import es.bit.api.persistence.model.componenttables.CpuCooler;
import es.bit.api.persistence.repository.jpa.componenttables.ICpuCoolerJPARepository;
import es.bit.api.rest.dto.componenttables.CpuCoolerDTO;
import es.bit.api.rest.mapper.componenttables.CpuCoolerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CpuCoolerService {
    @Autowired
    ICpuCoolerJPARepository cpuCoolerJPARepository;

    public Long count() {
        return this.cpuCoolerJPARepository.count();
    }

    public List<CpuCoolerDTO> findAll(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<CpuCooler> cpuCoolerPage = this.cpuCoolerJPARepository.findAll(pageRequest);

        return CpuCoolerMapper.toDTO(cpuCoolerPage.getContent());
    }

    public CpuCoolerDTO findById(Integer id) {
        Optional<CpuCooler> cpuCooler = this.cpuCoolerJPARepository.findById(id);

        if (cpuCooler.isEmpty()) {
            return null;
        }

        return CpuCoolerMapper.toDTO(cpuCooler);
    }



    public CpuCoolerDTO create(CpuCoolerDTO cpuCoolerDTO) {
        CpuCooler cpuCooler = CpuCoolerMapper.toBD(cpuCoolerDTO);
        cpuCooler = this.cpuCoolerJPARepository.save(cpuCooler);

        return CpuCoolerMapper.toDTO(cpuCooler);
    }

    public void update(CpuCoolerDTO cpuCoolerDTO) {
        CpuCooler cpuCooler = CpuCoolerMapper.toBD(cpuCoolerDTO);
        this.cpuCoolerJPARepository.save(cpuCooler);

        CpuCoolerMapper.toDTO(cpuCooler);
    }

    public void delete(CpuCoolerDTO cpuCoolerDTO) {
        CpuCooler cpuCooler = CpuCoolerMapper.toBD(cpuCoolerDTO);
        this.cpuCoolerJPARepository.delete(cpuCooler);
    }
}