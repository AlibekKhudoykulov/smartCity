package com.example.smartcity.service.impl;

import com.example.smartcity.entity.Crime;
import com.example.smartcity.entity.Witness;
import com.example.smartcity.payload.CitizenDTO;
import com.example.smartcity.repository.CrimeRepository;
import com.example.smartcity.repository.WitnessRepository;
import com.example.smartcity.service.WitnessService;
import com.example.smartcity.exception.RestException;
import com.example.smartcity.payload.ApiResponse;
import com.example.smartcity.payload.WitnessDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class WitnessServiceImpl implements WitnessService {

    private final WitnessRepository witnessRepository;
    private final CitizenExternalApiServiceImpl citizenExternalApiService;
    private final CrimeRepository crimeRepository;

    @Override
    public ResponseEntity<?> getAllWitnesses(Integer page) {
        Pageable pageableAndSortedByTime = PageRequest.of(page,10, Sort.by("createdAt").descending());
        Page<Witness> witnesses = witnessRepository.findAll(pageableAndSortedByTime);
        return ResponseEntity.ok().body(new ApiResponse("Success",true,witnesses));
    }

    @Override
    public ResponseEntity<?> getWitnessById(UUID id) {
        Witness witness = witnessRepository.findById(id)
                .orElseThrow(() -> new RestException("Witness not found", HttpStatus.NOT_FOUND));

        return ResponseEntity.ok().body(new ApiResponse("Success",true,witness));
    }

    @Override
    public ResponseEntity<?> addWitness(WitnessDTO witnessDTO) {
        boolean existsByCardNumber = witnessRepository.existsByCardNumber(witnessDTO.getCardNumber());
        if (existsByCardNumber) throw new RestException("This card number Already added",HttpStatus.CONFLICT);

        CitizenDTO citizenByCardNumber = citizenExternalApiService.getCitizenByCardNumber(witnessDTO.getCardNumber());

        List<Crime> crimeList = citizenExternalApiService.getCrimesWithId(witnessDTO.getCrimes());
        Witness witness=new Witness(
                witnessDTO.getCardNumber(),
                citizenByCardNumber.getFirstName(),
                citizenByCardNumber.getSurname(),
                citizenByCardNumber.getBirthDate(),
                witnessDTO.getPhoneNumber(),
                witnessDTO.getRemark(),
                citizenByCardNumber.getPhotoId(),
                crimeList
        );

        witnessRepository.save(witness);

        return ResponseEntity.ok().body(new ApiResponse("Successfully Added",true));
    }

    @Override
    public ResponseEntity<?> editWitness(UUID id, WitnessDTO witnessDTO) {
        Witness witness = witnessRepository.findById(id).
                orElseThrow(() -> new RestException("Not found", HttpStatus.NOT_FOUND));
        boolean byCardNumberAndIdNot = witnessRepository.existsByCardNumberAndIdNot(witnessDTO.getCardNumber(), id);
        if (byCardNumberAndIdNot) throw new RestException("This Card number already added",HttpStatus.CONFLICT);

        CitizenDTO citizenByCardNumber = citizenExternalApiService.getCitizenByCardNumber(witnessDTO.getCardNumber());
        List<Crime> crimeList = citizenExternalApiService.getCrimesWithId(witnessDTO.getCrimes());

        if (crimeList!=null){
            witness.setCrime(crimeList);
        }
        witness.setCardNumber(witnessDTO.getCardNumber());
        witness.setFirstName(citizenByCardNumber.getFirstName());
        witness.setSurname(citizenByCardNumber.getSurname());
        witness.setBirthDate(citizenByCardNumber.getBirthDate());
        witness.setPhotoId(citizenByCardNumber.getPhotoId());
        witness.setPhoneNumber(witnessDTO.getPhoneNumber());
        witness.setRemark(witnessDTO.getRemark());

        witnessRepository.save(witness);
        return ResponseEntity.ok().body(new ApiResponse("Witness Updated Successfully",true));

    }

    @Override
    public ResponseEntity<?> deleteWitness(UUID id) {
        try {
            witnessRepository.deleteById(id);
            return ResponseEntity.ok(new ApiResponse("Successfully deleted", true));
        } catch (Exception e) {
            throw new RestException("Witness Not found", HttpStatus.NOT_FOUND);
        }
    }
}
