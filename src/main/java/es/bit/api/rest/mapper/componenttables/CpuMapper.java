package es.bit.api.rest.mapper.componenttables;

import es.bit.api.persistence.model.componenttables.Cpu;
import es.bit.api.rest.dto.componenttables.CpuDTO;
import es.bit.api.rest.mapper.basictables.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CpuMapper {
    public static CpuDTO toDTO(Cpu cpu) {
        CpuDTO cpuDTO = new CpuDTO();
        cpuDTO.setComponentId(cpu.getComponentId());
        cpuDTO.setName(cpu.getName());
        cpuDTO.setPrice(cpu.getPrice());
        cpuDTO.setManufacturerDTO(ManufacturerMapper.toDTO(cpu.getManufacturer()));
        cpuDTO.setLightingDTO(LightingMapper.toDTO(cpu.getLighting()));
        cpuDTO.setComponentTypeDTO(ComponentTypeMapper.toDTO(cpu.getComponentType()));
        cpuDTO.setLevel(cpu.getLevel());

        cpuDTO.setFrequency(cpu.getFrequency());
        cpuDTO.setCores(cpu.getCores());
        cpuDTO.setWattage(cpu.getWattage());
        cpuDTO.setCpuSocket(CpuSocketMapper.toDTO(cpu.getCpuSocket(), false));
        cpuDTO.setCpuSerie(CpuSerieMapper.toDTO(cpu.getCpuSerie()));

        return cpuDTO;
    }

    public static CpuDTO toDTO(Optional<Cpu> cpuOptional) {
        return cpuOptional.map(CpuMapper::toDTO).orElse(null);
    }

    public static List<CpuDTO> toDTO(List<Cpu> cpus) {
        List<CpuDTO> cpusDTO = new ArrayList<>();

        if (cpus == null)
            return cpusDTO;

        for (Cpu cpu : cpus) {
            cpusDTO.add(CpuMapper.toDTO(cpu));
        }

        return cpusDTO;
    }

    public static Cpu toBD(CpuDTO cpuDTO) {
        Cpu cpu = new Cpu();
        cpu.setComponentId(cpuDTO.getComponentId());
        cpu.setName(cpuDTO.getName());
        cpu.setPrice(cpuDTO.getPrice());
        cpu.setManufacturer(ManufacturerMapper.toBD(cpuDTO.getManufacturerDTO()));
        cpu.setLighting(LightingMapper.toBD(cpuDTO.getLightingDTO()));
        cpu.setComponentType(ComponentTypeMapper.toBD(cpuDTO.getComponentTypeDTO()));
        cpu.setLevel(cpuDTO.getLevel());

        cpu.setFrequency(cpuDTO.getFrequency());
        cpu.setCores(cpuDTO.getCores());
        cpu.setWattage(cpuDTO.getWattage());
        cpu.setCpuSocket(CpuSocketMapper.toBD(cpuDTO.getCpuSocket(), false));
        cpu.setCpuSerie(CpuSerieMapper.toBD(cpuDTO.getCpuSerie()));

        return cpu;
    }
}
