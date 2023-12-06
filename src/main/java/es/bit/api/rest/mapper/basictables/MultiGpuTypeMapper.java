package es.bit.api.rest.mapper.basictables;

import es.bit.api.persistence.model.basictables.MultiGpuType;
import es.bit.api.rest.dto.basictables.MultiGpuTypeDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MultiGpuTypeMapper {
    public static MultiGpuTypeDTO toDTO(MultiGpuType multiGpuType) {
        MultiGpuTypeDTO multiGpuTypeDTO = new MultiGpuTypeDTO();

        if (multiGpuType != null) {
            multiGpuTypeDTO.setId(multiGpuType.getId());
            multiGpuTypeDTO.setName(multiGpuType.getName());
        }

        return multiGpuTypeDTO;
    }


    public static MultiGpuTypeDTO toDTO(Optional<MultiGpuType> multiGpuTypeOptional) {
        return multiGpuTypeOptional.map(MultiGpuTypeMapper::toDTO).orElse(null);
    }


    public static List<MultiGpuTypeDTO> toDTO(List<MultiGpuType> multiGpuTypes) {
        List<MultiGpuTypeDTO> multiGpuTypesDTO = new ArrayList<>();

        if (multiGpuTypes == null) {
            return multiGpuTypesDTO;
        }

        for (MultiGpuType multiGpuType : multiGpuTypes) {
            multiGpuTypesDTO.add(MultiGpuTypeMapper.toDTO(multiGpuType));
        }

        return multiGpuTypesDTO;
    }


    public static MultiGpuType toBD(MultiGpuTypeDTO multiGpuTypeDTO) {
        MultiGpuType multiGpuType = new MultiGpuType();

        if (multiGpuTypeDTO != null) {
            multiGpuType.setId(multiGpuTypeDTO.getId());
            multiGpuType.setName(multiGpuTypeDTO.getName());
        }

        return multiGpuType;
    }
}
