package es.bit.api.rest.mapper.componenttables;

import es.bit.api.persistence.model.componenttables.CpuCooler;
import es.bit.api.rest.dto.componenttables.CpuCoolerDTO;
import es.bit.api.rest.mapper.basictables.ComponentTypeMapper;
import es.bit.api.rest.mapper.basictables.CpuSocketMapper;
import es.bit.api.rest.mapper.basictables.LightingMapper;
import es.bit.api.rest.mapper.basictables.ManufacturerMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CpuCoolerMapper {
    public static CpuCoolerDTO toDTO(CpuCooler cpuCooler, Boolean withCpuSockets) {
        CpuCoolerDTO cpuCoolerDTO = new CpuCoolerDTO();
        cpuCoolerDTO.setComponentId(cpuCooler.getComponentId());
        cpuCoolerDTO.setName(cpuCooler.getName());
        cpuCoolerDTO.setPrice(cpuCooler.getPrice());
        cpuCoolerDTO.setManufacturerDTO(ManufacturerMapper.toDTO(cpuCooler.getManufacturer()));
        cpuCoolerDTO.setLightingDTO(LightingMapper.toDTO(cpuCooler.getLighting()));
        cpuCoolerDTO.setComponentTypeDTO(ComponentTypeMapper.toDTO(cpuCooler.getComponentType()));
        cpuCoolerDTO.setLevel(cpuCoolerDTO.getLevel());

        cpuCoolerDTO.setType(cpuCooler.getType());
        cpuCoolerDTO.setAirFlow(cpuCooler.getAirFlow());
        cpuCoolerDTO.setHeight(cpuCooler.getHeight());
        cpuCoolerDTO.setSize(cpuCooler.getSize());

        if (withCpuSockets) {
            cpuCoolerDTO.setCpuSockets(CpuSocketMapper.toDTO(cpuCooler.getCpuSockets(), false));
        }

        return cpuCoolerDTO;
    }

    public static CpuCoolerDTO toDTO(Optional<CpuCooler> cpuCoolerOptional, Boolean withCpuSockets) {
        return cpuCoolerOptional.map(cpuCooler -> toDTO(cpuCooler, withCpuSockets)).orElse(null);
    }

    public static List<CpuCoolerDTO> toDTO(List<CpuCooler> cpuCoolers, Boolean withCpuSockets) {
        List<CpuCoolerDTO> cpuCoolersDTO = new ArrayList<>();

        if (cpuCoolers == null)
            return cpuCoolersDTO;

        for (CpuCooler cpuCooler : cpuCoolers) {
            cpuCoolersDTO.add(CpuCoolerMapper.toDTO(cpuCooler, withCpuSockets));
        }

        return cpuCoolersDTO;
    }

    public static CpuCooler toBD(CpuCoolerDTO cpuCoolerDTO, Boolean withCpuSockets) {
        CpuCooler cpuCooler = new CpuCooler();
        cpuCooler.setComponentId(cpuCoolerDTO.getComponentId());
        cpuCooler.setName(cpuCoolerDTO.getName());
        cpuCooler.setPrice(cpuCoolerDTO.getPrice());
        cpuCooler.setManufacturer(ManufacturerMapper.toBD(cpuCoolerDTO.getManufacturerDTO()));
        cpuCooler.setLighting(LightingMapper.toBD(cpuCoolerDTO.getLightingDTO()));
        cpuCooler.setComponentType(ComponentTypeMapper.toBD(cpuCoolerDTO.getComponentTypeDTO()));
        cpuCooler.setLevel(cpuCoolerDTO.getLevel());

        cpuCooler.setType(cpuCoolerDTO.getType());
        cpuCooler.setAirFlow(cpuCoolerDTO.getAirFlow());
        cpuCooler.setHeight(cpuCoolerDTO.getHeight());
        cpuCooler.setSize(cpuCoolerDTO.getSize());

        if (withCpuSockets) {
            cpuCooler.setCpuSockets(CpuSocketMapper.toBD(cpuCoolerDTO.getCpuSockets(), false));
        }

        return cpuCooler;
    }

    public static List<CpuCooler> toBD(List<CpuCoolerDTO> cpuCoolersDTO, Boolean withCpuSockets) {
        List<CpuCooler> cpuCoolers = new ArrayList<>();

        for (CpuCoolerDTO cpuCoolerDTO : cpuCoolersDTO) {
            cpuCoolers.add(CpuCoolerMapper.toBD(cpuCoolerDTO, withCpuSockets));
        }

        return cpuCoolers;
    }
}
