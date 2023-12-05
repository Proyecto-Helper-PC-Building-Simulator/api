package es.bit.api.rest.mapper;

import es.bit.api.persistence.model.Lighting;
import es.bit.api.rest.dto.LightingDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LightingMapper {
    public static LightingDTO toDTO(Lighting lighting) {
        LightingDTO lightingDTO = new LightingDTO();

        if (lighting != null) {
            lightingDTO.setId(lighting.getId());
            lightingDTO.setName(lighting.getName());
        }

        return lightingDTO;
    }


    public static LightingDTO toDTO(Optional<Lighting> lightingOptional) {
        return lightingOptional.map(LightingMapper::toDTO).orElse(null);
    }


    public static List<LightingDTO> toDTO(List<Lighting> lightings) {
        List<LightingDTO> lightingsDTO = new ArrayList<>();

        if (lightings == null) {
            return lightingsDTO;
        }

        for (Lighting lighting : lightings) {
            lightingsDTO.add(LightingMapper.toDTO(lighting));
        }

        return lightingsDTO;
    }


    public static Lighting toBD(LightingDTO lightingDTO) {
        Lighting lighting = new Lighting();

        if (lightingDTO != null) {
            lighting.setId(lightingDTO.getId());
            lighting.setName(lightingDTO.getName());
        }

        return lighting;
    }
}
