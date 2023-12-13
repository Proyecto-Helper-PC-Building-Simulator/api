package es.bit.api.rest.mapper.basictables;

import es.bit.api.persistence.model.basictables.CpuSocket;
import es.bit.api.rest.dto.basictables.CpuSocketDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CpuSocketMapper {
    public static CpuSocketDTO toDTO(CpuSocket cpuSocket) {
        CpuSocketDTO cpuSocketDTO = new CpuSocketDTO();

        if (cpuSocket != null) {
            cpuSocketDTO.setId(cpuSocket.getId());
            cpuSocketDTO.setName(cpuSocket.getName());
        }

        return cpuSocketDTO;
    }


    public static CpuSocketDTO toDTO(Optional<CpuSocket> cpuSocketOptional) {
        return cpuSocketOptional.map(CpuSocketMapper::toDTO).orElse(null);
    }


    public static List<CpuSocketDTO> toDTO(List<CpuSocket> cpuSockets) {
        List<CpuSocketDTO> cpuSocketsDTO = new ArrayList<>();

        if (cpuSockets == null) {
            return cpuSocketsDTO;
        }

        for (CpuSocket cpuSocket : cpuSockets) {
            cpuSocketsDTO.add(CpuSocketMapper.toDTO(cpuSocket));
        }

        return cpuSocketsDTO;
    }


    public static CpuSocket toBD(CpuSocketDTO cpuSocketDTO) {
        CpuSocket cpuSocket = new CpuSocket();

        if (cpuSocketDTO != null) {
            cpuSocket.setId(cpuSocketDTO.getId());
            cpuSocket.setName(cpuSocketDTO.getName());
        }

        return cpuSocket;
    }
}
