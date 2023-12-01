package es.bit.api.rest.service;

import es.bit.api.persistence.model.CpuSerie;
import es.bit.api.persistence.repository.jpa.ICpuSerieJPARepository;
import es.bit.api.rest.dto.CpuSerieDTO;
import es.bit.api.rest.mapper.CpuSerieMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CpuSerieService {
    @Autowired
    ICpuSerieJPARepository cpuSerieJPARepository;

    public Long count() {
        return this.cpuSerieJPARepository.count();
    }

    public List<CpuSerieDTO> findAll(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<CpuSerie> cpuSeriePage = this.cpuSerieJPARepository.findAll(pageRequest);

        return CpuSerieMapper.toDTO(cpuSeriePage.getContent());
    }

    public CpuSerieDTO findById(Integer id) {
        Optional<CpuSerie> cpuSerie = this.cpuSerieJPARepository.findById(id);

        if (cpuSerie.isEmpty()) {
            return null;
        }

        return CpuSerieMapper.toDTO(cpuSerie);
    }

    public CpuSerieDTO create(CpuSerieDTO cpuSerieDTO) {
        CpuSerie cpuSerie = CpuSerieMapper.toBD(cpuSerieDTO);
        cpuSerie = this.cpuSerieJPARepository.save(cpuSerie);

        return CpuSerieMapper.toDTO(cpuSerie);
    }

    public void update(CpuSerieDTO cpuSerieDTO) {
        CpuSerie cpuSerie = CpuSerieMapper.toBD(cpuSerieDTO);
        this.cpuSerieJPARepository.save(cpuSerie);

        CpuSerieMapper.toDTO(cpuSerie);
    }

    public void delete(CpuSerieDTO cpuSerieDTO) {
        CpuSerie cpuSerie = CpuSerieMapper.toBD(cpuSerieDTO);
        this.cpuSerieJPARepository.delete(cpuSerie);
    }
}
