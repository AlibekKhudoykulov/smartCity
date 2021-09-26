package com.example.smartcity.Service.impl;

import com.example.smartcity.Entity.*;
import com.example.smartcity.Entity.Enums.CrimeReportStatus;
import com.example.smartcity.Entity.Enums.CrimeStatus;
import com.example.smartcity.Repository.*;
import com.example.smartcity.Service.CrimeService;
import com.example.smartcity.Service.PrisonerService;
import com.example.smartcity.Service.WitnessService;
import com.example.smartcity.exception.RestException;
import com.example.smartcity.payload.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class CrimeServiceImpl implements CrimeService {

    private CrimeRepository crimeRepository;
    private WitnessRepository witnessRepository;
    private VictimRepository victimRepository;
    private PrisonerRepository prisonerRepository;
    private PoliceStationRepository policeStationRepository;
    private OfficerRepository officerRepository;
    private WitnessServiceImpl witnessService;
    private VictimServiceImpl victimService;
    private PrisonerServiceImpl prisonerService;


    @Override
    public ApiResponse getAllCrimes() {
        List<Crime> allCrimes = crimeRepository.findAll();
        return new ApiResponse("Success", true, allCrimes);
    }

    @Override
    public ApiResponse getCrimeById(UUID id) {
        Crime byId = crimeRepository.findById(id)
                .orElseThrow(() -> new RestException("Crime not found", HttpStatus.NOT_FOUND));
        return new ApiResponse("Success", true, byId);
    }

    @Override
    public ApiResponse addCrime(CrimeDTO crimeDTO) {

        List<Witness> witnessList = getWitnessList(crimeDTO.getWitnessCardNumbers());
        List<Prisoner> prisonerList = getPrisonerList(crimeDTO.getPrisonerCardNumbers());
        List<Victim> victimList = getVictimList(crimeDTO.getVictimCardNumbers());
        List<Officer> officerList = getOfficerList(crimeDTO.getOfficerCardNumbers());
        PoliceStation policeStation = checkPoliceStation(crimeDTO.getPoliceStationId());

        Crime crime=new Crime(
                crimeDTO.getName(),
                crimeDTO.getAddress(),
                LocalDateTime.now(),
                crimeDTO.getCrimeDescription(),
                witnessList,
                victimList,
                prisonerList,
                officerList,
                policeStation,
                CrimeReportStatus.PENDING,
                crimeDTO.getCrimeStatus(),
                crimeDTO.getCrimeType()
        );
        crimeRepository.save(crime);
        return new ApiResponse("Saved successfully",true);
    }

    @Override
    public ApiResponse editCrime(UUID id, CrimeDTO crimeDTO) {
        return null;
    }

    @Override
    public ApiResponse deleteCrime(UUID id) {
        try {
            crimeRepository.deleteById(id);
            return new ApiResponse("Successfully deleted", true);
        } catch (Exception e) {
            throw new RestException("Crime Not found", HttpStatus.NOT_FOUND);
        }
    }

    public List<Witness> getWitnessList(List<Long> witnessCardNumbers) {
        List<Witness> witnesses = new ArrayList<>();
        for (Long witnessCardNumber : witnessCardNumbers) {
            Optional<Witness> byCardNumber = witnessRepository.findByCardNumber(witnessCardNumber);
            if (!byCardNumber.isPresent()) {
                witnessService.addWitness(new WitnessDTO(witnessCardNumber));
                Optional<Witness> addedWitness = witnessRepository.findByCardNumber(witnessCardNumber);
                witnesses.add(addedWitness.get());
            }
            witnesses.add(byCardNumber.get());
        }
        return witnesses;
    }

    public List<Victim> getVictimList(List<Long> victimCardNumbers) {
        List<Victim> victims = new ArrayList<>();
        for (Long victimCardNumber : victimCardNumbers) {
            Optional<Victim> byCardNumber = victimRepository.findByCardNumber(victimCardNumber);
            if (!byCardNumber.isPresent()) {
                victimService.addVictim(new VictimDTO(victimCardNumber));
                Optional<Victim> addedVictim = victimRepository.findByCardNumber(victimCardNumber);
                victims.add(addedVictim.get());
            }
            victims.add(byCardNumber.get());
        }
        return victims;
    }

    public List<Prisoner> getPrisonerList(List<Long> prisonerCardNumbers) {
        List<Prisoner> prisoners = new ArrayList<>();
        for (Long prisonCardNumber : prisonerCardNumbers) {
            Optional<Prisoner> byCardNumber = prisonerRepository.findByCardNumber(prisonCardNumber);
            if (!byCardNumber.isPresent()) {
                prisonerService.addPrisoner(new PrisonerDTO(prisonCardNumber));
                Optional<Prisoner> addedPrisoner = prisonerRepository.findByCardNumber(prisonCardNumber);
                prisoners.add(addedPrisoner.get());
            }
            prisoners.add(byCardNumber.get());
        }
        return prisoners;
    }

    public List<Officer> getOfficerList(List<Long> officerCardNumbers) {
        List<Officer> officerList = new ArrayList<>();
        for (Long officerCardNumber : officerCardNumbers) {
            Optional<Officer> byCardNumber = officerRepository.findByCardNumber(officerCardNumber);
            if (byCardNumber.isPresent()) officerList.add(byCardNumber.get());
        }
        return officerList;
    }

    public PoliceStation checkPoliceStation(UUID id){
        Optional<PoliceStation> byId = policeStationRepository.findById(id);
        if (!byId.isPresent()) return null;
        PoliceStation policeStation = byId.get();
        return policeStation;
    }

}
