package com.example.smartcity.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.smartcity.entity.Crime;
import com.example.smartcity.entity.Prisoner;
import com.example.smartcity.exception.RestException;
import com.example.smartcity.payload.ApiResponse;
import com.example.smartcity.payload.CitizenDTO;
import com.example.smartcity.payload.PrisonerDTO;
import com.example.smartcity.repository.PrisonerRepository;
import com.example.smartcity.service.Mapper.Mappers;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {PrisonerServiceImpl.class, CitizenExternalApiServiceImpl.class, Mappers.class})
@ExtendWith(SpringExtension.class)
class PrisonerServiceImplTest {
    @MockBean
    private CitizenExternalApiServiceImpl citizenExternalApiServiceImpl;

    @MockBean
    private Mappers mappers;

    @MockBean
    private PrisonerRepository prisonerRepository;

    @Autowired
    private PrisonerServiceImpl prisonerServiceImpl;

    @Test
    void testEditPrisoner() {
        Prisoner prisoner = new Prisoner();
        prisoner.setId(UUID.randomUUID());
        prisoner.setUpdatedAt(mock(Timestamp.class));
        prisoner.setCardNumber(1L);
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        prisoner.setEndingDate(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        prisoner.setCreatedById(UUID.randomUUID());
        prisoner.setDeleted(true);
        prisoner.setSurname("Doe");
        prisoner.setFirstName("Jane");
        prisoner.setUpdateById(UUID.randomUUID());
        prisoner.setInPrison(true);
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        prisoner.setStartingDate(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        prisoner.setCreatedAt(mock(Timestamp.class));
        LocalDateTime atStartOfDayResult2 = LocalDate.of(1970, 1, 1).atStartOfDay();
        prisoner.setBirthDate(Date.from(atStartOfDayResult2.atZone(ZoneId.of("UTC")).toInstant()));
        prisoner.setPrisonDuration("Prison Duration");
        prisoner.setCrime(new ArrayList<Crime>());
        Optional<Prisoner> ofResult = Optional.<Prisoner>of(prisoner);
        when(this.prisonerRepository.existsByCardNumberAndIdNot((Long) any(), (UUID) any())).thenReturn(true);
        when(this.prisonerRepository.findById((UUID) any())).thenReturn(ofResult);
        UUID id = UUID.randomUUID();
        assertThrows(RestException.class, () -> this.prisonerServiceImpl.editPrisoner(id, new PrisonerDTO()));
        verify(this.prisonerRepository).existsByCardNumberAndIdNot((Long) any(), (UUID) any());
        verify(this.prisonerRepository).findById((UUID) any());
    }

    @Test
    void testEditPrisoner2() {
        Prisoner prisoner = new Prisoner();
        prisoner.setId(UUID.randomUUID());
        prisoner.setUpdatedAt(mock(Timestamp.class));
        prisoner.setCardNumber(1L);
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        prisoner.setEndingDate(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        prisoner.setCreatedById(UUID.randomUUID());
        prisoner.setDeleted(true);
        prisoner.setSurname("Doe");
        prisoner.setFirstName("Jane");
        prisoner.setUpdateById(UUID.randomUUID());
        prisoner.setInPrison(true);
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        prisoner.setStartingDate(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        prisoner.setCreatedAt(mock(Timestamp.class));
        LocalDateTime atStartOfDayResult2 = LocalDate.of(1970, 1, 1).atStartOfDay();
        prisoner.setBirthDate(Date.from(atStartOfDayResult2.atZone(ZoneId.of("UTC")).toInstant()));
        prisoner.setPrisonDuration("Prison Duration");
        prisoner.setCrime(new ArrayList<Crime>());
        Optional<Prisoner> ofResult = Optional.<Prisoner>of(prisoner);

        Prisoner prisoner1 = new Prisoner();
        prisoner1.setId(UUID.randomUUID());
        prisoner1.setUpdatedAt(mock(Timestamp.class));
        prisoner1.setCardNumber(1L);
        LocalDateTime atStartOfDayResult3 = LocalDate.of(1970, 1, 1).atStartOfDay();
        prisoner1.setEndingDate(Date.from(atStartOfDayResult3.atZone(ZoneId.of("UTC")).toInstant()));
        prisoner1.setCreatedById(UUID.randomUUID());
        prisoner1.setDeleted(true);
        prisoner1.setSurname("Doe");
        prisoner1.setFirstName("Jane");
        prisoner1.setUpdateById(UUID.randomUUID());
        prisoner1.setInPrison(true);
        LocalDateTime atStartOfDayResult4 = LocalDate.of(1970, 1, 1).atStartOfDay();
        prisoner1.setStartingDate(Date.from(atStartOfDayResult4.atZone(ZoneId.of("UTC")).toInstant()));
        prisoner1.setCreatedAt(mock(Timestamp.class));
        LocalDateTime atStartOfDayResult5 = LocalDate.of(1970, 1, 1).atStartOfDay();
        prisoner1.setBirthDate(Date.from(atStartOfDayResult5.atZone(ZoneId.of("UTC")).toInstant()));
        prisoner1.setPrisonDuration("Prison Duration");
        prisoner1.setCrime(new ArrayList<Crime>());
        when(this.prisonerRepository.save((Prisoner) any())).thenReturn(prisoner1);
        when(this.prisonerRepository.existsByCardNumberAndIdNot((Long) any(), (UUID) any())).thenReturn(false);
        when(this.prisonerRepository.findById((UUID) any())).thenReturn(ofResult);

        CitizenDTO citizenDTO = new CitizenDTO();
        citizenDTO.setCardNumber(1L);
        LocalDateTime atStartOfDayResult6 = LocalDate.of(1970, 1, 1).atStartOfDay();
        citizenDTO.setBirthDate(Date.from(atStartOfDayResult6.atZone(ZoneId.of("UTC")).toInstant()));
        citizenDTO.setFirstName("Jane");
        citizenDTO.setSurname("Doe");
        doNothing().when(this.citizenExternalApiServiceImpl).sendLiberationToCityManagement(anyLong());
        when(this.citizenExternalApiServiceImpl.getCrimesWithId((List<UUID>) any())).thenReturn(new ArrayList<Crime>());
        when(this.citizenExternalApiServiceImpl.getCitizenByCardNumber(anyLong())).thenReturn(citizenDTO);
        UUID id = UUID.randomUUID();
        ApiResponse actualEditPrisonerResult = this.prisonerServiceImpl.editPrisoner(id, new PrisonerDTO());
        assertEquals("Prisoner Updated Successfully", actualEditPrisonerResult.getMessage());
        assertTrue(actualEditPrisonerResult.isSuccess());
        verify(this.prisonerRepository).existsByCardNumberAndIdNot((Long) any(), (UUID) any());
        verify(this.prisonerRepository).findById((UUID) any());
        verify(this.prisonerRepository).save((Prisoner) any());
        verify(this.citizenExternalApiServiceImpl).getCitizenByCardNumber(anyLong());
        verify(this.citizenExternalApiServiceImpl).getCrimesWithId((List<UUID>) any());
        verify(this.citizenExternalApiServiceImpl).sendLiberationToCityManagement(anyLong());
    }

    @Test
    void testEditPrisoner3() {
        Prisoner prisoner = new Prisoner();
        prisoner.setId(UUID.randomUUID());
        prisoner.setUpdatedAt(mock(Timestamp.class));
        prisoner.setCardNumber(1L);
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        prisoner.setEndingDate(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        prisoner.setCreatedById(UUID.randomUUID());
        prisoner.setDeleted(true);
        prisoner.setSurname("Doe");
        prisoner.setFirstName("Jane");
        prisoner.setUpdateById(UUID.randomUUID());
        prisoner.setInPrison(false);
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        prisoner.setStartingDate(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        prisoner.setCreatedAt(mock(Timestamp.class));
        LocalDateTime atStartOfDayResult2 = LocalDate.of(1970, 1, 1).atStartOfDay();
        prisoner.setBirthDate(Date.from(atStartOfDayResult2.atZone(ZoneId.of("UTC")).toInstant()));
        prisoner.setPrisonDuration("Prison Duration");
        prisoner.setCrime(new ArrayList<Crime>());
        Optional<Prisoner> ofResult = Optional.<Prisoner>of(prisoner);

        Prisoner prisoner1 = new Prisoner();
        prisoner1.setId(UUID.randomUUID());
        prisoner1.setUpdatedAt(mock(Timestamp.class));
        prisoner1.setCardNumber(1L);
        LocalDateTime atStartOfDayResult3 = LocalDate.of(1970, 1, 1).atStartOfDay();
        prisoner1.setEndingDate(Date.from(atStartOfDayResult3.atZone(ZoneId.of("UTC")).toInstant()));
        prisoner1.setCreatedById(UUID.randomUUID());
        prisoner1.setDeleted(true);
        prisoner1.setSurname("Doe");
        prisoner1.setFirstName("Jane");
        prisoner1.setUpdateById(UUID.randomUUID());
        prisoner1.setInPrison(true);
        LocalDateTime atStartOfDayResult4 = LocalDate.of(1970, 1, 1).atStartOfDay();
        prisoner1.setStartingDate(Date.from(atStartOfDayResult4.atZone(ZoneId.of("UTC")).toInstant()));
        prisoner1.setCreatedAt(mock(Timestamp.class));
        LocalDateTime atStartOfDayResult5 = LocalDate.of(1970, 1, 1).atStartOfDay();
        prisoner1.setBirthDate(Date.from(atStartOfDayResult5.atZone(ZoneId.of("UTC")).toInstant()));
        prisoner1.setPrisonDuration("Prison Duration");
        prisoner1.setCrime(new ArrayList<Crime>());
        when(this.prisonerRepository.save((Prisoner) any())).thenReturn(prisoner1);
        when(this.prisonerRepository.existsByCardNumberAndIdNot((Long) any(), (UUID) any())).thenReturn(false);
        when(this.prisonerRepository.findById((UUID) any())).thenReturn(ofResult);

        CitizenDTO citizenDTO = new CitizenDTO();
        citizenDTO.setCardNumber(1L);
        LocalDateTime atStartOfDayResult6 = LocalDate.of(1970, 1, 1).atStartOfDay();
        citizenDTO.setBirthDate(Date.from(atStartOfDayResult6.atZone(ZoneId.of("UTC")).toInstant()));
        citizenDTO.setFirstName("Jane");
        citizenDTO.setSurname("Doe");
        doNothing().when(this.citizenExternalApiServiceImpl).sendLiberationToCityManagement(anyLong());
        when(this.citizenExternalApiServiceImpl.getCrimesWithId((List<UUID>) any())).thenReturn(new ArrayList<Crime>());
        when(this.citizenExternalApiServiceImpl.getCitizenByCardNumber(anyLong())).thenReturn(citizenDTO);
        UUID id = UUID.randomUUID();
        ApiResponse actualEditPrisonerResult = this.prisonerServiceImpl.editPrisoner(id, new PrisonerDTO());
        assertEquals("Prisoner Updated Successfully", actualEditPrisonerResult.getMessage());
        assertTrue(actualEditPrisonerResult.isSuccess());
        verify(this.prisonerRepository).existsByCardNumberAndIdNot((Long) any(), (UUID) any());
        verify(this.prisonerRepository).findById((UUID) any());
        verify(this.prisonerRepository).save((Prisoner) any());
        verify(this.citizenExternalApiServiceImpl).getCitizenByCardNumber(anyLong());
        verify(this.citizenExternalApiServiceImpl).getCrimesWithId((List<UUID>) any());
        verify(this.citizenExternalApiServiceImpl).sendLiberationToCityManagement(anyLong());
    }
}

