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

    public List<CpuCoolerDTO> findAll(int page, int size, Boolean withCpuSockets) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<CpuCooler> cpuCoolerPage = this.cpuCoolerJPARepository.findAll(pageRequest);

        return CpuCoolerMapper.toDTO(cpuCoolerPage.getContent(), withCpuSockets);
    }

    public CpuCoolerDTO findById(Integer id, Boolean withCpuSockets) {
        Optional<CpuCooler> cpuCooler = this.cpuCoolerJPARepository.findById(id);

        if (cpuCooler.isEmpty()) {
            return null;
        }

        return CpuCoolerMapper.toDTO(cpuCooler, withCpuSockets);
    }



    public CpuCoolerDTO create(CpuCoolerDTO cpuCoolerDTO, Boolean withCpuSockets) {
        CpuCooler cpuCooler = CpuCoolerMapper.toBD(cpuCoolerDTO, withCpuSockets);
        cpuCooler = this.cpuCoolerJPARepository.save(cpuCooler);

        return CpuCoolerMapper.toDTO(cpuCooler, withCpuSockets);
    }

    public void update(CpuCoolerDTO cpuCoolerDTO, Boolean withCpuSockets) {
        CpuCooler cpuCooler = CpuCoolerMapper.toBD(cpuCoolerDTO, withCpuSockets);
        this.cpuCoolerJPARepository.save(cpuCooler);
    }

    public void delete(CpuCoolerDTO cpuCoolerDTO, Boolean withCpuSockets) {
        CpuCooler cpuCooler = CpuCoolerMapper.toBD(cpuCoolerDTO, withCpuSockets);
        this.cpuCoolerJPARepository.delete(cpuCooler);
    }
}