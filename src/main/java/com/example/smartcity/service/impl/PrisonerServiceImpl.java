package com.example.smartcity.service.impl;

import com.example.smartcity.entity.Crime;
import com.example.smartcity.entity.Prisoner;
import com.example.smartcity.payload.responseDTO.CustomPage;
import com.example.smartcity.payload.responseDTO.PrisonerResponseDTO;
import com.example.smartcity.payload.responseDTO.VictimResponseDTO;
import com.example.smartcity.repository.PrisonerRepository;
import com.example.smartcity.service.Mapper.Mappers;
import com.example.smartcity.service.PrisonerService;
import com.example.smartcity.exception.RestException;
import com.example.smartcity.payload.ApiResponse;
import com.example.smartcity.payload.CitizenDTO;
import com.example.smartcity.payload.PrisonerDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PrisonerServiceImpl implements PrisonerService {

    private final PrisonerRepository prisonerRepository;
    private final CitizenExternalApiServiceImpl citizenExternalApiService;
    private final Mappers mappers;

    @Override
    public CustomPage<PrisonerResponseDTO> getAllArrestedPeople(Integer page) {
        Pageable pageableAndSortedByTime = PageRequest.of(page,10, Sort.by("createdAt").descending());
        Page<Prisoner> prisoners = prisonerRepository.findAll(pageableAndSortedByTime);
        CustomPage<PrisonerResponseDTO> prisonerResponseDTOCustomPage = Mappers.prisonerCustomPage(prisoners);
        return prisonerResponseDTOCustomPage;
    }

    @Override
    public PrisonerResponseDTO getPrisonerById(UUID id) {
        Prisoner prisoner = prisonerRepository.findById(id)
                .orElseThrow(() -> new RestException("Prisoner not found", HttpStatus.NOT_FOUND));

        return mappers.forPrisonerResponseMapper(prisoner);
    }

    @Override
    @Transactional
    public ApiResponse addPrisoner(PrisonerDTO prisonerDTO) {
        CitizenDTO citizenByCardNumber = citizenExternalApiService.getCitizenByCardNumber(prisonerDTO.getCardNumber());

        List<Crime> crimeList= citizenExternalApiService.getCrimesWithId(prisonerDTO.getCrimes());

        Prisoner prisoner=new Prisoner(
                prisonerDTO.getCardNumber(),
                citizenByCardNumber.getFirstName(),
                citizenByCardNumber.getSurname(),
                citizenByCardNumber.getBirthDate(),
                prisonerDTO.getPrisonDuration(),
                prisonerDTO.getStartingDate(),
                prisonerDTO.getEndingDate(),
                true,
                citizenByCardNumber.getPhotoId(),
                crimeList
        );

        prisonerRepository.save(prisoner);
        citizenExternalApiService.sendPrisonerToCityManagement(prisonerDTO.getCardNumber());
        return new ApiResponse("success",true);

    }

    @Override
    @Transactional
    public ApiResponse editPrisoner(UUID id, PrisonerDTO prisonerDTO) {

        Prisoner prisoner = prisonerRepository.findById(id)
                .orElseThrow(()-> new RestException("Not found",HttpStatus.NOT_FOUND));

        Optional<Prisoner> byCardNumberAndIdNot = prisonerRepository.findByCardNumberAndIdNot(prisonerDTO.getCardNumber(), id);
        if (byCardNumberAndIdNot.isPresent()) throw  new RestException("This CardNumber Already added",HttpStatus.CONFLICT);
        CitizenDTO citizenByCardNumber = citizenExternalApiService.getCitizenByCardNumber(prisonerDTO.getCardNumber());

        List<Crime> crimeList = citizenExternalApiService.getCrimesWithId(prisonerDTO.getCrimes());

        if (crimeList!=null) {
            prisoner.setCrime(crimeList);
        }
        prisoner.setCardNumber(prisonerDTO.getCardNumber());
        prisoner.setFirstName(citizenByCardNumber.getFirstName());
        prisoner.setSurname(citizenByCardNumber.getSurname());
        prisoner.setBirthDate(citizenByCardNumber.getBirthDate());
        prisoner.setPrisonDuration(prisonerDTO.getPrisonDuration());
        prisoner.setStartingDate(prisonerDTO.getStartingDate());
        prisoner.setEndingDate(prisonerDTO.getEndingDate());
        prisoner.setPhotoId(citizenByCardNumber.getPhotoId());

        if (prisonerDTO.isInPrison() && prisoner.isInPrison()==false){
            prisoner.setInPrison(prisonerDTO.isInPrison());
            citizenExternalApiService.sendPrisonerToCityManagement(prisonerDTO.getCardNumber());
        }else if (prisonerDTO.isInPrison()==false && prisoner.isInPrison()==true){
            prisoner.setInPrison(prisoner.isInPrison());
            citizenExternalApiService.sendLiberationToCityManagement(prisonerDTO.getCardNumber());
        }

        prisonerRepository.save(prisoner);
        return new ApiResponse("Prisoner Updated Successfully",true);

    }

    @Override
    public ApiResponse deletePrisoner(UUID id) {
        try {
            prisonerRepository.deleteById(id);
            return new ApiResponse("Successfully deleted", true);
        } catch (Exception e) {
            throw new RestException("Prisoner Not found", HttpStatus.NOT_FOUND);
        }
    }
}
