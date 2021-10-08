package com.example.smartcity.service.impl;

import com.example.smartcity.entity.*;
import com.example.smartcity.entity.enums.CrimeReportStatus;
import com.example.smartcity.repository.*;
import com.example.smartcity.service.CrimeService;
import com.example.smartcity.exception.RestException;
import com.example.smartcity.payload.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class CrimeServiceImpl implements CrimeService {

    private final CrimeRepository crimeRepository;
    private final PoliceStationRepository policeStationRepository;
    private final OfficerRepository officerRepository;


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

        List<Officer> list=new ArrayList<>();
        if (crimeDTO.getOfficers()!=null) {
            List<Officer> officerList = getOfficerList(crimeDTO.getOfficers());
            officerList.addAll(list);
        }

        PoliceStation policeStation = null;
        if (crimeDTO.getPoliceStationId()!=null) {
            policeStation = checkPoliceStation(crimeDTO.getPoliceStationId());
        }

        Crime crime=new Crime(
                crimeDTO.getName(),
                crimeDTO.getAddress(),
                LocalDateTime.now(),
                crimeDTO.getCrimeDescription(),
                list,
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
        Crime crime = crimeRepository.findById(id)
                .orElseThrow(() -> new RestException("Not Found",HttpStatus.NOT_FOUND));

        List<Officer> officerList = getOfficerList(crimeDTO.getOfficers());
        PoliceStation policeStation = checkPoliceStation(crimeDTO.getPoliceStationId());

        crime.setName(crimeDTO.getName());
        crime.setAddress(crime.getAddress());
        crime.setCrimeDescription(crimeDTO.getCrimeDescription());
        crime.setOfficers(officerList);
        crime.setPoliceStation(policeStationRepository.getOne(crimeDTO.getPoliceStationId()));
        crime.setCrimeReportStatus(crimeDTO.getCrimeReportStatus());
        crime.setCrimeType(crimeDTO.getCrimeType());
        crime.setCrimeStatus(crimeDTO.getCrimeStatus());

        return new ApiResponse("Edited successfully",true);
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


    public List<Officer> getOfficerList(List<UUID> officers) {
        List<Officer> officerList = new ArrayList<>();
        for (UUID officer : officers) {
            Optional<Officer> byId = officerRepository.findById(officer);
            if (byId.isPresent()) officerList.add(byId.get());
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
