package com.example.smartcity.service;

import com.example.smartcity.payload.CitizenDTO;
public interface CitizenExternalApiService {

     CitizenDTO getCitizenByCardNumber(long cardNumber);
}
