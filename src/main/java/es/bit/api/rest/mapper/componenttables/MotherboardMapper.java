package es.bit.api.rest.mapper.componenttables;

import es.bit.api.persistence.model.componenttables.Motherboard;
import es.bit.api.rest.dto.componenttables.MotherboardDTO;
import es.bit.api.rest.mapper.basictables.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MotherboardMapper {
    public static MotherboardDTO toDTO(Motherboard motherboard) {
        MotherboardDTO motherboardDTO = new MotherboardDTO();
        motherboardDTO.setComponentId(motherboard.getComponentId());
        motherboardDTO.setName(motherboard.getName());
        motherboardDTO.setPrice(motherboard.getPrice());
        motherboardDTO.setManufacturerDTO(ManufacturerMapper.toDTO(motherboard.getManufacturer()));
        motherboardDTO.setLightingDTO(LightingMapper.toDTO(motherboard.getLighting()));
        motherboardDTO.setComponentTypeDTO(ComponentTypeMapper.toDTO(motherboard.getComponentType()));

        motherboardDTO.setMaxRamSpeed(motherboard.getMaxRamSpeed());
        motherboardDTO.setMotherboardChipset(MotherboardChipsetMapper.toDTO(motherboard.getMotherboardChipset()));
        motherboardDTO.setMotherboardFormFactor(MotherboardFormFactorMapper.toDTO(motherboard.getMotherboardFormFactor(), false));
        motherboardDTO.setCpuSocket(CpuSocketMapper.toDTO(motherboard.getCpuSocket(), false));

        return motherboardDTO;
    }

    public static MotherboardDTO toDTO(Optional<Motherboard> motherboardOptional) {
        return motherboardOptional.map(MotherboardMapper::toDTO).orElse(null);
    }

    public static List<MotherboardDTO> toDTO(List<Motherboard> motherboards) {
        List<MotherboardDTO> motherboardsDTO = new ArrayList<>();

        if (motherboards == null)
            return motherboardsDTO;

        for (Motherboard motherboard : motherboards) {
            motherboardsDTO.add(MotherboardMapper.toDTO(motherboard));
        }

        return motherboardsDTO;
    }

    public static Motherboard toBD(MotherboardDTO motherboardDTO) {
        Motherboard motherboard = new Motherboard();
        motherboard.setComponentId(motherboardDTO.getComponentId());
        motherboard.setName(motherboardDTO.getName());
        motherboard.setPrice(motherboardDTO.getPrice());
        motherboard.setManufacturer(ManufacturerMapper.toBD(motherboardDTO.getManufacturerDTO()));
        motherboard.setLighting(LightingMapper.toBD(motherboardDTO.getLightingDTO()));
        motherboard.setComponentType(ComponentTypeMapper.toBD(motherboardDTO.getComponentTypeDTO()));

        motherboard.setMaxRamSpeed(motherboardDTO.getMaxRamSpeed());
        motherboard.setMotherboardChipset(MotherboardChipsetMapper.toBD(motherboardDTO.getMotherboardChipset()));
        motherboard.setMotherboardFormFactor(MotherboardFormFactorMapper.toBD(motherboardDTO.getMotherboardFormFactor(), false));
        motherboard.setCpuSocket(CpuSocketMapper.toBD(motherboardDTO.getCpuSocket(), false));

        return motherboard;
    }
}
