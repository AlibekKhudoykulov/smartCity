package com.example.smartcity.Service;

import com.example.smartcity.payload.CitizenDTO;
public interface CitizenExternalApiService {

     CitizenDTO getCitizenByCardNumber(long cardNumber);
}
