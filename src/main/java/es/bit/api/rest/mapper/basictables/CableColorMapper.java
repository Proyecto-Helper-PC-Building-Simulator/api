package es.bit.api.rest.mapper.basictables;

import es.bit.api.persistence.model.basictables.CableColor;
import es.bit.api.rest.dto.basictables.CableColorDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CableColorMapper {
    public static CableColorDTO toDTO(CableColor cableColor) {
        CableColorDTO cableColorDTO = new CableColorDTO();

        if (cableColor != null) {
            cableColorDTO.setId(cableColor.getId());
            cableColorDTO.setName(cableColor.getName());
        }

        return cableColorDTO;
    }


    public static CableColorDTO toDTO(Optional<CableColor> cableColorOptional) {
        return cableColorOptional.map(CableColorMapper::toDTO).orElse(null);
    }


    public static List<CableColorDTO> toDTO(List<CableColor> cableColors) {
        List<CableColorDTO> cableColorsDTO = new ArrayList<>();

        if (cableColors == null) {
            return cableColorsDTO;
        }

        for (CableColor cableColor : cableColors) {
            cableColorsDTO.add(CableColorMapper.toDTO(cableColor));
        }

        return cableColorsDTO;
    }


    public static CableColor toBD(CableColorDTO cableColorDTO) {
        CableColor cableColor = new CableColor();

        if (cableColorDTO != null) {
            cableColor.setId(cableColorDTO.getId());
            cableColor.setName(cableColorDTO.getName());
        }

        return cableColor;
    }
}
