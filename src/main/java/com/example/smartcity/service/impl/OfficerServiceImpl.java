package com.example.smartcity.service.impl;

import com.example.smartcity.entity.Officer;
import com.example.smartcity.entity.PoliceStation;
import com.example.smartcity.payload.CitizenDTO;
import com.example.smartcity.payload.UserDTO;
import com.example.smartcity.repository.OfficerRepository;
import com.example.smartcity.repository.PoliceStationRepository;
import com.example.smartcity.repository.UserRepository;
import com.example.smartcity.service.OfficerService;
import com.example.smartcity.exception.RestException;
import com.example.smartcity.payload.ApiResponse;
import com.example.smartcity.payload.OfficerDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OfficerServiceImpl implements OfficerService {

    private final OfficerRepository officerRepository;
    private final CitizenExternalApiServiceImpl citizenExternalApiService;
    private final PoliceStationRepository policeStationRepository;
    private final UserServiceImpl userService;
    private final UserRepository userRepository;

    @Override
    public ApiResponse getAllOfficers() {
        List<Officer> officerList = officerRepository.findAll();
        return new ApiResponse("Officer list", true, officerList);
    }

    @Override
    public ApiResponse getOfficerById(UUID id) {
        Officer officer = officerRepository.findById(id)
                .orElseThrow(() -> new RestException("Officer not found", HttpStatus.NOT_FOUND));

        return new ApiResponse("success", true, officer);
    }

    @Override
    public ApiResponse getOfficerByCardNumber(long cardNumber) {
        Officer officer = officerRepository.findByCardNumber(cardNumber)
                .orElseThrow(() -> new RestException("Officer not found", HttpStatus.NOT_FOUND));

        return new ApiResponse("Success", true, officer);
    }

    @Override
    public ApiResponse addOfficer(OfficerDTO officerDTO) {
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
        return new ApiResponse("Officer saved successfully and sent for checking certificate",true);
    }

    @Override
    public ApiResponse editOfficer(UUID id, OfficerDTO officerDTO) {
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

        return new ApiResponse("Officer updated successfully",true);
    }

    @Override
    public ApiResponse deleteOfficer(UUID id) {
        try {
            officerRepository.deleteById(id);
            return new ApiResponse("Successfully deleted", true);

        } catch (Exception e) {
            throw new RestException("Officer Not found", HttpStatus.NOT_FOUND);
        }
    }

}
