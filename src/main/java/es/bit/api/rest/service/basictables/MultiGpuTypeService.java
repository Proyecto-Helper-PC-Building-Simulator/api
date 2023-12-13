package es.bit.api.rest.service.basictables;

import es.bit.api.persistence.model.basictables.MultiGpuType;
import es.bit.api.persistence.repository.jpa.basictables.IMultiGpuTypeJPARepository;
import es.bit.api.rest.dto.basictables.MultiGpuTypeDTO;
import es.bit.api.rest.mapper.basictables.MultiGpuTypeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MultiGpuTypeService {
    @Autowired
    IMultiGpuTypeJPARepository multiGpuTypeJPARepository;

    public Long count() {
        return this.multiGpuTypeJPARepository.count();
    }

    public List<MultiGpuTypeDTO> findAll(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<MultiGpuType> multiGpuTypePage = this.multiGpuTypeJPARepository.findAll(pageRequest);

        return MultiGpuTypeMapper.toDTO(multiGpuTypePage.getContent());
    }

    public MultiGpuTypeDTO findById(Integer id) {
        Optional<MultiGpuType> multiGpuType = this.multiGpuTypeJPARepository.findById(id);

        if (multiGpuType.isEmpty()) {
            return null;
        }

        return MultiGpuTypeMapper.toDTO(multiGpuType);
    }

    public MultiGpuTypeDTO create(MultiGpuTypeDTO multiGpuTypeDTO) {
        MultiGpuType multiGpuType = MultiGpuTypeMapper.toBD(multiGpuTypeDTO);
        multiGpuType = this.multiGpuTypeJPARepository.save(multiGpuType);

        return MultiGpuTypeMapper.toDTO(multiGpuType);
    }

    public void update(MultiGpuTypeDTO multiGpuTypeDTO) {
        MultiGpuType multiGpuType = MultiGpuTypeMapper.toBD(multiGpuTypeDTO);
        this.multiGpuTypeJPARepository.save(multiGpuType);

        MultiGpuTypeMapper.toDTO(multiGpuType);
    }

    public void delete(MultiGpuTypeDTO multiGpuTypeDTO) {
        MultiGpuType multiGpuType = MultiGpuTypeMapper.toBD(multiGpuTypeDTO);
        this.multiGpuTypeJPARepository.delete(multiGpuType);
    }
}
