package com.example.smartcity.service.impl;

import com.example.smartcity.entity.Crime;
import com.example.smartcity.entity.Officer;
import com.example.smartcity.exception.RestException;
import com.example.smartcity.repository.CrimeRepository;
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
        }catch (WebClientResponseException exception){
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
    public List<Crime> getCrimesWithId(List<UUID> crimes){
        List<Crime> crimeList=new ArrayList<>();
        if (crimes!=null) {
            for (UUID crime : crimes) {
                Optional<Crime> byId = crimeRepository.findById(crime);
                if (byId.isPresent()) crimeList.add(byId.get());
            }
        }
        return crimeList;
    }

    @Override
    public String generateCertificate() {
        StringBuilder str=new StringBuilder();
        Random random = new Random();
        String abc = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String numbers="1234567890";
        for (int i = 0; i < 2; i++) {
            char letter = abc.charAt(random.nextInt(abc.length()));
            str.append(letter);
        }
        str.append(" ");
        for (int i = 0; i < 8; i++) {
            char number = numbers.charAt(random.nextInt(numbers.length()));
            str.append(number);
        }
        return str.toString();
    }

    public void sendCheckingCertificate(Officer officer){
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");//dd/MM/yyyy
        Date now = new Date();
        String strDate = sdfDate.format(now);


        String reqBody="{" +
//                "\"officerId\":" + officer.getId() + "," +
                "\"residentCardNumber\":" + officer.getCardNumber() + "," +
                "\"infoType\":" + "\"CERTIFICATE\"" + "," +
                "\"date\":" + "\""+strDate +"\"" +
                "}";

        try {
            httpRequestService.makePOSTHTTPCallUsingHMAC(
                    "POLICE",
                    "check_certificate",
                    "http://citymanagementfull-env.eba-tixcjyas.us-east-2.elasticbeanstalk.com/api/v1/request/police",
                    "policeKey",
                    reqBody
            );
        }catch (WebClientResponseException webClientResponseException){
            throw new RestException("Certificate didn't send for checking but saved in database",HttpStatus.CONFLICT);
        }
    }
    public void sendDeathPersonToCityManagement(long cardNumber){
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");//dd/MM/yyyy
        Date now = new Date();
        String strDate = sdfDate.format(now);


        String reqBody="{" +
                "\"residentCardNumber\":" + cardNumber + "," +
                "\"infoType\":" + "\"DEATH\"" + "," +
                "\"date\":" + "\""+strDate +"\"" +
                "}";

        try {
            httpRequestService.makePOSTHTTPCallUsingHMAC(
                    "POLICE",
                    "died_person",
                    "http://citymanagementfull-env.eba-tixcjyas.us-east-2.elasticbeanstalk.com/api/v1/request/police",
                    "policeKey",
                    reqBody
            );
        }catch (WebClientResponseException webClientResponseException){
            throw new RestException("Death info didn't send to City Management but saved in database",HttpStatus.CONFLICT);
        }
    }
    public void sendPrisonerToCityManagement(long cardNumber){
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");//dd/MM/yyyy
        Date now = new Date();
        String strDate = sdfDate.format(now);


        String reqBody="{" +
                "\"residentCardNumber\":" + cardNumber + "," +
                "\"infoType\":" + "\"PRISON\"" + "," +
                "\"date\":" + "\""+strDate +"\"" +
                "}";

        try {
            httpRequestService.makePOSTHTTPCallUsingHMAC(
                    "POLICE",
                    "prisoner",
                    "http://citymanagementfull-env.eba-tixcjyas.us-east-2.elasticbeanstalk.com/api/v1/request/police",
                    "policeKey",
                    reqBody
            );
        }catch (WebClientResponseException webClientResponseException){
            throw new RestException("Prisoner didn't send for blocking account but saved in database",HttpStatus.CONFLICT);
        }
    }
    public void sendLiberationToCityManagement(long cardNumber){
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");//dd/MM/yyyy
        Date now = new Date();
        String strDate = sdfDate.format(now);


        String reqBody="{" +
                "\"residentCardNumber\":" + cardNumber + "," +
                "\"infoType\":" + "\"LIBERATION\"" + "," +
                "\"date\":" + "\""+strDate +"\"" +
                "}";

        try {
            httpRequestService.makePOSTHTTPCallUsingHMAC(
                    "POLICE",
                    "Liberation",
                    "http://citymanagementfull-env.eba-tixcjyas.us-east-2.elasticbeanstalk.com/api/v1/request/police",
                    "policeKey",
                    reqBody
            );
        }catch (WebClientResponseException webClientResponseException){
            throw new RestException("Liberation didn't send for opening account but saved in database",HttpStatus.CONFLICT);
        }
    }
}
