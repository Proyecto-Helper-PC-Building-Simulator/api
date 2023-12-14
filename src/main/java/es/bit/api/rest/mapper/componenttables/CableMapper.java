package es.bit.api.rest.mapper.componenttables;

import es.bit.api.persistence.model.componenttables.Cable;
import es.bit.api.rest.dto.componenttables.CableDTO;
import es.bit.api.rest.mapper.basictables.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CableMapper {
    public static CableDTO toDTO(Cable cable, Boolean withCableColors) {
        CableDTO cableDTO = new CableDTO();
        cableDTO.setComponentId(cable.getComponentId());
        cableDTO.setName(cable.getName());
        cableDTO.setPrice(cable.getPrice());
        cableDTO.setManufacturerDTO(ManufacturerMapper.toDTO(cable.getManufacturer()));
        cableDTO.setLightingDTO(LightingMapper.toDTO(cable.getLighting()));
        cableDTO.setComponentTypeDTO(ComponentTypeMapper.toDTO(cable.getComponentType()));

        cableDTO.setCableType(CableTypeMapper.toDTO(cable.getCableType()));

        if (withCableColors) {
            cableDTO.setCableColors(CableColorMapper.toDTO(cable.getCableColors(), false));
        }

        return cableDTO;
    }

    public static CableDTO toDTO(Optional<Cable> cableOptional, Boolean withCableColors) {
        return cableOptional.map(cable -> toDTO(cable, withCableColors)).orElse(null);
    }

    public static List<CableDTO> toDTO(List<Cable> cables, Boolean withCableColors) {
        List<CableDTO> cablesDTO = new ArrayList<>();

        if (cables == null)
            return cablesDTO;

        for (Cable cable : cables) {
            cablesDTO.add(CableMapper.toDTO(cable, withCableColors));
        }

        return cablesDTO;
    }

    public static Cable toBD(CableDTO cableDTO, Boolean withCableColors) {
        Cable cable = new Cable();
        cable.setComponentId(cableDTO.getComponentId());
        cable.setName(cableDTO.getName());
        cable.setPrice(cableDTO.getPrice());
        cable.setManufacturer(ManufacturerMapper.toBD(cableDTO.getManufacturerDTO()));
        cable.setLighting(LightingMapper.toBD(cableDTO.getLightingDTO()));
        cable.setComponentType(ComponentTypeMapper.toBD(cableDTO.getComponentTypeDTO()));

        cable.setCableType(CableTypeMapper.toBD(cableDTO.getCableType()));

        if (withCableColors) {
            cable.setCableColors(CableColorMapper.toBD(cableDTO.getCableColors(), false));
        }

        return cable;
    }

    public static List<Cable> toBD(List<CableDTO> cableColorsDTO, Boolean withCableColors) {
        List<Cable> cableColors = new ArrayList<>();

        for (CableDTO cableColorDTO : cableColorsDTO) {
            cableColors.add(CableMapper.toBD(cableColorDTO, withCableColors));
        }

        return cableColors;
    }
}
