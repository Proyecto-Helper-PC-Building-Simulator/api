package es.bit.api.rest.service.components.attributes;

import es.bit.api.persistence.model.components.attributes.MultiGpuType;
import es.bit.api.persistence.repository.jpa.components.attributes.IMultiGpuTypeJPARepository;
import es.bit.api.rest.dto.components.attributes.MultiGpuTypeDTO;
import es.bit.api.rest.mapper.components.attributes.MultiGpuTypeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
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

    @Cacheable(value = "multiGpuTypes", key = "#page + '-' + #size + '-' + #sortBy + '-' + #sortDir + '-' + #filters")
    public List<MultiGpuTypeDTO> findAll(int page, int size, Boolean withMotherboards) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<MultiGpuType> multiGpuTypePage = this.multiGpuTypeJPARepository.findAll(pageRequest);

        return MultiGpuTypeMapper.toDTO(multiGpuTypePage.getContent(), withMotherboards);
    }

    public MultiGpuTypeDTO findById(Integer id, Boolean withMotherboards) {
        Optional<MultiGpuType> multiGpuType = this.multiGpuTypeJPARepository.findById(id);

        if (multiGpuType.isEmpty()) {
            return null;
        }

        return MultiGpuTypeMapper.toDTO(multiGpuType, withMotherboards);
    }

    public MultiGpuTypeDTO create(MultiGpuTypeDTO multiGpuTypeDTO, Boolean withMotherboards) {
        MultiGpuType multiGpuType = MultiGpuTypeMapper.toBD(multiGpuTypeDTO, withMotherboards);
        multiGpuType = this.multiGpuTypeJPARepository.save(multiGpuType);

        return MultiGpuTypeMapper.toDTO(multiGpuType, withMotherboards);
    }

    public void update(MultiGpuTypeDTO multiGpuTypeDTO, Boolean withMotherboards) {
        MultiGpuType multiGpuType = MultiGpuTypeMapper.toBD(multiGpuTypeDTO, withMotherboards);
        this.multiGpuTypeJPARepository.save(multiGpuType);
    }

    public void delete(MultiGpuTypeDTO multiGpuTypeDTO, Boolean withMotherboards) {
        MultiGpuType multiGpuType = MultiGpuTypeMapper.toBD(multiGpuTypeDTO, withMotherboards);
        this.multiGpuTypeJPARepository.delete(multiGpuType);
    }
}
