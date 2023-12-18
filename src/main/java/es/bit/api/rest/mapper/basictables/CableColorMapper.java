package es.bit.api.rest.mapper.basictables;

import es.bit.api.persistence.model.basictables.CableColor;
import es.bit.api.rest.dto.basictables.CableColorDTO;
import es.bit.api.rest.mapper.componenttables.CableMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CableColorMapper {
    public static CableColorDTO toDTO(CableColor cableColor, Boolean withCables) {
        CableColorDTO cableColorDTO = new CableColorDTO();

        if (cableColor != null) {
            cableColorDTO.setId(cableColor.getId());
            cableColorDTO.setName(cableColor.getName());

            if (withCables) {
                cableColorDTO.setCables(CableMapper.toDTO(cableColor.getCables(), false));
            }
        }

        return cableColorDTO;
    }


    public static CableColorDTO toDTO(Optional<CableColor> cableColorOptional, Boolean withCables) {
        return cableColorOptional.map(cableColor -> toDTO(cableColor, withCables)).orElse(null);
    }


    public static List<CableColorDTO> toDTO(List<CableColor> cableColors, Boolean withCables) {
        List<CableColorDTO> cableColorsDTO = new ArrayList<>();

        if (cableColors == null) {
            return cableColorsDTO;
        }

        for (CableColor cableColor : cableColors) {
            cableColorsDTO.add(CableColorMapper.toDTO(cableColor, withCables));
        }

        return cableColorsDTO;
    }


    public static CableColor toBD(CableColorDTO cableColorDTO, Boolean withCables) {
        CableColor cableColor = new CableColor();

        if (cableColorDTO != null) {
            cableColor.setId(cableColorDTO.getId());
            cableColor.setName(cableColorDTO.getName());

            if (withCables) {
                cableColor.setCables(CableMapper.toBD(cableColorDTO.getCables(), false));
            }
        }

        return cableColor;
    }

    public static List<CableColor> toBD(List<CableColorDTO> cableColorsDTO, Boolean withCables) {
        List<CableColor> cableColors = new ArrayList<>();

        for (CableColorDTO cableColorDTO : cableColorsDTO) {
            cableColors.add(CableColorMapper.toBD(cableColorDTO, withCables));
        }

        return cableColors;
    }
}
