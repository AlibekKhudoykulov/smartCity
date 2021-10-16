package com.example.smartcity.service.impl;

import com.example.smartcity.entity.Crime;
import com.example.smartcity.entity.Victim;
import com.example.smartcity.payload.CitizenDTO;
import com.example.smartcity.payload.responseDTO.VictimResponseDTO;
import com.example.smartcity.payload.responseDTO.WitnessResponseDTO;
import com.example.smartcity.repository.VictimRepository;
import com.example.smartcity.service.Mapper.Mappers;
import com.example.smartcity.service.VictimService;
import com.example.smartcity.exception.RestException;
import com.example.smartcity.payload.ApiResponse;
import com.example.smartcity.payload.VictimDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VictimServiceImpl implements VictimService {

    private final VictimRepository victimRepository;
    private final CitizenExternalApiServiceImpl citizenExternalApiService;
    private final Mappers mappers;

    @Override
    public List<VictimResponseDTO> getAllVictims(Integer page) {
        Pageable pageableAndSortedByTime = PageRequest.of(page,10, Sort.by("createdAt").descending());
        Page<Victim> all = victimRepository.findAll(pageableAndSortedByTime);

        List<VictimResponseDTO> collect = all.getContent()
                .stream()
                .map(mappers::forVictimResponseMapper)
                .collect(Collectors.toList());

        return collect;
    }

    @Override
    public VictimResponseDTO getVictimById(UUID id) {
        Victim victim = victimRepository.findById(id)
                .orElseThrow(() -> new RestException("Victim not found", HttpStatus.NOT_FOUND));
        VictimResponseDTO victimResponseDTO = mappers.forVictimResponseMapper(victim);
        return victimResponseDTO;
    }

    @Override
    @Transactional
    public ApiResponse addVictim(VictimDTO victimDTO) {
        boolean existsByCardNumber = victimRepository.existsByCardNumber(victimDTO.getCardNumber());
        if (existsByCardNumber) throw new RestException("This card number already added",HttpStatus.CONFLICT);

        CitizenDTO citizenByCardNumber = citizenExternalApiService.getCitizenByCardNumber(victimDTO.getCardNumber());
        List<Crime> crimeList = citizenExternalApiService.getCrimesWithId(victimDTO.getCrimes());

        Victim victim=new Victim(
                victimDTO.getCardNumber(),
                citizenByCardNumber.getFirstName(),
                citizenByCardNumber.getSurname(),
                citizenByCardNumber.getBirthDate(),
                victimDTO.getDeathDate(),
                victimDTO.getRemark(),
                citizenByCardNumber.getPhotoId(),
                victimDTO.isDead(),
                crimeList
        );

        if (victimDTO.isDead()) citizenExternalApiService.sendDeathPersonToCityManagement(victimDTO.getCardNumber());

        victimRepository.save(victim);
        return new  ApiResponse("Victim Saved Successfully",true);
    }

    @Override
    @Transactional
    public ApiResponse editVictim(UUID id,VictimDTO victimDTO) {
        Victim victim = victimRepository.findById(id).
                orElseThrow(() -> new RestException("Not found", HttpStatus.NOT_FOUND));
        boolean byCardNumberAndIdNot = victimRepository.existsByCardNumberAndIdNot(victimDTO.getCardNumber(), id);
        if (byCardNumberAndIdNot) throw new RestException("This Card number already added",HttpStatus.CONFLICT);


        CitizenDTO citizenByCardNumber = citizenExternalApiService.getCitizenByCardNumber(victimDTO.getCardNumber());
        List<Crime> crimeList = citizenExternalApiService.getCrimesWithId(victimDTO.getCrimes());

        if (crimeList!=null){
            victim.setCrime(crimeList);
        }
        victim.setCardNumber(victimDTO.getCardNumber());
        victim.setName(citizenByCardNumber.getFirstName());
        victim.setSurname(citizenByCardNumber.getSurname());
        victim.setBirthDate(citizenByCardNumber.getBirthDate());
        victim.setPhotoId(citizenByCardNumber.getPhotoId());
        victim.setRemark(victimDTO.getRemark());
        victim.setDead(victimDTO.isDead());

        victimRepository.save(victim);
        return new ApiResponse("Victim Updated Successfully",true);
    }

    @Override
    public ApiResponse deleteVictim(UUID id) {
        try {
            victimRepository.deleteById(id);
            return new ApiResponse("Successfully deleted", true);
        } catch (Exception e) {
            throw new RestException("Victim Not found", HttpStatus.NOT_FOUND);
        }
    }
}
