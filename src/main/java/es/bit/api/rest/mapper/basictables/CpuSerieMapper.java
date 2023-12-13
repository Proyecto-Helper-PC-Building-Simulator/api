package es.bit.api.rest.mapper.basictables;

import es.bit.api.persistence.model.basictables.CpuSerie;
import es.bit.api.rest.dto.basictables.CpuSerieDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CpuSerieMapper {
    public static CpuSerieDTO toDTO(CpuSerie cpuSerie) {
        CpuSerieDTO cpuSerieDTO = new CpuSerieDTO();

        if (cpuSerie != null) {
            cpuSerieDTO.setId(cpuSerie.getId());
            cpuSerieDTO.setName(cpuSerie.getName());
        }

        return cpuSerieDTO;
    }


    public static CpuSerieDTO toDTO(Optional<CpuSerie> cpuSerieOptional) {
        return cpuSerieOptional.map(CpuSerieMapper::toDTO).orElse(null);
    }


    public static List<CpuSerieDTO> toDTO(List<CpuSerie> cpuSeries) {
        List<CpuSerieDTO> cpuSeriesDTO = new ArrayList<>();

        if (cpuSeries == null) {
            return cpuSeriesDTO;
        }

        for (CpuSerie cpuSerie : cpuSeries) {
            cpuSeriesDTO.add(CpuSerieMapper.toDTO(cpuSerie));
        }

        return cpuSeriesDTO;
    }


    public static CpuSerie toBD(CpuSerieDTO cpuSerieDTO) {
        CpuSerie cpuSerie = new CpuSerie();

        if (cpuSerieDTO != null) {
            cpuSerie.setId(cpuSerieDTO.getId());
            cpuSerie.setName(cpuSerieDTO.getName());
        }

        return cpuSerie;
    }
}
