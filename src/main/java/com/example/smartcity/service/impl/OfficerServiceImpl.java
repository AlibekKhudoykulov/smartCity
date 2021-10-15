package com.example.smartcity.service.impl;

import com.example.smartcity.entity.Officer;
import com.example.smartcity.entity.PoliceStation;
import com.example.smartcity.payload.CitizenDTO;
import com.example.smartcity.payload.UserDTO;
import com.example.smartcity.payload.responseDTO.OfficerResponseDTO;
import com.example.smartcity.repository.OfficerRepository;
import com.example.smartcity.repository.PoliceStationRepository;
import com.example.smartcity.repository.UserRepository;
import com.example.smartcity.service.Mapper.Mappers;
import com.example.smartcity.service.OfficerService;
import com.example.smartcity.exception.RestException;
import com.example.smartcity.payload.ApiResponse;
import com.example.smartcity.payload.OfficerDTO;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.mapper.Mapper;
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
public class OfficerServiceImpl implements OfficerService {

    private final OfficerRepository officerRepository;
    private final CitizenExternalApiServiceImpl citizenExternalApiService;
    private final PoliceStationRepository policeStationRepository;
    private final UserServiceImpl userService;
    private final UserRepository userRepository;
    private final Mappers mappers;
    @Override
    public List<OfficerResponseDTO> getAllOfficers(Integer page) {
        Pageable pageableAndSortedByTime = PageRequest.of(page,10, Sort.by("createdAt").descending());
        Page<Officer> officerList = officerRepository.findAll(pageableAndSortedByTime);

        List<OfficerResponseDTO> collect = officerList.getContent()
                .stream()
                .map(mappers::forOfficerResponseMapper)
                .collect(Collectors.toList());

        return collect;
    }

    @Override
    public OfficerResponseDTO getOfficerById(UUID id) {
        Officer officer = officerRepository.findById(id)
                .orElseThrow(() -> new RestException("Officer not found", HttpStatus.NOT_FOUND));
        return mappers.forOfficerResponseMapper(officer);
    }

    @Override
    public OfficerResponseDTO getOfficerByCardNumber(long cardNumber) {
        Officer officer = officerRepository.findByCardNumber(cardNumber)
                .orElseThrow(() -> new RestException("Officer not found", HttpStatus.NOT_FOUND));

        return mappers.forOfficerResponseMapper(officer);
    }

    @Override
    public ResponseEntity<?> addOfficer(OfficerDTO officerDTO) {
        Optional<Officer> byCardNumber = officerRepository.findByCardNumber(officerDTO.getCardNumber());
        if (byCardNumber.isPresent()) throw new RestException("This card number already added",HttpStatus.CONFLICT);
        CitizenDTO citizenByCardNumber = citizenExternalApiService.getCitizenByCardNumber(officerDTO.getCardNumber());

        PoliceStation  policeStation=null;
        if (officerDTO.getStationId()!=null) {
            Optional<PoliceStation> byId = policeStationRepository.findById(officerDTO.getStationId());
            if (byId.isPresent()){
                policeStation=byId.get();
            }
        }

        Officer officer=new Officer(
                officerDTO.getCardNumber(),
                citizenByCardNumber.getFirstName(),
                citizenByCardNumber.getSurname(),
                citizenByCardNumber.getBirthDate(),
                citizenExternalApiService.generateCertificate(),
                officerDTO.getRank(),
                citizenByCardNumber.getPhotoId(),
                policeStation
        );

        Officer save = officerRepository.save(officer);

        if (!userRepository.existsByCardNumber(officerDTO.getCardNumber())) {
            userService.addUser(new UserDTO(
                    officerDTO.getCardNumber(),
                    String.valueOf(officerDTO.getCardNumber()),
                    String.valueOf(officerDTO.getCardNumber()),
                    3
            ));
        }

        citizenExternalApiService.sendCheckingCertificate(save);
        return ResponseEntity.ok().body(new ApiResponse("Officer saved successfully and sent for checking certificate",true));
    }

    @Override
    public ResponseEntity<?> editOfficer(UUID id, OfficerDTO officerDTO) {
        Officer officer = officerRepository.findById(id)
                .orElseThrow(()-> new RestException("Officer not found",HttpStatus.NOT_FOUND));

        PoliceStation policeStation = null;
        if (officerDTO.getStationId()!=null) {
             policeStation = policeStationRepository.findById(officerDTO.getStationId()).
                    orElseThrow(()-> new RestException("Police station not found",HttpStatus.NOT_FOUND));
        }

        officer.setPoliceStation(policeStation);
        officer.setRank(officerDTO.getRank());

        officerRepository.save(officer);

        return ResponseEntity.ok().body(new ApiResponse("Officer updated successfully",true));
    }

    @Override
    public ResponseEntity<?> deleteOfficer(UUID id) {
        try {
            officerRepository.deleteById(id);
            return ResponseEntity.ok().body(new ApiResponse("Successfully deleted", true));

        } catch (Exception e) {
            throw new RestException("Officer Not found", HttpStatus.NOT_FOUND);
        }
    }

}
