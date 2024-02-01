package es.bit.api.rest.mapper.basictables;

import es.bit.api.persistence.model.basictables.MultiGpuType;
import es.bit.api.rest.dto.basictables.MultiGpuTypeDTO;
import es.bit.api.rest.mapper.componenttables.MotherboardMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MultiGpuTypeMapper {
    public static MultiGpuTypeDTO toDTO(MultiGpuType multiGpuType, Boolean withMotherboards) {
        MultiGpuTypeDTO multiGpuTypeDTO = new MultiGpuTypeDTO();

        if (multiGpuType != null) {
            multiGpuTypeDTO.setId(multiGpuType.getId());
            multiGpuTypeDTO.setName(multiGpuType.getName());

            if (withMotherboards) {
                multiGpuTypeDTO.setMotherboards(MotherboardMapper.toDTO(multiGpuType.getMotherboards(), false));
            }
        }

        return multiGpuTypeDTO;
    }


    public static MultiGpuTypeDTO toDTO(Optional<MultiGpuType> multiGpuTypeOptional, Boolean withMotherboards) {
        return multiGpuTypeOptional.map(multiGpuType -> toDTO(multiGpuType, withMotherboards)).orElse(null);
    }


    public static List<MultiGpuTypeDTO> toDTO(List<MultiGpuType> multiGpuTypes, Boolean withMotherboards) {
        List<MultiGpuTypeDTO> multiGpuTypesDTO = new ArrayList<>();

        if (multiGpuTypes == null) {
            return multiGpuTypesDTO;
        }

        for (MultiGpuType multiGpuType : multiGpuTypes) {
            multiGpuTypesDTO.add(MultiGpuTypeMapper.toDTO(multiGpuType, withMotherboards));
        }

        return multiGpuTypesDTO;
    }


    public static MultiGpuType toBD(MultiGpuTypeDTO multiGpuTypeDTO, Boolean withMotherboards) {
        MultiGpuType multiGpuType = new MultiGpuType();

        if (multiGpuTypeDTO != null) {
            multiGpuType.setId(multiGpuTypeDTO.getId());
            multiGpuType.setName(multiGpuTypeDTO.getName());

            if (withMotherboards) {
                multiGpuType.setMotherboards(MotherboardMapper.toBD(multiGpuTypeDTO.getMotherboards(), false));
            }
        }

        return multiGpuType;
    }

    public static List<MultiGpuType> toBD(List<MultiGpuTypeDTO> multiGpuTypesDTO, Boolean withMotherboards) {
        List<MultiGpuType> multiGpuTypes = new ArrayList<>();

        for (MultiGpuTypeDTO multiGpuTypeDTO : multiGpuTypesDTO) {
            multiGpuTypes.add(MultiGpuTypeMapper.toBD(multiGpuTypeDTO, withMotherboards));
        }

        return multiGpuTypes;
    }
}
