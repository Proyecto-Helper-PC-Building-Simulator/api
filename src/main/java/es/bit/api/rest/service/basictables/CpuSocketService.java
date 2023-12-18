package es.bit.api.rest.service.basictables;

import es.bit.api.persistence.model.basictables.CpuSocket;
import es.bit.api.persistence.repository.jpa.basictables.ICpuSocketJPARepository;
import es.bit.api.rest.dto.basictables.CpuSocketDTO;
import es.bit.api.rest.mapper.basictables.CpuSocketMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CpuSocketService {
    @Autowired
    ICpuSocketJPARepository cpuSocketJPARepository;

    public Long count() {
        return this.cpuSocketJPARepository.count();
    }

    public List<CpuSocketDTO> findAll(int page, int size, Boolean withCpuCoolers) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<CpuSocket> cpuSocketPage = this.cpuSocketJPARepository.findAll(pageRequest);

        return CpuSocketMapper.toDTO(cpuSocketPage.getContent(), withCpuCoolers);
    }

    public CpuSocketDTO findById(Integer id, Boolean withCpuCoolers) {
        Optional<CpuSocket> cpuSocket = this.cpuSocketJPARepository.findById(id);

        if (cpuSocket.isEmpty()) {
            return null;
        }

        return CpuSocketMapper.toDTO(cpuSocket, withCpuCoolers);
    }

    public CpuSocketDTO create(CpuSocketDTO cpuSocketDTO, Boolean withCpuCoolers) {
        CpuSocket cpuSocket = CpuSocketMapper.toBD(cpuSocketDTO, withCpuCoolers);
        cpuSocket = this.cpuSocketJPARepository.save(cpuSocket);

        return CpuSocketMapper.toDTO(cpuSocket, withCpuCoolers);
    }

    public void update(CpuSocketDTO cpuSocketDTO, Boolean withCpuCoolers) {
        CpuSocket cpuSocket = CpuSocketMapper.toBD(cpuSocketDTO, withCpuCoolers);
        this.cpuSocketJPARepository.save(cpuSocket);
    }

    public void delete(CpuSocketDTO cpuSocketDTO, Boolean withCpuCoolers) {
        CpuSocket cpuSocket = CpuSocketMapper.toBD(cpuSocketDTO, withCpuCoolers);
        this.cpuSocketJPARepository.delete(cpuSocket);
    }
}
