package es.bit.api.rest.mapper.componenttables;

import es.bit.api.persistence.model.componenttables.CaseFan;
import es.bit.api.rest.dto.componenttables.CaseFanDTO;
import es.bit.api.rest.mapper.basictables.ComponentTypeMapper;
import es.bit.api.rest.mapper.basictables.LightingMapper;
import es.bit.api.rest.mapper.basictables.ManufacturerMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CaseFanMapper {
    public static CaseFanDTO toDTO(CaseFan caseFan) {
        CaseFanDTO caseFanDTO = new CaseFanDTO();
        caseFanDTO.setComponentId(caseFan.getComponentId());
        caseFanDTO.setName(caseFan.getName());
        caseFanDTO.setPrice(caseFan.getPrice());
        caseFanDTO.setManufacturerDTO(ManufacturerMapper.toDTO(caseFan.getManufacturer()));
        caseFanDTO.setLightingDTO(LightingMapper.toDTO(caseFan.getLighting()));
        caseFanDTO.setComponentTypeDTO(ComponentTypeMapper.toDTO(caseFan.getComponentType()));
        caseFanDTO.setLevel(caseFan.getLevel());

        caseFanDTO.setSize(caseFan.getSize());
        caseFanDTO.setAirFlow(caseFan.getAirFlow());

        return caseFanDTO;
    }

    public static CaseFanDTO toDTO(Optional<CaseFan> caseFanOptional) {
        return caseFanOptional.map(CaseFanMapper::toDTO).orElse(null);
    }

    public static List<CaseFanDTO> toDTO(List<CaseFan> caseFans) {
        List<CaseFanDTO> caseFansDTO = new ArrayList<>();

        if (caseFans == null)
            return caseFansDTO;

        for (CaseFan caseFan : caseFans) {
            caseFansDTO.add(CaseFanMapper.toDTO(caseFan));
        }

        return caseFansDTO;
    }

    public static CaseFan toBD(CaseFanDTO caseFanDTO) {
        CaseFan caseFan = new CaseFan();
        caseFan.setComponentId(caseFanDTO.getComponentId());
        caseFan.setName(caseFanDTO.getName());
        caseFan.setPrice(caseFanDTO.getPrice());
        caseFan.setManufacturer(ManufacturerMapper.toBD(caseFanDTO.getManufacturerDTO()));
        caseFan.setLighting(LightingMapper.toBD(caseFanDTO.getLightingDTO()));
        caseFan.setComponentType(ComponentTypeMapper.toBD(caseFanDTO.getComponentTypeDTO()));
        caseFan.setLevel(caseFanDTO.getLevel());

        caseFan.setSize(caseFanDTO.getSize());
        caseFan.setAirFlow(caseFanDTO.getAirFlow());

        return caseFan;
    }
}
