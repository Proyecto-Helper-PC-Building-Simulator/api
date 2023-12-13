package es.bit.api.rest.mapper.componenttables;

import es.bit.api.persistence.model.componenttables.RamMemory;
import es.bit.api.rest.dto.componenttables.RamMemoryDTO;
import es.bit.api.rest.mapper.basictables.ComponentTypeMapper;
import es.bit.api.rest.mapper.basictables.LightingMapper;
import es.bit.api.rest.mapper.basictables.ManufacturerMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RamMemoryMapper {
    public static RamMemoryDTO toDTO(RamMemory ramMemory) {
        RamMemoryDTO ramMemoryDTO = new RamMemoryDTO();
        ramMemoryDTO.setComponentId(ramMemory.getComponentId());
        ramMemoryDTO.setName(ramMemory.getName());
        ramMemoryDTO.setPrice(ramMemory.getPrice());
        ramMemoryDTO.setManufacturerDTO(ManufacturerMapper.toDTO(ramMemory.getManufacturer()));
        ramMemoryDTO.setLightingDTO(LightingMapper.toDTO(ramMemory.getLighting()));
        ramMemoryDTO.setComponentTypeDTO(ComponentTypeMapper.toDTO(ramMemory.getComponentType()));

        ramMemoryDTO.setSize(ramMemory.getSize());
        ramMemoryDTO.setFrequency(ramMemory.getFrequency());

        return ramMemoryDTO;
    }

    public static RamMemoryDTO toDTO(Optional<RamMemory> ramMemoryOptional) {
        return ramMemoryOptional.map(RamMemoryMapper::toDTO).orElse(null);
    }

    public static List<RamMemoryDTO> toDTO(List<RamMemory> ramMemorys) {
        List<RamMemoryDTO> ramMemorysDTO = new ArrayList<>();

        if (ramMemorys == null)
            return ramMemorysDTO;

        for (RamMemory ramMemory : ramMemorys) {
            ramMemorysDTO.add(RamMemoryMapper.toDTO(ramMemory));
        }

        return ramMemorysDTO;
    }

    public static RamMemory toBD(RamMemoryDTO ramMemoryDTO) {
        RamMemory ramMemory = new RamMemory();
        ramMemory.setComponentId(ramMemoryDTO.getComponentId());
        ramMemory.setName(ramMemoryDTO.getName());
        ramMemory.setPrice(ramMemoryDTO.getPrice());
        ramMemory.setManufacturer(ManufacturerMapper.toBD(ramMemoryDTO.getManufacturerDTO()));
        ramMemory.setLighting(LightingMapper.toBD(ramMemoryDTO.getLightingDTO()));
        ramMemory.setComponentType(ComponentTypeMapper.toBD(ramMemoryDTO.getComponentTypeDTO()));

        ramMemory.setSize(ramMemoryDTO.getSize());
        ramMemory.setFrequency(ramMemoryDTO.getFrequency());

        return ramMemory;
    }
}
