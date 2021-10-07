package com.example.smartcity.service.impl;

import com.example.smartcity.entity.Crime;
import com.example.smartcity.entity.Prisoner;
import com.example.smartcity.repository.CrimeRepository;
import com.example.smartcity.repository.PrisonerRepository;
import com.example.smartcity.service.PrisonerService;
import com.example.smartcity.exception.RestException;
import com.example.smartcity.payload.ApiResponse;
import com.example.smartcity.payload.CitizenDTO;
import com.example.smartcity.payload.PrisonerDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PrisonerServiceImpl implements PrisonerService {

    @Autowired
    private PrisonerRepository prisonerRepository;

    @Autowired
    private CitizenExternalApiServiceImpl citizenExternalApiService;

    @Autowired
    private CrimeRepository crimeRepository;

    @Override
    public ApiResponse getAllArrestedPeople() {
        List<Prisoner> prisoners = prisonerRepository.findAll();
        return new ApiResponse("Success", true, prisoners);
    }

    @Override
    public ApiResponse getPrisonerById(UUID id) {
        Prisoner prisoner = prisonerRepository.findById(id)
                .orElseThrow(() -> new RestException("Prisoner not found", HttpStatus.NOT_FOUND));

        return new ApiResponse("Success", true, prisoner);
    }

    @Override
    public ApiResponse addPrisoner(PrisonerDTO prisonerDTO) {
        CitizenDTO citizenByCardNumber = citizenExternalApiService.getCitizenByCardNumber(prisonerDTO.getCardNumber());
        System.out.println(citizenByCardNumber);

        List<Crime> crimes=new ArrayList<>();

        for (UUID crime : prisonerDTO.getCrimes()) {
            Optional<Crime> byId = crimeRepository.findById(crime);
            if (byId.isPresent()) crimes.add(byId.get());
        }

        Prisoner prisoner=new Prisoner(
                prisonerDTO.getCardNumber(),
                citizenByCardNumber.getFirstName(),
                citizenByCardNumber.getSurname(),
                citizenByCardNumber.getBirthDate(),
                prisonerDTO.getPrisonDuration(),
                prisonerDTO.getStartingDate(),
                prisonerDTO.getEndingDate(),
                crimes
        );

        prisonerRepository.save(prisoner);
        return new ApiResponse("success",true);

    }

    @Override
    public ApiResponse editPrisoner(UUID id, PrisonerDTO prisonerDTO) {

        Prisoner prisoner = prisonerRepository.findById(id)
                .orElseThrow(()-> new RestException("Not found",HttpStatus.NOT_FOUND));

        Optional<Prisoner> byCardNumberAndIdNot = prisonerRepository.findByCardNumberAndIdNot(prisonerDTO.getCardNumber(), id);
        if (byCardNumberAndIdNot.isPresent()) throw  new RestException("Already added",HttpStatus.CONFLICT);

        CitizenDTO citizenByCardNumber = citizenExternalApiService.getCitizenByCardNumber(prisonerDTO.getCardNumber());
        System.out.println(citizenByCardNumber);

        List<Crime> crimes=new ArrayList<>();

        for (UUID crime : prisonerDTO.getCrimes()) {
            Optional<Crime> byId = crimeRepository.findById(crime);
            if (byId.isPresent()) crimes.add(byId.get());
        }

//        prisonerDTO.getCardNumber(),
//                citizenByCardNumber.getFirstName(),
//                citizenByCardNumber.getSurname(),
//                citizenByCardNumber.getBirthDate(),
//                prisonerDTO.getPrisonDuration(),
//                prisonerDTO.getStartingDate(),
//                prisonerDTO.getEndingDate(),
//                crimes

        prisoner.setCardNumber(prisonerDTO.getCardNumber());
        prisoner.setFirstName(citizenByCardNumber.getFirstName());
        prisoner.setSurname(citizenByCardNumber.getSurname());
        prisoner.setBirthDate(citizenByCardNumber.getBirthDate());
        prisoner.setPrisonDuration(prisonerDTO.getPrisonDuration());
        prisoner.setStartingDate(prisonerDTO.getStartingDate());
        prisoner.setEndingDate(prisonerDTO.getEndingDate());
        prisoner.setCrime(crimes);

        prisonerRepository.save(prisoner);
        return new ApiResponse("success",true);

    }

    @Override
    public ApiResponse deletePrisoner(UUID id) {
        try {
            prisonerRepository.deleteById(id);
            return new ApiResponse("Successfully deleted", true);
        } catch (Exception e) {
            throw new RestException("Prisoner Not found", HttpStatus.NOT_FOUND);
        }
    }
}
