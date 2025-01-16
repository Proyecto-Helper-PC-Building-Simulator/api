package es.bit.api.rest.mapper.components;

import es.bit.api.persistence.model.components.PowerSupply;
import es.bit.api.rest.dto.components.PowerSupplyDTO;
import es.bit.api.rest.mapper.components.attributes.ComponentTypeMapper;
import es.bit.api.rest.mapper.components.attributes.LightingMapper;
import es.bit.api.rest.mapper.components.attributes.ManufacturerMapper;
import es.bit.api.rest.mapper.components.attributes.PowerSupplyFormFactorMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PowerSupplyMapper {
    public static PowerSupplyDTO toDTO(PowerSupply powerSupply) {
        PowerSupplyDTO powerSupplyDTO = new PowerSupplyDTO();
        powerSupplyDTO.setComponentId(powerSupply.getComponentId());
        powerSupplyDTO.setName(powerSupply.getName());
        powerSupplyDTO.setPrice(powerSupply.getPrice());
        powerSupplyDTO.setManufacturerDTO(ManufacturerMapper.toDTO(powerSupply.getManufacturer()));
        powerSupplyDTO.setLightingDTO(LightingMapper.toDTO(powerSupply.getLighting()));
        powerSupplyDTO.setComponentTypeDTO(ComponentTypeMapper.toDTO(powerSupply.getComponentType()));
        powerSupplyDTO.setLevel(powerSupply.getLevel());

        powerSupplyDTO.setWattage(powerSupply.getWattage());
        powerSupplyDTO.setType(powerSupply.getType());
        powerSupplyDTO.setLength(powerSupply.getLength());
        powerSupplyDTO.setPowerSupplyFormFactor(PowerSupplyFormFactorMapper.toDTO(powerSupply.getPowerSupplyFormFactor(), false));

        return powerSupplyDTO;
    }

    public static PowerSupplyDTO toDTO(Optional<PowerSupply> powerSupplyOptional) {
        return powerSupplyOptional.map(PowerSupplyMapper::toDTO).orElse(null);
    }

    public static List<PowerSupplyDTO> toDTO(List<PowerSupply> powerSupplys) {
        List<PowerSupplyDTO> powerSupplysDTO = new ArrayList<>();

        if (powerSupplys == null)
            return powerSupplysDTO;

        for (PowerSupply powerSupply : powerSupplys) {
            powerSupplysDTO.add(PowerSupplyMapper.toDTO(powerSupply));
        }

        return powerSupplysDTO;
    }

    public static PowerSupply toBD(PowerSupplyDTO powerSupplyDTO) {
        PowerSupply powerSupply = new PowerSupply();
        powerSupply.setComponentId(powerSupplyDTO.getComponentId());
        powerSupply.setName(powerSupplyDTO.getName());
        powerSupply.setPrice(powerSupplyDTO.getPrice());
        powerSupply.setManufacturer(ManufacturerMapper.toBD(powerSupplyDTO.getManufacturerDTO()));
        powerSupply.setLighting(LightingMapper.toBD(powerSupplyDTO.getLightingDTO()));
        powerSupply.setComponentType(ComponentTypeMapper.toBD(powerSupplyDTO.getComponentTypeDTO()));
        powerSupply.setLevel(powerSupplyDTO.getLevel());

        powerSupply.setWattage(powerSupplyDTO.getWattage());
        powerSupply.setType(powerSupplyDTO.getType());
        powerSupply.setLength(powerSupplyDTO.getLength());
        powerSupply.setPowerSupplyFormFactor(PowerSupplyFormFactorMapper.toBD(powerSupplyDTO.getPowerSupplyFormFactor(), false));

        return powerSupply;
    }
}
