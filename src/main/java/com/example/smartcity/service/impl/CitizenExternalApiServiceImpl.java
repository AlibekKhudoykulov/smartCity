package com.example.smartcity.service.impl;

import com.example.smartcity.entity.*;
import com.example.smartcity.exception.RestException;
import com.example.smartcity.payload.PoliceStationDTO;
import com.example.smartcity.payload.responseDTO.*;
import com.example.smartcity.repository.CrimeRepository;
import com.example.smartcity.repository.PoliceStationRepository;
import com.example.smartcity.service.CitizenExternalApiService;
import com.example.smartcity.payload.CitizenDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@RequiredArgsConstructor
public class CitizenExternalApiServiceImpl implements CitizenExternalApiService {

    private final HTTPRequestServiceImpl httpRequestService;
    private final CrimeRepository crimeRepository;
    private final PoliceStationRepository policeStationRepository;

    @Override
    public CitizenDTO getCitizenByCardNumber(long cardNumber) {
        ResponseEntity<?> responseEntity;
        try {
            responseEntity = httpRequestService.makeGETHTTPCallUsingHMAC(
                    "POLICE",
                    "get_resident",
                    "http://citymanagementfull-env.eba-tixcjyas.us-east-2.elasticbeanstalk.com/api/v1/resident/card/"
                            + cardNumber,
                    "policeKey"
            );
        } catch (WebClientResponseException exception) {
            throw new RestException("Card number not found", HttpStatus.NOT_FOUND);
        }


        Object body = responseEntity.getBody();
        String s = body.toString();

        String[] split = s.split(",");
        CitizenDTO citizenDTO = new CitizenDTO();
        for (String s1 : split) {
            if (s1.replaceAll("\"", "").startsWith("firstName")) {
                citizenDTO.setFirstName(s1.substring(13, s1.length() - 1));
            }
            if (s1.replaceAll("\"", "").startsWith("lastName")) {
                citizenDTO.setSurname(s1.substring(12, s1.length() - 1));
            }
            if (s1.replaceAll("\"", "").startsWith("photoId")) {
                citizenDTO.setPhotoId(UUID.fromString(s1.substring(11, s1.length() - 1)));
            }
            if (s1.replaceAll("\"", "").startsWith("birthDate")) {
                try {
                    String substring = s1.substring(13, s1.length() - 1);
                    Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(substring);
                    citizenDTO.setBirthDate(date1);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }

        return citizenDTO;
    }

    @Override
    public List<Crime> getCrimesWithId(List<UUID> crimes) {
        List<Crime> crimeList = new ArrayList<>();
        if (crimes != null) {
            for (UUID crime : crimes) {
                Optional<Crime> byId = crimeRepository.findById(crime);
                if (byId.isPresent()) crimeList.add(byId.get());
            }
        }
        return crimeList;
    }

    @Override
    public long generateCertificate() {
        Random rnd = new Random();
        long n = 100000 + rnd.nextInt(900000);
        return n;
    }

    public void sendCheckingCertificate(Officer officer) {
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");//dd/MM/yyyy
        Date now = new Date();
        String strDate = sdfDate.format(now);


        String reqBody = "{" +
                "\"officerId\":" + "\"" + officer.getId() + "\"" + "," +
                "\"officerCardNumber\":" + officer.getCardNumber() + "," +
                "\"officerFullName\":" + "\"" + officer.getFirstName() + " " + officer.getLastName() + "\"" + "," +
                "\"certificateCode\":" + officer.getCertificate() +
                "}";

        try {
            httpRequestService.makePOSTHTTPCallUsingHMAC(
                    "POLICE",
                    "check_certificate",
                    "http://citymanagementfull-env.eba-tixcjyas.us-east-2.elasticbeanstalk.com/api/v1/request/police/certificate",
                    "policeKey",
                    reqBody
            );
        } catch (WebClientResponseException webClientResponseException) {
            throw new RestException("Certificate didn't send for checking but saved in database", HttpStatus.CONFLICT);
        }
    }

    public void sendDeathPersonToCityManagement(long cardNumber) {
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");//dd/MM/yyyy
        Date now = new Date();
        String strDate = sdfDate.format(now);


        String reqBody = "{" +
                "\"residentCardNumber\":" + cardNumber + "," +
                "\"infoType\":" + "\"DEATH\"" + "," +
                "\"date\":" + "\"" + strDate + "\"" +
                "}";

        try {
            httpRequestService.makePOSTHTTPCallUsingHMAC(
                    "POLICE",
                    "died_person",
                    "http://citymanagementfull-env.eba-tixcjyas.us-east-2.elasticbeanstalk.com/api/v1/request/police",
                    "policeKey",
                    reqBody
            );
        } catch (WebClientResponseException webClientResponseException) {
            throw new RestException("Death info didn't send to City Management but saved in database", HttpStatus.CONFLICT);
        }
    }

    public void sendPrisonerToCityManagement(long cardNumber) {
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");//dd/MM/yyyy
        Date now = new Date();
        String strDate = sdfDate.format(now);


        String reqBody = "{" +
                "\"residentCardNumber\":" + cardNumber + "," +
                "\"infoType\":" + "\"PRISON\"" + "," +
                "\"date\":" + "\"" + strDate + "\"" +
                "}";

        try {
            httpRequestService.makePOSTHTTPCallUsingHMAC(
                    "POLICE",
                    "prisoner",
                    "http://citymanagementfull-env.eba-tixcjyas.us-east-2.elasticbeanstalk.com/api/v1/request/police",
                    "policeKey",
                    reqBody
            );
        } catch (WebClientResponseException webClientResponseException) {
            throw new RestException("Prisoner didn't send for blocking account but saved in database", HttpStatus.CONFLICT);
        }
    }

    public void sendLiberationToCityManagement(long cardNumber) {
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");//dd/MM/yyyy
        Date now = new Date();
        String strDate = sdfDate.format(now);


        String reqBody = "{" +
                "\"residentCardNumber\":" + cardNumber + "," +
                "\"infoType\":" + "\"LIBERATION\"" + "," +
                "\"date\":" + "\"" + strDate + "\"" +
                "}";

        try {
            httpRequestService.makePOSTHTTPCallUsingHMAC(
                    "POLICE",
                    "Liberation",
                    "http://citymanagementfull-env.eba-tixcjyas.us-east-2.elasticbeanstalk.com/api/v1/request/police",
                    "policeKey",
                    reqBody
            );
        } catch (WebClientResponseException webClientResponseException) {
            throw new RestException("Liberation didn't send for opening account but saved in database", HttpStatus.CONFLICT);
        }
    }

    public OfficerResponseDTO sendOfficer(Officer officer) {
        PoliceStationDTO policeStationDTO = null;
        if (officer.getPoliceStation() != null) {
            policeStationDTO = new PoliceStationDTO(
                    officer.getPoliceStation().getName(),
                    officer.getPoliceStation().getPhoneNumber(),
                    officer.getPoliceStation().getAddress(),
                    officer.getPoliceStation().getRemark()
            );
        }
        OfficerResponseDTO officerResponseDTO = OfficerResponseDTO.builder()
                .uuid(officer.getId())
                .cardNumber(officer.getCardNumber())
                .firstName(officer.getFirstName())
                .lastName(officer.getLastName())
                .rank(officer.getRank())
                .policeStation(policeStationDTO)
                .build();
        return officerResponseDTO;
    }

    public OfficerResponseDTO forResponseOfficer(Officer officer) {
        PoliceStationDTO policeStationDTO = null;
        if (officer.getPoliceStation() != null) {
            policeStationDTO = new PoliceStationDTO(
                    officer.getPoliceStation().getName(),
                    officer.getPoliceStation().getPhoneNumber(),
                    officer.getPoliceStation().getAddress(),
                    officer.getPoliceStation().getRemark()
            );
        }
        OfficerResponseDTO officerResponseDTO = OfficerResponseDTO.builder()
                .uuid(officer.getId())
                .cardNumber(officer.getCardNumber())
                .firstName(officer.getFirstName())
                .lastName(officer.getLastName())
                .rank(officer.getRank())
                .birthDate(officer.getBirthDate())
                .certificate(officer.getCertificate())
                .photoId(officer.getPhotoId())
                .policeStation(policeStationDTO)
                .build();
        return officerResponseDTO;
    }

    public CrimeResponseDTO forResponseCrime(Crime crime) {

        PoliceStationDTO policeStationDTO = null;
        if (crime.getPoliceStation() != null) {
            policeStationDTO = PoliceStationDTO.builder()
                    .name(crime.getPoliceStation().getName())
                    .address(crime.getPoliceStation().getAddress())
                    .phoneNumber(crime.getPoliceStation().getPhoneNumber())
                    .remark(crime.getPoliceStation().getRemark())
                    .build();
        }
        List<OfficerResponseDTO> officerList = new ArrayList<>();
        if (crime.getOfficers() != null && crime.getOfficers().size() != 0) {
            for (Officer officer : crime.getOfficers()) {
                officerList.add(forResponseOfficer(officer));
            }
        }

        CrimeResponseDTO crimeResponseDTO = CrimeResponseDTO.builder()
                .uuid(crime.getId())
                .name(crime.getName())
                .address(crime.getAddress())
                .crimeTime(crime.getCrimeTime())
                .crimeDescription(crime.getCrimeDescription())
                .officers(officerList)
                .policeStation(policeStationDTO)
                .crimeReportStatus(crime.getCrimeReportStatus())
                .crimeStatus(crime.getCrimeStatus())
                .crimeType(crime.getCrimeType())
                .build();
        return crimeResponseDTO;
    }

    public WitnessResponseDTO forResponseWitness(Witness witness) {
        List<CrimeResponseDTO> crimeResponseDTOS = new ArrayList<>();
        for (Crime crime : witness.getCrime()) {
            CrimeResponseDTO crimeResponseDTO = forResponseCrime(crime);
            crimeResponseDTOS.add(crimeResponseDTO);
        }
        WitnessResponseDTO witnessResponseDTO = WitnessResponseDTO.builder()
                .id(witness.getId())
                .firstName(witness.getFirstName())
                .surname(witness.getSurname())
                .birthDate(witness.getBirthDate())
                .cardNumber(witness.getCardNumber())
                .phoneNumber(witness.getPhoneNumber())
                .photoId(witness.getPhotoId())
                .crime(crimeResponseDTOS)
                .remark(witness.getRemark())
                .build();

        return witnessResponseDTO;
    }

    public VictimResponseDTO forResponseVictim(Victim victim) {
        List<CrimeResponseDTO> crimeResponseDTOS = new ArrayList<>();
        for (Crime crime : victim.getCrime()) {
            CrimeResponseDTO crimeResponseDTO = forResponseCrime(crime);
            crimeResponseDTOS.add(crimeResponseDTO);
        }
        VictimResponseDTO victimResponseDTO = VictimResponseDTO.builder()
                .id(victim.getId())
                .name(victim.getName())
                .surname(victim.getSurname())
                .birthDate(victim.getBirthDate())
                .cardNumber(victim.getCardNumber())
                .deathDate(victim.getDeathDate())
                .photoId(victim.getPhotoId())
                .crimes(crimeResponseDTOS)
                .remark(victim.getRemark())
                .build();

        return victimResponseDTO;
    }
    public PrisonerResponseDTO forResponsePrisoner(Prisoner prisoner) {
        List<CrimeResponseDTO> crimeResponseDTOS = new ArrayList<>();
        for (Crime crime : prisoner.getCrime()) {
            CrimeResponseDTO crimeResponseDTO = forResponseCrime(crime);
            crimeResponseDTOS.add(crimeResponseDTO);
        }
        PrisonerResponseDTO prisonerResponseDTO = PrisonerResponseDTO.builder()
                .id(prisoner.getId())
                .firstName(prisoner.getFirstName())
                .surname(prisoner.getSurname())
                .birthDate(prisoner.getBirthDate())
                .cardNumber(prisoner.getCardNumber())
                .prisonDuration(prisoner.getPrisonDuration())
                .photoId(prisoner.getPhotoId())
                .startingDate(prisoner.getStartingDate())
                .endingDate(prisoner.getEndingDate())
                .crimes(crimeResponseDTOS)
                .build();

        return prisonerResponseDTO;
    }
}
