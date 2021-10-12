package com.example.smartcity.service.impl;

import com.example.smartcity.entity.MorgueRequest;
import com.example.smartcity.entity.Officer;
import com.example.smartcity.entity.User;
import com.example.smartcity.entity.enums.OfficerRank;
import com.example.smartcity.exception.RestException;
import com.example.smartcity.payload.ApiResponse;
import com.example.smartcity.payload.PoliceStationDTO;
import com.example.smartcity.payload.VictimDTO;
import com.example.smartcity.payload.requestDTO.CityManagementRequestDTO;
import com.example.smartcity.payload.requestDTO.MorgueRequestDTO;
import com.example.smartcity.payload.responseDTO.OfficerResponseDTO;
import com.example.smartcity.repository.MorgueRequestRepository;
import com.example.smartcity.repository.OfficerRepository;
import com.example.smartcity.repository.UserRepository;
import com.example.smartcity.repository.VictimRepository;
import com.example.smartcity.service.ReceiveRequestAPIService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReceiveRequestAPIServiceImpl implements ReceiveRequestAPIService {

    private final OfficerRepository officerRepository;
    private final UserRepository userRepository;
    private final MorgueRequestRepository morgueRequestRepository;
    private final VictimServiceImpl victimService;
    private final CitizenExternalApiServiceImpl citizenExternalApiService;


    @Override
    @Transactional
    public ResponseEntity<?> getCheckingCertificate(CityManagementRequestDTO cityManagementRequestDTO) {
        Officer officer = officerRepository.findById(cityManagementRequestDTO.getOfficerId())
                .orElseThrow(() -> new RestException("Officer not found", HttpStatus.NOT_FOUND));

        if (cityManagementRequestDTO.isConfirmed()) {
            User user = userRepository.findByCardNumber(officer.getCardNumber())
                    .orElseThrow(() -> new RestException("User not found", HttpStatus.NOT_FOUND));
            user.setEnabled(true);
        } else {
            officerRepository.delete(officer);
            User user = userRepository.findByCardNumber(officer.getCardNumber())
                    .orElseThrow(() -> new RestException("User not found", HttpStatus.NOT_FOUND));
            userRepository.delete(user);
        }
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(new ApiResponse("Success", true));
    }

    @Override
    public ResponseEntity<?> askPoliceOfficerForMorgue(MorgueRequestDTO morgueRequestDTO) {

            MorgueRequest morgueRequest = new MorgueRequest();
            morgueRequest.setCorpseCardNumber(morgueRequestDTO.getCorpseCardNumber());
            for (Officer officer : officerRepository.findAllByRank(OfficerRank.EXPERT)) {
                Optional<MorgueRequest> byOfficerCardNumber = morgueRequestRepository.findByOfficer(officer);
                if (!byOfficerCardNumber.isPresent()) {
                    OfficerResponseDTO officerResponseDTO = citizenExternalApiService.sendOfficer(officer);
                    morgueRequest.setOfficer(officer);
                    morgueRequest.setOfficerCardNumber(officer.getCardNumber());
                    morgueRequestRepository.save(morgueRequest);
                    return ResponseEntity.status(200).body(officerResponseDTO);
                } else {
                    boolean endExamination = byOfficerCardNumber.get().isEndExamination();
                    if (endExamination) {
                        OfficerResponseDTO officerResponseDTO = citizenExternalApiService.sendOfficer(officer);
                        morgueRequest.setOfficer(officer);
                        morgueRequest.setOfficerCardNumber(officer.getCardNumber());
                        morgueRequestRepository.save(morgueRequest);
                        return ResponseEntity.status(200).body(officerResponseDTO);
                    }
                }
            }

        ApiResponse apiResponse=new ApiResponse("All officers are busy",false);
        return ResponseEntity.status(apiResponse.isSuccess()?HttpStatus.OK:HttpStatus.NOT_FOUND).body(apiResponse);
    }

    @Override
    public ResponseEntity<?> getRequestToBlockAccountFromMorgue(MorgueRequestDTO morgueRequestDTO) {
        MorgueRequest morgueRequest = morgueRequestRepository.findByCorpseCardNumber(morgueRequestDTO.getCorpseCardNumber())
                .orElseThrow(() -> new RestException("This corpse not found", HttpStatus.NOT_FOUND));

        morgueRequest.setDeathDate(morgueRequest.getDeathDate());
        morgueRequest.setCauses(morgueRequestDTO.getCause());
        morgueRequest.setEndExamination(true);
        MorgueRequest save = morgueRequestRepository.save(morgueRequest);

        VictimDTO victimDTO = new VictimDTO(
                save.getCorpseCardNumber(),
                save.getDeathDate(),
                save.getCauses()
        );

        ResponseEntity<?> responseEntity = victimService.addVictim(victimDTO);
        return ResponseEntity.status(responseEntity.getStatusCode().equals(HttpStatus.OK) ? HttpStatus.OK : HttpStatus.CONFLICT).
                body(responseEntity.getBody());
    }
}
