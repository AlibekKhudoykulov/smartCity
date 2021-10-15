package com.example.smartcity.service.Mapper;

import com.example.smartcity.entity.*;
import com.example.smartcity.payload.responseDTO.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class Mappers {
    public WitnessResponseDTO forWitnessResponseMapper(Witness witness) {
        return new WitnessResponseDTO(
                witness.getId(),
                witness.getCardNumber(),
                witness.getFirstName(),
                witness.getSurname(),
                witness.getBirthDate(),
                witness.getPhoneNumber(),
                witness.getRemark(),
                witness.getPhotoId(),
                this.crimeResponseDTOList(witness.getCrime())
        );
    }
    public VictimResponseDTO forVictimResponseMapper(Victim victim){
        return new VictimResponseDTO(
                victim.getId(),
                victim.getCardNumber(),
                victim.getName(),
                victim.getSurname(),
                victim.getBirthDate(),
                victim.getDeathDate(),
                victim.getRemark(),
                victim.getPhotoId(),
                victim.isDead(),
                this.crimeResponseDTOList(victim.getCrime())
        );
    }
    public PrisonerResponseDTO forPrisonerResponseMapper(Prisoner prisoner){
        return new PrisonerResponseDTO(
                prisoner.getId(),
                prisoner.getCardNumber(),
                prisoner.getFirstName(),
                prisoner.getSurname(),
                prisoner.getBirthDate(),
                prisoner.getPrisonDuration(),
                prisoner.getStartingDate(),
                prisoner.getEndingDate(),
                prisoner.isInPrison(),
                prisoner.getPhotoId(),
                this.crimeResponseDTOList(prisoner.getCrime())
        );
    }

    public OfficerResponseDTO forOfficerResponseMapper(Officer officer){
        if (officer==null) return null;

            return new OfficerResponseDTO(
                officer.getId(),
                officer.getCardNumber(),
                officer.getFirstName(),
                officer.getLastName(),
                officer.getRank(),
                this.forPoliceStationResponseMapper(officer.getPoliceStation()),
                officer.getBirthDate(),
                officer.getCertificate(),
                officer.getPhotoId()
        );
    }
    public PoliceStationResponseDTO forPoliceStationResponseMapper(PoliceStation policeStation){
        if (policeStation==null) return null;

            return new PoliceStationResponseDTO(
                policeStation.getId(),
                policeStation.getName(),
                policeStation.getAddress(),
                policeStation.getPhoneNumber(),
                policeStation.getRemark()
        );
    }
    public CrimeResponseDTO forCrimeResponseMapper(Crime crime){
        if (crime==null) return null;
            return new CrimeResponseDTO(
                crime.getId(),
                crime.getName(),
                crime.getAddress(),
                crime.getCrimeTime(),
                crime.getCrimeDescription(),
                this.forCrimeOfficerList(crime.getOfficers()),
                this.forPoliceStationResponseMapper(crime.getPoliceStation()),
                crime.getCrimeReportStatus(),
                crime.getCrimeStatus(),
                crime.getCrimeType()
        );
    }
    public List<OfficerResponseDTO> forCrimeOfficerList(List<Officer> officerList){
        return officerList.stream().map(this::forOfficerResponseMapper).collect(Collectors.toList());
    }
    public List<CrimeResponseDTO> crimeResponseDTOList(List<Crime> crimes){
        return crimes.stream().map(this::forCrimeResponseMapper).collect(Collectors.toList());
    }
}

