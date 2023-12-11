package es.bit.api.rest.mapper.componenttables;

import es.bit.api.persistence.model.componenttables.CpuCooler;
import es.bit.api.rest.dto.componenttables.CpuCoolerDTO;
import es.bit.api.rest.mapper.basictables.ComponentTypeMapper;
import es.bit.api.rest.mapper.basictables.LightingMapper;
import es.bit.api.rest.mapper.basictables.ManufacturerMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CpuCoolerMapper {
    public static CpuCoolerDTO toDTO(CpuCooler cpuCooler) {
        CpuCoolerDTO cpuCoolerDTO = new CpuCoolerDTO();
        cpuCoolerDTO.setComponentId(cpuCooler.getComponentId());
        cpuCoolerDTO.setName(cpuCooler.getName());
        cpuCoolerDTO.setPrice(cpuCooler.getPrice());
        cpuCoolerDTO.setManufacturerDTO(ManufacturerMapper.toDTO(cpuCooler.getManufacturer()));
        cpuCoolerDTO.setLightingDTO(LightingMapper.toDTO(cpuCooler.getLighting()));
        cpuCoolerDTO.setComponentTypeDTO(ComponentTypeMapper.toDTO(cpuCooler.getComponentType()));

        cpuCoolerDTO.setType(cpuCooler.getType());
        cpuCoolerDTO.setAirFlow(cpuCooler.getAirFlow());
        cpuCoolerDTO.setHeight(cpuCooler.getHeight());
        cpuCoolerDTO.setSize(cpuCooler.getSize());

        return cpuCoolerDTO;
    }

    public static CpuCoolerDTO toDTO(Optional<CpuCooler> cpuCoolerOptional) {
        return cpuCoolerOptional.map(CpuCoolerMapper::toDTO).orElse(null);
    }

    public static List<CpuCoolerDTO> toDTO(List<CpuCooler> cpuCoolers) {
        List<CpuCoolerDTO> cpuCoolersDTO = new ArrayList<>();

        if (cpuCoolers == null)
            return cpuCoolersDTO;

        for (CpuCooler cpuCooler : cpuCoolers) {
            cpuCoolersDTO.add(CpuCoolerMapper.toDTO(cpuCooler));
        }

        return cpuCoolersDTO;
    }

    public static CpuCooler toBD(CpuCoolerDTO cpuCoolerDTO) {
        CpuCooler cpuCooler = new CpuCooler();
        cpuCooler.setComponentId(cpuCoolerDTO.getComponentId());
        cpuCooler.setName(cpuCoolerDTO.getName());
        cpuCooler.setPrice(cpuCoolerDTO.getPrice());
        cpuCooler.setManufacturer(ManufacturerMapper.toBD(cpuCoolerDTO.getManufacturerDTO()));
        cpuCooler.setLighting(LightingMapper.toBD(cpuCoolerDTO.getLightingDTO()));
        cpuCooler.setComponentType(ComponentTypeMapper.toBD(cpuCoolerDTO.getComponentTypeDTO()));

        cpuCooler.setType(cpuCoolerDTO.getType());
        cpuCooler.setAirFlow(cpuCoolerDTO.getAirFlow());
        cpuCooler.setHeight(cpuCoolerDTO.getHeight());
        cpuCooler.setSize(cpuCoolerDTO.getSize());

        return cpuCooler;
    }
}
