package com.example.smartcity.service;

import com.example.smartcity.entity.Crime;
import com.example.smartcity.payload.CitizenDTO;

import java.util.List;
import java.util.UUID;

public interface CitizenExternalApiService {

     CitizenDTO getCitizenByCardNumber(long cardNumber);
     List<Crime> getCrimesWithId(List<UUID> crimes);
     String generateCertificate();
}
