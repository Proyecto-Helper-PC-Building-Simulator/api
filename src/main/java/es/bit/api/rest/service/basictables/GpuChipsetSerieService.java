package es.bit.api.rest.service.basictables;

import es.bit.api.persistence.model.basictables.GpuChipsetSerie;
import es.bit.api.persistence.repository.jpa.basictables.IGpuChipsetSerieJPARepository;
import es.bit.api.rest.dto.basictables.GpuChipsetSerieDTO;
import es.bit.api.rest.mapper.basictables.GpuChipsetSerieMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GpuChipsetSerieService {
    @Autowired
    IGpuChipsetSerieJPARepository gpuChipsetSerieJPARepository;

    public Long count() {
        return this.gpuChipsetSerieJPARepository.count();
    }

    public List<GpuChipsetSerieDTO> findAll(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<GpuChipsetSerie> gpuChipsetSeriePage = this.gpuChipsetSerieJPARepository.findAll(pageRequest);

        return GpuChipsetSerieMapper.toDTO(gpuChipsetSeriePage.getContent());
    }

    public GpuChipsetSerieDTO findById(Integer id) {
        Optional<GpuChipsetSerie> gpuChipsetSerie = this.gpuChipsetSerieJPARepository.findById(id);

        if (gpuChipsetSerie.isEmpty()) {
            return null;
        }

        return GpuChipsetSerieMapper.toDTO(gpuChipsetSerie);
    }

    public GpuChipsetSerieDTO create(GpuChipsetSerieDTO gpuChipsetSerieDTO) {
        GpuChipsetSerie gpuChipsetSerie = GpuChipsetSerieMapper.toBD(gpuChipsetSerieDTO);
        gpuChipsetSerie = this.gpuChipsetSerieJPARepository.save(gpuChipsetSerie);

        return GpuChipsetSerieMapper.toDTO(gpuChipsetSerie);
    }

    public void update(GpuChipsetSerieDTO gpuChipsetSerieDTO) {
        GpuChipsetSerie gpuChipsetSerie = GpuChipsetSerieMapper.toBD(gpuChipsetSerieDTO);
        this.gpuChipsetSerieJPARepository.save(gpuChipsetSerie);

        GpuChipsetSerieMapper.toDTO(gpuChipsetSerie);
    }

    public void delete(GpuChipsetSerieDTO gpuChipsetSerieDTO) {
        GpuChipsetSerie gpuChipsetSerie = GpuChipsetSerieMapper.toBD(gpuChipsetSerieDTO);
        this.gpuChipsetSerieJPARepository.delete(gpuChipsetSerie);
    }
}
