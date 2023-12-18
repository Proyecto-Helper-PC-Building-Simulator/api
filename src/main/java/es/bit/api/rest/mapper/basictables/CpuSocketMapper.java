package es.bit.api.rest.mapper.basictables;

import es.bit.api.persistence.model.basictables.CpuSocket;
import es.bit.api.rest.dto.basictables.CpuSocketDTO;
import es.bit.api.rest.mapper.componenttables.CpuCoolerMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CpuSocketMapper {
    public static CpuSocketDTO toDTO(CpuSocket cpuSocket, Boolean withCpuCoolers) {
        CpuSocketDTO cpuSocketDTO = new CpuSocketDTO();

        if (cpuSocket != null) {
            cpuSocketDTO.setId(cpuSocket.getId());
            cpuSocketDTO.setName(cpuSocket.getName());

            if (withCpuCoolers) {
                cpuSocketDTO.setCpuCoolers(CpuCoolerMapper.toDTO(cpuSocket.getCpuCoolers(), false));
            }
        }

        return cpuSocketDTO;
    }


    public static CpuSocketDTO toDTO(Optional<CpuSocket> cpuSocketOptional, Boolean withCpuCoolers) {
        return cpuSocketOptional.map(cpuSocket -> toDTO(cpuSocket, withCpuCoolers)).orElse(null);
    }


    public static List<CpuSocketDTO> toDTO(List<CpuSocket> cpuSockets, Boolean withCpuCoolers) {
        List<CpuSocketDTO> cpuSocketsDTO = new ArrayList<>();

        if (cpuSockets == null) {
            return cpuSocketsDTO;
        }

        for (CpuSocket cpuSocket : cpuSockets) {
            cpuSocketsDTO.add(CpuSocketMapper.toDTO(cpuSocket, withCpuCoolers));
        }

        return cpuSocketsDTO;
    }


    public static CpuSocket toBD(CpuSocketDTO cpuSocketDTO, Boolean withCpuCoolers) {
        CpuSocket cpuSocket = new CpuSocket();

        if (cpuSocketDTO != null) {
            cpuSocket.setId(cpuSocketDTO.getId());
            cpuSocket.setName(cpuSocketDTO.getName());

            if (withCpuCoolers) {
                cpuSocket.setCpuCoolers(CpuCoolerMapper.toBD(cpuSocketDTO.getCpuCoolers(), false));
            }
        }

        return cpuSocket;
    }

    public static List<CpuSocket> toBD(List<CpuSocketDTO> cpuSocketsDTO, Boolean withCpuCoolers) {
        List<CpuSocket> cpuSockets = new ArrayList<>();

        for (CpuSocketDTO cpuSocketDTO : cpuSocketsDTO) {
            cpuSockets.add(CpuSocketMapper.toBD(cpuSocketDTO, withCpuCoolers));
        }

        return cpuSockets;
    }
}
