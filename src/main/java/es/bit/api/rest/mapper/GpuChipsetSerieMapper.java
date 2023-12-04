package es.bit.api.rest.mapper;

import es.bit.api.persistence.model.GpuChipsetSerie;
import es.bit.api.rest.dto.GpuChipsetSerieDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GpuChipsetSerieMapper {
    public static GpuChipsetSerieDTO toDTO(GpuChipsetSerie gpuChipsetSerie) {
        GpuChipsetSerieDTO gpuChipsetSerieDTO = new GpuChipsetSerieDTO();

        if (gpuChipsetSerie != null) {
            gpuChipsetSerieDTO.setId(gpuChipsetSerie.getId());
            gpuChipsetSerieDTO.setName(gpuChipsetSerie.getName());
        }

        return gpuChipsetSerieDTO;
    }


    public static GpuChipsetSerieDTO toDTO(Optional<GpuChipsetSerie> gpuChipsetSerieOptional) {
        return gpuChipsetSerieOptional.map(GpuChipsetSerieMapper::toDTO).orElse(null);
    }


    public static List<GpuChipsetSerieDTO> toDTO(List<GpuChipsetSerie> gpuChipsetSeries) {
        List<GpuChipsetSerieDTO> gpuChipsetSeriesDTO = new ArrayList<>();

        if (gpuChipsetSeries == null) {
            return gpuChipsetSeriesDTO;
        }

        for (GpuChipsetSerie gpuChipsetSerie : gpuChipsetSeries) {
            gpuChipsetSeriesDTO.add(GpuChipsetSerieMapper.toDTO(gpuChipsetSerie));
        }

        return gpuChipsetSeriesDTO;
    }


    public static GpuChipsetSerie toBD(GpuChipsetSerieDTO gpuChipsetSerieDTO) {
        GpuChipsetSerie gpuChipsetSerie = new GpuChipsetSerie();

        if (gpuChipsetSerieDTO != null) {
            gpuChipsetSerie.setId(gpuChipsetSerieDTO.getId());
            gpuChipsetSerie.setName(gpuChipsetSerieDTO.getName());
        }

        return gpuChipsetSerie;
    }
}
