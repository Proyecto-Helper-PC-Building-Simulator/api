package es.bit.api.rest.mapper.basictables;

import es.bit.api.persistence.model.basictables.CableType;
import es.bit.api.rest.dto.basictables.CableTypeDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CableTypeMapper {
    public static CableTypeDTO toDTO(CableType cableType) {
        CableTypeDTO cableTypeDTO = new CableTypeDTO();

        if (cableType != null) {
            cableTypeDTO.setId(cableType.getId());
            cableTypeDTO.setName(cableType.getName());
        }

        return cableTypeDTO;
    }


    public static CableTypeDTO toDTO(Optional<CableType> cableTypeOptional) {
        return cableTypeOptional.map(CableTypeMapper::toDTO).orElse(null);
    }


    public static List<CableTypeDTO> toDTO(List<CableType> cableTypes) {
        List<CableTypeDTO> cableTypesDTO = new ArrayList<>();

        if (cableTypes == null) {
            return cableTypesDTO;
        }

        for (CableType cableType : cableTypes) {
            cableTypesDTO.add(CableTypeMapper.toDTO(cableType));
        }

        return cableTypesDTO;
    }


    public static CableType toBD(CableTypeDTO cableTypeDTO) {
        CableType cableType = new CableType();

        if (cableTypeDTO != null) {
            cableType.setId(cableTypeDTO.getId());
            cableType.setName(cableTypeDTO.getName());
        }

        return cableType;
    }
}
