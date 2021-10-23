package com.example.smartcity.service.Mapper;

import com.example.smartcity.entity.*;
import com.example.smartcity.payload.responseDTO.*;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class Mappers {
    public static WitnessResponseDTO forWitnessResponseMapper(Witness witness) {
        return new WitnessResponseDTO(
                witness.getId(),
                witness.getCardNumber(),
                witness.getFirstName(),
                witness.getSurname(),
                witness.getBirthDate(),
                witness.getPhoneNumber(),
                witness.getRemark(),
                witness.getPhotoId(),
                crimeResponseDTOList(witness.getCrime())
        );
    }
    public static VictimResponseDTO forVictimResponseMapper(Victim victim){
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
                crimeResponseDTOList(victim.getCrime())
        );
    }
    public static PrisonerResponseDTO forPrisonerResponseMapper(Prisoner prisoner){
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
                crimeResponseDTOList(prisoner.getCrime())
        );
    }

    public static OfficerResponseDTO forOfficerResponseMapper(Officer officer){
        if (officer==null) return null;

            return new OfficerResponseDTO(
                officer.getId(),
                officer.getCardNumber(),
                officer.getFirstName(),
                officer.getLastName(),
                officer.getRank(),
                forPoliceStationResponseMapper(officer.getPoliceStation()),
                officer.getBirthDate(),
                officer.getCertificate(),
                officer.getPhotoId()
        );
    }
    public static PoliceStationResponseDTO forPoliceStationResponseMapper(PoliceStation policeStation){
        if (policeStation==null) return null;

            return new PoliceStationResponseDTO(
                policeStation.getId(),
                policeStation.getName(),
                policeStation.getAddress(),
                policeStation.getPhoneNumber(),
                policeStation.getRemark()
        );
    }
    public static CrimeResponseDTO forCrimeResponseMapper(Crime crime){
        if (crime==null) return null;
            return new CrimeResponseDTO(
                crime.getId(),
                crime.getName(),
                crime.getAddress(),
                crime.getCrimeTime(),
                crime.getCrimeDescription(),
                officerResponseDTOList(crime.getOfficers()),
                forPoliceStationResponseMapper(crime.getPoliceStation()),
                crime.getCrimeReportStatus(),
                crime.getCrimeStatus(),
                crime.getCrimeType()
        );
    }

    public static CustomPage<PoliceStationResponseDTO> policeStationCustomPage(Page<PoliceStation> policeStationsPage) {
        return new CustomPage<> (
                policeStationsPage.getContent().stream().map(Mappers::forPoliceStationResponseMapper).collect(Collectors.toList()),
                policeStationsPage.getNumberOfElements(),
                policeStationsPage.getNumber(),
                policeStationsPage.getTotalElements(),
                policeStationsPage.getTotalPages(),
                policeStationsPage.getSize()
        );
    }
    public static CustomPage<WitnessResponseDTO> witnessCustomPage(Page<Witness> witnessPage) {
        return new CustomPage<> (
                witnessPage.getContent().stream().map(Mappers::forWitnessResponseMapper).collect(Collectors.toList()),
                witnessPage.getNumberOfElements(),
                witnessPage.getNumber(),
                witnessPage.getTotalElements(),
                witnessPage.getTotalPages(),
                witnessPage.getSize()
        );
    }

    public static CustomPage<VictimResponseDTO> victimCustomPage(Page<Victim> victims) {
        return new CustomPage<> (
                victims.getContent().stream().map(Mappers::forVictimResponseMapper).collect(Collectors.toList()),
                victims.getNumberOfElements(),
                victims.getNumber(),
                victims.getTotalElements(),
                victims.getTotalPages(),
                victims.getSize()
        );
    }
    public static CustomPage<PrisonerResponseDTO> prisonerCustomPage(Page<Prisoner> prisonerPage) {
        return new CustomPage<> (
                prisonerPage.getContent().stream().map(Mappers::forPrisonerResponseMapper).collect(Collectors.toList()),
                prisonerPage.getNumberOfElements(),
                prisonerPage.getNumber(),
                prisonerPage.getTotalElements(),
                prisonerPage.getTotalPages(),
                prisonerPage.getSize()
        );
    }

    public static CustomPage<OfficerResponseDTO> officerCustomPage(Page<Officer> officerPage) {
        return new CustomPage<> (
                officerPage.getContent().stream().map(Mappers::forOfficerResponseMapper).collect(Collectors.toList()),
                officerPage.getNumberOfElements(),
                officerPage.getNumber(),
                officerPage.getTotalElements(),
                officerPage.getTotalPages(),
                officerPage.getSize()
        );
    }

    public static CustomPage<CrimeResponseDTO> CrimeCustomPage(Page<Crime> crimePage) {
        return new CustomPage<> (
                crimePage.getContent().stream().map(Mappers::forCrimeResponseMapper).collect(Collectors.toList()),
                crimePage.getNumberOfElements(),
                crimePage.getNumber(),
                crimePage.getTotalElements(),
                crimePage.getTotalPages(),
                crimePage.getSize()
        );
    }
    public static List<OfficerResponseDTO> officerResponseDTOList(List<Officer> officerList){
        return officerList.stream().map(Mappers::forOfficerResponseMapper).collect(Collectors.toList());
    }
    public static List<CrimeResponseDTO> crimeResponseDTOList(List<Crime> crimes){
        return crimes.stream().map(Mappers::forCrimeResponseMapper).collect(Collectors.toList());
    }

}

