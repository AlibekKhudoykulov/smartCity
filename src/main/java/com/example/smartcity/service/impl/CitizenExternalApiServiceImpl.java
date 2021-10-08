package com.example.smartcity.service.impl;

import com.example.smartcity.entity.Crime;
import com.example.smartcity.repository.CrimeRepository;
import com.example.smartcity.service.CitizenExternalApiService;
import com.example.smartcity.payload.CitizenDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

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
        ResponseEntity<?> responseEntity = httpRequestService.makeGETHTTPCallUsingHMAC(
                "POLICE",
                "get_resident",
                "http://citymanagementfull-env.eba-tixcjyas.us-east-2.elasticbeanstalk.com/api/v1/resident/card/"
                        + cardNumber,
                "policeKey"
        );
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
}
