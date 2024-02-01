package es.bit.api.rest.mapper.componenttables;

import es.bit.api.persistence.model.componenttables.Gpu;
import es.bit.api.rest.dto.componenttables.GpuDTO;
import es.bit.api.rest.mapper.basictables.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GpuMapper {
    public static GpuDTO toDTO(Gpu gpu) {
        GpuDTO gpuDTO = new GpuDTO();
        gpuDTO.setComponentId(gpu.getComponentId());
        gpuDTO.setName(gpu.getName());
        gpuDTO.setPrice(gpu.getPrice());
        gpuDTO.setManufacturerDTO(ManufacturerMapper.toDTO(gpu.getManufacturer()));
        gpuDTO.setLightingDTO(LightingMapper.toDTO(gpu.getLighting()));
        gpuDTO.setComponentTypeDTO(ComponentTypeMapper.toDTO(gpu.getComponentType()));

        gpuDTO.setVram(gpu.getVram());
        gpuDTO.setMemoryFrequency(gpu.getMemoryFrequency());
        gpuDTO.setCoreFrequency(gpu.getCoreFrequency());
        gpuDTO.setLength(gpu.getLength());
        gpuDTO.setWattage(gpu.getWattage());
        gpuDTO.setChipsetBrand(gpu.getChipsetBrand());
        gpuDTO.setGpuChipsetSerie(GpuChipsetSerieMapper.toDTO(gpu.getGpuChipsetSerie()));
        gpuDTO.setMultiGpuType(MultiGpuTypeMapper.toDTO(gpu.getMultiGpuType(), false));

        return gpuDTO;
    }

    public static GpuDTO toDTO(Optional<Gpu> gpuOptional) {
        return gpuOptional.map(GpuMapper::toDTO).orElse(null);
    }

    public static List<GpuDTO> toDTO(List<Gpu> gpus) {
        List<GpuDTO> gpusDTO = new ArrayList<>();

        if (gpus == null)
            return gpusDTO;

        for (Gpu gpu : gpus) {
            gpusDTO.add(GpuMapper.toDTO(gpu));
        }

        return gpusDTO;
    }

    public static Gpu toBD(GpuDTO gpuDTO) {
        Gpu gpu = new Gpu();
        gpu.setComponentId(gpuDTO.getComponentId());
        gpu.setName(gpuDTO.getName());
        gpu.setPrice(gpuDTO.getPrice());
        gpu.setManufacturer(ManufacturerMapper.toBD(gpuDTO.getManufacturerDTO()));
        gpu.setLighting(LightingMapper.toBD(gpuDTO.getLightingDTO()));
        gpu.setComponentType(ComponentTypeMapper.toBD(gpuDTO.getComponentTypeDTO()));

        gpu.setVram(gpuDTO.getVram());
        gpu.setMemoryFrequency(gpuDTO.getMemoryFrequency());
        gpu.setCoreFrequency(gpuDTO.getCoreFrequency());
        gpu.setLength(gpuDTO.getLength());
        gpu.setWattage(gpuDTO.getWattage());
        gpu.setChipsetBrand(gpuDTO.getChipsetBrand());
        gpu.setGpuChipsetSerie(GpuChipsetSerieMapper.toBD(gpuDTO.getGpuChipsetSerie()));
        gpu.setMultiGpuType(MultiGpuTypeMapper.toBD(gpuDTO.getMultiGpuType(), false));

        return gpu;
    }
}
