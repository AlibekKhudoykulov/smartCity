package com.example.smartcity.service.impl;

import com.example.smartcity.entity.PoliceStation;
import com.example.smartcity.payload.responseDTO.PoliceStationResponseDTO;
import com.example.smartcity.repository.PoliceStationRepository;
import com.example.smartcity.service.Mapper.Mappers;
import com.example.smartcity.service.PoliceStationService;
import com.example.smartcity.exception.RestException;
import com.example.smartcity.payload.ApiResponse;
import com.example.smartcity.payload.PoliceStationDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PoliceStationServiceImpl implements PoliceStationService {

    private final PoliceStationRepository policeStationRepository;
    private final Mappers mappers;

    @Override
    public List<PoliceStationResponseDTO> getAllStations(Integer page) {
        Pageable pageableAndSortedByTime = PageRequest.of(page,10, Sort.by("createdAt").descending());
        Page<PoliceStation> policeStationList = policeStationRepository.findAll(pageableAndSortedByTime);

        List<PoliceStationResponseDTO> collect = policeStationList.getContent()
                .stream()
                .map(mappers::forPoliceStationResponseMapper)
                .collect(Collectors.toList());

        return collect;
    }

    @Override
    public PoliceStationResponseDTO getStationById(UUID id) {
        PoliceStation byId = policeStationRepository.findById(id)
                .orElseThrow(()->new RestException("Police Station Not found", HttpStatus.NOT_FOUND));

           return mappers.forPoliceStationResponseMapper(byId);
    }

    @Override
    public ApiResponse addStation(PoliceStationDTO policeStationDTO) {
        Optional<PoliceStation> byName = policeStationRepository.findByName(policeStationDTO.getName());
        if (byName.isPresent()) throw new RestException("Already exists this Police Station",HttpStatus.CONFLICT);

        PoliceStation policeStation=new PoliceStation(
                policeStationDTO.getName(),
                policeStationDTO.getAddress(),
                policeStationDTO.getPhoneNumber(),
                policeStationDTO.getRemark()
        );

        policeStationRepository.save(policeStation);

        return new ApiResponse("Police Station Saved Successfully",true);
    }

    @Override
    public ApiResponse editStation(UUID id, PoliceStationDTO policeStationDTO) {
        PoliceStation station = policeStationRepository.findById(id)
                .orElseThrow(() -> new RestException("Police Station Not found",HttpStatus.NOT_FOUND));
        station.setName(policeStationDTO.getName());
        station.setAddress(policeStationDTO.getAddress());
        station.setPhoneNumber(policeStationDTO.getPhoneNumber());
        station.setRemark(policeStationDTO.getRemark());

        policeStationRepository.save(station);

        return new ApiResponse("Edited Successfully",true);
    }

    @Override
    public ApiResponse deleteStation(UUID id) {
        try {
            policeStationRepository.deleteById(id);
            return new ApiResponse("Successfully deleted", true);
        } catch (Exception e) {
            throw new RestException("Police Station Not found", HttpStatus.NOT_FOUND);
        }
    }
}
