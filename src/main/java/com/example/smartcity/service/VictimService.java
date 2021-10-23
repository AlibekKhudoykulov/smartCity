package com.example.smartcity.service;

import com.example.smartcity.payload.ApiResponse;
import com.example.smartcity.payload.VictimDTO;
import com.example.smartcity.payload.responseDTO.CustomPage;
import com.example.smartcity.payload.responseDTO.VictimResponseDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

/**
 * @author Alibek Khudoykulov
 */
public interface VictimService {

    CustomPage<VictimResponseDTO> getAllVictims(Integer page);

    VictimResponseDTO getVictimById(UUID id);

    ApiResponse addVictim(VictimDTO victimDTO);

    ApiResponse editVictim(UUID id,VictimDTO victimDTO);

    ApiResponse deleteVictim(UUID id);
}
