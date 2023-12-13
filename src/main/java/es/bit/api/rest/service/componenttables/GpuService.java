package es.bit.api.rest.service.componenttables;

import es.bit.api.persistence.model.componenttables.Gpu;
import es.bit.api.persistence.repository.jpa.componenttables.IGpuJPARepository;
import es.bit.api.rest.dto.componenttables.GpuDTO;
import es.bit.api.rest.mapper.componenttables.GpuMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GpuService {
    @Autowired
    IGpuJPARepository gpuJPARepository;

    public Long count() {
        return this.gpuJPARepository.count();
    }

    public List<GpuDTO> findAll(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Gpu> gpuPage = this.gpuJPARepository.findAll(pageRequest);

        return GpuMapper.toDTO(gpuPage.getContent());
    }

    public GpuDTO findById(Integer id) {
        Optional<Gpu> gpu = this.gpuJPARepository.findById(id);

        if (gpu.isEmpty()) {
            return null;
        }

        return GpuMapper.toDTO(gpu);
    }



    public GpuDTO create(GpuDTO gpuDTO) {
        Gpu gpu = GpuMapper.toBD(gpuDTO);
        gpu = this.gpuJPARepository.save(gpu);

        return GpuMapper.toDTO(gpu);
    }

    public void update(GpuDTO gpuDTO) {
        Gpu gpu = GpuMapper.toBD(gpuDTO);
        this.gpuJPARepository.save(gpu);

        GpuMapper.toDTO(gpu);
    }

    public void delete(GpuDTO gpuDTO) {
        Gpu gpu = GpuMapper.toBD(gpuDTO);
        this.gpuJPARepository.delete(gpu);
    }
}