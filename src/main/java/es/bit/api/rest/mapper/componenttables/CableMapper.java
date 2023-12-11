package es.bit.api.rest.mapper.componenttables;

import es.bit.api.persistence.model.componenttables.Cable;
import es.bit.api.rest.dto.componenttables.CableDTO;
import es.bit.api.rest.mapper.basictables.CableTypeMapper;
import es.bit.api.rest.mapper.basictables.ComponentTypeMapper;
import es.bit.api.rest.mapper.basictables.LightingMapper;
import es.bit.api.rest.mapper.basictables.ManufacturerMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CableMapper {
    public static CableDTO toDTO(Cable cable) {
        CableDTO cableDTO = new CableDTO();
        cableDTO.setComponentId(cable.getComponentId());
        cableDTO.setName(cable.getName());
        cableDTO.setPrice(cable.getPrice());
        cableDTO.setManufacturerDTO(ManufacturerMapper.toDTO(cable.getManufacturer()));
        cableDTO.setLightingDTO(LightingMapper.toDTO(cable.getLighting()));
        cableDTO.setComponentTypeDTO(ComponentTypeMapper.toDTO(cable.getComponentType()));

        cableDTO.setCableType(CableTypeMapper.toDTO(cable.getCableType()));

        return cableDTO;
    }

    public static CableDTO toDTO(Optional<Cable> cableOptional) {
        return cableOptional.map(CableMapper::toDTO).orElse(null);
    }

    public static List<CableDTO> toDTO(List<Cable> cables) {
        List<CableDTO> cablesDTO = new ArrayList<>();

        if (cables == null)
            return cablesDTO;

        for (Cable cable : cables) {
            cablesDTO.add(CableMapper.toDTO(cable));
        }

        return cablesDTO;
    }

    public static Cable toBD(CableDTO cableDTO) {
        Cable cable = new Cable();
        cable.setComponentId(cableDTO.getComponentId());
        cable.setName(cableDTO.getName());
        cable.setPrice(cableDTO.getPrice());
        cable.setManufacturer(ManufacturerMapper.toBD(cableDTO.getManufacturerDTO()));
        cable.setLighting(LightingMapper.toBD(cableDTO.getLightingDTO()));
        cable.setComponentType(ComponentTypeMapper.toBD(cableDTO.getComponentTypeDTO()));

        cable.setCableType(CableTypeMapper.toBD(cableDTO.getCableType()));

        return cable;
    }
}
