package com.example.smartcity.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.smartcity.entity.Crime;
import com.example.smartcity.entity.Officer;
import com.example.smartcity.entity.PoliceStation;
import com.example.smartcity.entity.Victim;
import com.example.smartcity.entity.enums.CrimeReportStatus;
import com.example.smartcity.entity.enums.CrimeStatus;
import com.example.smartcity.entity.enums.CrimeType;
import com.example.smartcity.entity.enums.OfficerRank;
import com.example.smartcity.exception.RestException;
import com.example.smartcity.payload.ApiResponse;
import com.example.smartcity.payload.CitizenDTO;
import com.example.smartcity.payload.VictimDTO;
import com.example.smartcity.payload.responseDTO.CustomPage;
import com.example.smartcity.payload.responseDTO.VictimResponseDTO;
import com.example.smartcity.repository.VictimRepository;
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
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {VictimServiceImpl.class, CitizenExternalApiServiceImpl.class, Mappers.class})
@ExtendWith(SpringExtension.class)
class VictimServiceImplTest {
    @MockBean
    private CitizenExternalApiServiceImpl citizenExternalApiServiceImpl;

    @MockBean
    private Mappers mappers;

    @MockBean
    private VictimRepository victimRepository;

    @Autowired
    private VictimServiceImpl victimServiceImpl;

    @Test
    void testGetAllVictims() {
        when(this.victimRepository.findAll((org.springframework.data.domain.Pageable) any()))
                .thenReturn(new PageImpl<Victim>(new ArrayList<Victim>()));
        CustomPage<VictimResponseDTO> actualAllVictims = this.victimServiceImpl.getAllVictims(1);
        assertTrue(actualAllVictims.getContent().isEmpty());
        assertEquals(1, actualAllVictims.getTotalPages());
        assertEquals(0L, actualAllVictims.getTotalElements());
        assertEquals(0, actualAllVictims.getSize());
        assertEquals(0, actualAllVictims.getNumberOfElements());
        assertEquals(0, actualAllVictims.getNumber());
        verify(this.victimRepository).findAll((org.springframework.data.domain.Pageable) any());
    }

    @Test
    void testGetAllVictims2() {
        Victim victim = new Victim();
        victim.setRemark("createdAt");
        victim.setId(UUID.randomUUID());
        victim.setUpdatedAt(mock(Timestamp.class));
        victim.setCardNumber(0L);
        victim.setCreatedById(UUID.randomUUID());
        victim.setDeleted(true);
        victim.setName("createdAt");
        victim.setSurname("Doe");
        victim.setUpdateById(UUID.randomUUID());
        victim.setDead(true);
        victim.setCreatedAt(mock(Timestamp.class));
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        victim.setBirthDate(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        victim.setCrime(new ArrayList<Crime>());
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        victim.setDeathDate(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));

        ArrayList<Victim> victimList = new ArrayList<Victim>();
        victimList.add(victim);
        PageImpl<Victim> pageImpl = new PageImpl<Victim>(victimList);
        when(this.victimRepository.findAll((org.springframework.data.domain.Pageable) any())).thenReturn(pageImpl);
        CustomPage<VictimResponseDTO> actualAllVictims = this.victimServiceImpl.getAllVictims(1);
        assertEquals(1, actualAllVictims.getContent().size());
        assertEquals(1, actualAllVictims.getTotalPages());
        assertEquals(1L, actualAllVictims.getTotalElements());
        assertEquals(1, actualAllVictims.getSize());
        assertEquals(1, actualAllVictims.getNumberOfElements());
        assertEquals(0, actualAllVictims.getNumber());
        verify(this.victimRepository).findAll((org.springframework.data.domain.Pageable) any());
    }

    @Test
    void testGetAllVictims3() {
        Victim victim = new Victim();
        victim.setRemark("createdAt");
        victim.setId(UUID.randomUUID());
        victim.setUpdatedAt(mock(Timestamp.class));
        victim.setCardNumber(0L);
        victim.setCreatedById(UUID.randomUUID());
        victim.setDeleted(true);
        victim.setName("createdAt");
        victim.setSurname("Doe");
        victim.setUpdateById(UUID.randomUUID());
        victim.setDead(true);
        victim.setCreatedAt(mock(Timestamp.class));
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        victim.setBirthDate(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        victim.setCrime(new ArrayList<Crime>());
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        victim.setDeathDate(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));

        Victim victim1 = new Victim();
        victim1.setRemark("createdAt");
        victim1.setId(UUID.randomUUID());
        victim1.setUpdatedAt(mock(Timestamp.class));
        victim1.setCardNumber(0L);
        victim1.setCreatedById(UUID.randomUUID());
        victim1.setDeleted(true);
        victim1.setName("createdAt");
        victim1.setSurname("Doe");
        victim1.setUpdateById(UUID.randomUUID());
        victim1.setDead(true);
        victim1.setCreatedAt(mock(Timestamp.class));
        LocalDateTime atStartOfDayResult2 = LocalDate.of(1970, 1, 1).atStartOfDay();
        victim1.setBirthDate(Date.from(atStartOfDayResult2.atZone(ZoneId.of("UTC")).toInstant()));
        victim1.setCrime(new ArrayList<Crime>());
        LocalDateTime atStartOfDayResult3 = LocalDate.of(1970, 1, 1).atStartOfDay();
        victim1.setDeathDate(Date.from(atStartOfDayResult3.atZone(ZoneId.of("UTC")).toInstant()));

        ArrayList<Victim> victimList = new ArrayList<Victim>();
        victimList.add(victim1);
        victimList.add(victim);
        PageImpl<Victim> pageImpl = new PageImpl<Victim>(victimList);
        when(this.victimRepository.findAll((org.springframework.data.domain.Pageable) any())).thenReturn(pageImpl);
        CustomPage<VictimResponseDTO> actualAllVictims = this.victimServiceImpl.getAllVictims(1);
        assertEquals(2, actualAllVictims.getContent().size());
        assertEquals(1, actualAllVictims.getTotalPages());
        assertEquals(2L, actualAllVictims.getTotalElements());
        assertEquals(2, actualAllVictims.getSize());
        assertEquals(2, actualAllVictims.getNumberOfElements());
        assertEquals(0, actualAllVictims.getNumber());
        verify(this.victimRepository).findAll((org.springframework.data.domain.Pageable) any());
    }

    @Test
    void testGetAllVictims4() {
        when(this.victimRepository.findAll((org.springframework.data.domain.Pageable) any()))
                .thenReturn(new PageImpl<Victim>(new ArrayList<Victim>()));
        CustomPage<VictimResponseDTO> actualAllVictims = this.victimServiceImpl.getAllVictims(0);
        assertTrue(actualAllVictims.getContent().isEmpty());
        assertEquals(1, actualAllVictims.getTotalPages());
        assertEquals(0L, actualAllVictims.getTotalElements());
        assertEquals(0, actualAllVictims.getSize());
        assertEquals(0, actualAllVictims.getNumberOfElements());
        assertEquals(0, actualAllVictims.getNumber());
        verify(this.victimRepository).findAll((org.springframework.data.domain.Pageable) any());
    }

    @Test
    void testGetVictimById() {
        Victim victim = new Victim();
        victim.setRemark("Remark");
        UUID randomUUIDResult = UUID.randomUUID();
        victim.setId(randomUUIDResult);
        victim.setUpdatedAt(mock(Timestamp.class));
        victim.setCardNumber(1L);
        victim.setCreatedById(UUID.randomUUID());
        victim.setDeleted(true);
        victim.setName("Name");
        victim.setSurname("Doe");
        victim.setUpdateById(UUID.randomUUID());
        victim.setDead(true);
        victim.setCreatedAt(mock(Timestamp.class));
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        Date fromResult = Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant());
        victim.setBirthDate(fromResult);
        victim.setCrime(new ArrayList<Crime>());
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        Date fromResult1 = Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant());
        victim.setDeathDate(fromResult1);
        Optional<Victim> ofResult = Optional.<Victim>of(victim);
        when(this.victimRepository.findById((UUID) any())).thenReturn(ofResult);
        VictimResponseDTO actualVictimById = this.victimServiceImpl.getVictimById(UUID.randomUUID());
        assertSame(fromResult, actualVictimById.getBirthDate());
        assertTrue(actualVictimById.isDead());
        assertEquals("Doe", actualVictimById.getSurname());
        assertEquals("Remark", actualVictimById.getRemark());
        assertNull(actualVictimById.getPhotoId());
        assertEquals("Name", actualVictimById.getName());
        assertSame(randomUUIDResult, actualVictimById.getId());
        assertSame(fromResult1, actualVictimById.getDeathDate());
        assertEquals(1L, actualVictimById.getCardNumber().longValue());
        assertTrue(actualVictimById.getCrimes().isEmpty());
        verify(this.victimRepository).findById((UUID) any());
    }

    @Test
    void testGetVictimById2() {
        PoliceStation policeStation = new PoliceStation();
        policeStation.setRemark("Remark");
        policeStation.setId(UUID.randomUUID());
        policeStation.setUpdatedAt(mock(Timestamp.class));
        policeStation.setCreatedById(UUID.randomUUID());
        policeStation.setCreatedAt(mock(Timestamp.class));
        policeStation.setName("Name");
        policeStation.setDeleted(true);
        policeStation.setPhoneNumber("4105551212");
        policeStation.setAddress("42 Main St");
        policeStation.setUpdateById(UUID.randomUUID());

        Crime crime = new Crime();
        crime.setId(UUID.randomUUID());
        crime.setUpdatedAt(mock(Timestamp.class));
        crime.setCrimeReportStatus(CrimeReportStatus.PENDING);
        crime.setOfficers(new ArrayList<Officer>());
        crime.setCrimeType(CrimeType.FELONY);
        crime.setCreatedById(UUID.randomUUID());
        crime.setDeleted(true);
        crime.setName("Name");
        crime.setCrimeStatus(CrimeStatus.UNDER_INVESTIGATION);
        crime.setAddress("42 Main St");
        crime.setUpdateById(UUID.randomUUID());
        crime.setCreatedAt(mock(Timestamp.class));
        crime.setCrimeDescription("Crime Description");
        crime.setCrimeTime(LocalDateTime.of(1, 1, 1, 1, 1));
        crime.setPoliceStation(policeStation);

        ArrayList<Crime> crimeList = new ArrayList<Crime>();
        crimeList.add(crime);

        Victim victim = new Victim();
        victim.setRemark("Remark");
        UUID randomUUIDResult = UUID.randomUUID();
        victim.setId(randomUUIDResult);
        victim.setUpdatedAt(mock(Timestamp.class));
        victim.setCardNumber(1L);
        victim.setCreatedById(UUID.randomUUID());
        victim.setDeleted(true);
        victim.setName("Name");
        victim.setSurname("Doe");
        victim.setUpdateById(UUID.randomUUID());
        victim.setDead(true);
        victim.setCreatedAt(mock(Timestamp.class));
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        Date fromResult = Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant());
        victim.setBirthDate(fromResult);
        victim.setCrime(crimeList);
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        Date fromResult1 = Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant());
        victim.setDeathDate(fromResult1);
        Optional<Victim> ofResult = Optional.<Victim>of(victim);
        when(this.victimRepository.findById((UUID) any())).thenReturn(ofResult);
        VictimResponseDTO actualVictimById = this.victimServiceImpl.getVictimById(UUID.randomUUID());
        assertSame(fromResult, actualVictimById.getBirthDate());
        assertTrue(actualVictimById.isDead());
        assertEquals("Doe", actualVictimById.getSurname());
        assertEquals("Remark", actualVictimById.getRemark());
        assertNull(actualVictimById.getPhotoId());
        assertEquals("Name", actualVictimById.getName());
        assertSame(randomUUIDResult, actualVictimById.getId());
        assertSame(fromResult1, actualVictimById.getDeathDate());
        assertEquals(1L, actualVictimById.getCardNumber().longValue());
        assertEquals(1, actualVictimById.getCrimes().size());
        verify(this.victimRepository).findById((UUID) any());
    }

    @Test
    void testGetVictimById3() {
        PoliceStation policeStation = new PoliceStation();
        policeStation.setRemark("Remark");
        policeStation.setId(UUID.randomUUID());
        policeStation.setUpdatedAt(mock(Timestamp.class));
        policeStation.setCreatedById(UUID.randomUUID());
        policeStation.setCreatedAt(mock(Timestamp.class));
        policeStation.setName("Name");
        policeStation.setDeleted(true);
        policeStation.setPhoneNumber("4105551212");
        policeStation.setAddress("42 Main St");
        policeStation.setUpdateById(UUID.randomUUID());

        Crime crime = new Crime();
        crime.setId(UUID.randomUUID());
        crime.setUpdatedAt(mock(Timestamp.class));
        crime.setCrimeReportStatus(CrimeReportStatus.PENDING);
        crime.setOfficers(new ArrayList<Officer>());
        crime.setCrimeType(CrimeType.FELONY);
        crime.setCreatedById(UUID.randomUUID());
        crime.setDeleted(true);
        crime.setName("Name");
        crime.setCrimeStatus(CrimeStatus.UNDER_INVESTIGATION);
        crime.setAddress("42 Main St");
        crime.setUpdateById(UUID.randomUUID());
        crime.setCreatedAt(mock(Timestamp.class));
        crime.setCrimeDescription("Crime Description");
        crime.setCrimeTime(LocalDateTime.of(1, 1, 1, 1, 1));
        crime.setPoliceStation(policeStation);

        PoliceStation policeStation1 = new PoliceStation();
        policeStation1.setRemark("Remark");
        policeStation1.setId(UUID.randomUUID());
        policeStation1.setUpdatedAt(mock(Timestamp.class));
        policeStation1.setCreatedById(UUID.randomUUID());
        policeStation1.setCreatedAt(mock(Timestamp.class));
        policeStation1.setName("Name");
        policeStation1.setDeleted(true);
        policeStation1.setPhoneNumber("4105551212");
        policeStation1.setAddress("42 Main St");
        policeStation1.setUpdateById(UUID.randomUUID());

        Crime crime1 = new Crime();
        crime1.setId(UUID.randomUUID());
        crime1.setUpdatedAt(mock(Timestamp.class));
        crime1.setCrimeReportStatus(CrimeReportStatus.PENDING);
        crime1.setOfficers(new ArrayList<Officer>());
        crime1.setCrimeType(CrimeType.FELONY);
        crime1.setCreatedById(UUID.randomUUID());
        crime1.setDeleted(true);
        crime1.setName("Name");
        crime1.setCrimeStatus(CrimeStatus.UNDER_INVESTIGATION);
        crime1.setAddress("42 Main St");
        crime1.setUpdateById(UUID.randomUUID());
        crime1.setCreatedAt(mock(Timestamp.class));
        crime1.setCrimeDescription("Crime Description");
        crime1.setCrimeTime(LocalDateTime.of(1, 1, 1, 1, 1));
        crime1.setPoliceStation(policeStation1);

        ArrayList<Crime> crimeList = new ArrayList<Crime>();
        crimeList.add(crime1);
        crimeList.add(crime);

        Victim victim = new Victim();
        victim.setRemark("Remark");
        UUID randomUUIDResult = UUID.randomUUID();
        victim.setId(randomUUIDResult);
        victim.setUpdatedAt(mock(Timestamp.class));
        victim.setCardNumber(1L);
        victim.setCreatedById(UUID.randomUUID());
        victim.setDeleted(true);
        victim.setName("Name");
        victim.setSurname("Doe");
        victim.setUpdateById(UUID.randomUUID());
        victim.setDead(true);
        victim.setCreatedAt(mock(Timestamp.class));
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        Date fromResult = Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant());
        victim.setBirthDate(fromResult);
        victim.setCrime(crimeList);
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        Date fromResult1 = Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant());
        victim.setDeathDate(fromResult1);
        Optional<Victim> ofResult = Optional.<Victim>of(victim);
        when(this.victimRepository.findById((UUID) any())).thenReturn(ofResult);
        VictimResponseDTO actualVictimById = this.victimServiceImpl.getVictimById(UUID.randomUUID());
        assertSame(fromResult, actualVictimById.getBirthDate());
        assertTrue(actualVictimById.isDead());
        assertEquals("Doe", actualVictimById.getSurname());
        assertEquals("Remark", actualVictimById.getRemark());
        assertNull(actualVictimById.getPhotoId());
        assertEquals("Name", actualVictimById.getName());
        assertSame(randomUUIDResult, actualVictimById.getId());
        assertSame(fromResult1, actualVictimById.getDeathDate());
        assertEquals(1L, actualVictimById.getCardNumber().longValue());
        assertEquals(2, actualVictimById.getCrimes().size());
        verify(this.victimRepository).findById((UUID) any());
    }

    @Test
    void testGetVictimById4() {
        when(this.victimRepository.findById((UUID) any())).thenReturn(Optional.<Victim>empty());
        assertThrows(RestException.class, () -> this.victimServiceImpl.getVictimById(UUID.randomUUID()));
        verify(this.victimRepository).findById((UUID) any());
    }

    @Test
    void testGetVictimById5() {
        PoliceStation policeStation = new PoliceStation();
        policeStation.setRemark("Remark");
        policeStation.setId(UUID.randomUUID());
        policeStation.setUpdatedAt(mock(Timestamp.class));
        policeStation.setCreatedById(UUID.randomUUID());
        policeStation.setCreatedAt(mock(Timestamp.class));
        policeStation.setName("Name");
        policeStation.setDeleted(true);
        policeStation.setPhoneNumber("4105551212");
        policeStation.setAddress("42 Main St");
        policeStation.setUpdateById(UUID.randomUUID());

        Officer officer = new Officer();
        officer.setLastName("Doe");
        officer.setId(UUID.randomUUID());
        officer.setUpdatedAt(mock(Timestamp.class));
        officer.setCreatedById(UUID.randomUUID());
        officer.setCreatedAt(mock(Timestamp.class));
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        officer.setBirthDate(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        officer.setDeleted(true);
        officer.setCardNumber(0L);
        officer.setCertificate(0L);
        officer.setFirstName("Jane");
        officer.setUpdateById(UUID.randomUUID());
        officer.setRank(OfficerRank.DETECTIVE);
        officer.setPoliceStation(policeStation);

        ArrayList<Officer> officerList = new ArrayList<Officer>();
        officerList.add(officer);

        PoliceStation policeStation1 = new PoliceStation();
        policeStation1.setRemark("Remark");
        policeStation1.setId(UUID.randomUUID());
        policeStation1.setUpdatedAt(mock(Timestamp.class));
        policeStation1.setCreatedById(UUID.randomUUID());
        policeStation1.setCreatedAt(mock(Timestamp.class));
        policeStation1.setName("Name");
        policeStation1.setDeleted(true);
        policeStation1.setPhoneNumber("4105551212");
        policeStation1.setAddress("42 Main St");
        policeStation1.setUpdateById(UUID.randomUUID());

        Crime crime = new Crime();
        crime.setId(UUID.randomUUID());
        crime.setUpdatedAt(mock(Timestamp.class));
        crime.setCrimeReportStatus(CrimeReportStatus.PENDING);
        crime.setOfficers(officerList);
        crime.setCrimeType(CrimeType.FELONY);
        crime.setCreatedById(UUID.randomUUID());
        crime.setDeleted(true);
        crime.setName("Name");
        crime.setCrimeStatus(CrimeStatus.UNDER_INVESTIGATION);
        crime.setAddress("42 Main St");
        crime.setUpdateById(UUID.randomUUID());
        crime.setCreatedAt(mock(Timestamp.class));
        crime.setCrimeDescription("Crime Description");
        crime.setCrimeTime(LocalDateTime.of(1, 1, 1, 1, 1));
        crime.setPoliceStation(policeStation1);

        ArrayList<Crime> crimeList = new ArrayList<Crime>();
        crimeList.add(crime);

        Victim victim = new Victim();
        victim.setRemark("Remark");
        UUID randomUUIDResult = UUID.randomUUID();
        victim.setId(randomUUIDResult);
        victim.setUpdatedAt(mock(Timestamp.class));
        victim.setCardNumber(1L);
        victim.setCreatedById(UUID.randomUUID());
        victim.setDeleted(true);
        victim.setName("Name");
        victim.setSurname("Doe");
        victim.setUpdateById(UUID.randomUUID());
        victim.setDead(true);
        victim.setCreatedAt(mock(Timestamp.class));
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        Date fromResult = Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant());
        victim.setBirthDate(fromResult);
        victim.setCrime(crimeList);
        LocalDateTime atStartOfDayResult2 = LocalDate.of(1970, 1, 1).atStartOfDay();
        Date fromResult1 = Date.from(atStartOfDayResult2.atZone(ZoneId.of("UTC")).toInstant());
        victim.setDeathDate(fromResult1);
        Optional<Victim> ofResult = Optional.<Victim>of(victim);
        when(this.victimRepository.findById((UUID) any())).thenReturn(ofResult);
        VictimResponseDTO actualVictimById = this.victimServiceImpl.getVictimById(UUID.randomUUID());
        assertSame(fromResult, actualVictimById.getBirthDate());
        assertTrue(actualVictimById.isDead());
        assertEquals("Doe", actualVictimById.getSurname());
        assertEquals("Remark", actualVictimById.getRemark());
        assertNull(actualVictimById.getPhotoId());
        assertEquals("Name", actualVictimById.getName());
        assertSame(randomUUIDResult, actualVictimById.getId());
        assertSame(fromResult1, actualVictimById.getDeathDate());
        assertEquals(1L, actualVictimById.getCardNumber().longValue());
        assertEquals(1, actualVictimById.getCrimes().size());
        verify(this.victimRepository).findById((UUID) any());
    }

    @Test
    void testGetVictimById6() {
        PoliceStation policeStation = new PoliceStation();
        policeStation.setRemark("Remark");
        policeStation.setId(UUID.randomUUID());
        policeStation.setUpdatedAt(mock(Timestamp.class));
        policeStation.setCreatedById(UUID.randomUUID());
        policeStation.setCreatedAt(mock(Timestamp.class));
        policeStation.setName("Name");
        policeStation.setDeleted(true);
        policeStation.setPhoneNumber("4105551212");
        policeStation.setAddress("42 Main St");
        policeStation.setUpdateById(UUID.randomUUID());

        Officer officer = new Officer();
        officer.setLastName("Doe");
        officer.setId(UUID.randomUUID());
        officer.setUpdatedAt(mock(Timestamp.class));
        officer.setCreatedById(UUID.randomUUID());
        officer.setCreatedAt(mock(Timestamp.class));
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        officer.setBirthDate(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        officer.setDeleted(true);
        officer.setCardNumber(0L);
        officer.setCertificate(0L);
        officer.setFirstName("Jane");
        officer.setUpdateById(UUID.randomUUID());
        officer.setRank(OfficerRank.DETECTIVE);
        officer.setPoliceStation(policeStation);

        PoliceStation policeStation1 = new PoliceStation();
        policeStation1.setRemark("Remark");
        policeStation1.setId(UUID.randomUUID());
        policeStation1.setUpdatedAt(mock(Timestamp.class));
        policeStation1.setCreatedById(UUID.randomUUID());
        policeStation1.setCreatedAt(mock(Timestamp.class));
        policeStation1.setName("Name");
        policeStation1.setDeleted(true);
        policeStation1.setPhoneNumber("4105551212");
        policeStation1.setAddress("42 Main St");
        policeStation1.setUpdateById(UUID.randomUUID());

        Officer officer1 = new Officer();
        officer1.setLastName("Doe");
        officer1.setId(UUID.randomUUID());
        officer1.setUpdatedAt(mock(Timestamp.class));
        officer1.setCreatedById(UUID.randomUUID());
        officer1.setCreatedAt(mock(Timestamp.class));
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        officer1.setBirthDate(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        officer1.setDeleted(true);
        officer1.setCardNumber(0L);
        officer1.setCertificate(0L);
        officer1.setFirstName("Jane");
        officer1.setUpdateById(UUID.randomUUID());
        officer1.setRank(OfficerRank.DETECTIVE);
        officer1.setPoliceStation(policeStation1);

        ArrayList<Officer> officerList = new ArrayList<Officer>();
        officerList.add(officer1);
        officerList.add(officer);

        PoliceStation policeStation2 = new PoliceStation();
        policeStation2.setRemark("Remark");
        policeStation2.setId(UUID.randomUUID());
        policeStation2.setUpdatedAt(mock(Timestamp.class));
        policeStation2.setCreatedById(UUID.randomUUID());
        policeStation2.setCreatedAt(mock(Timestamp.class));
        policeStation2.setName("Name");
        policeStation2.setDeleted(true);
        policeStation2.setPhoneNumber("4105551212");
        policeStation2.setAddress("42 Main St");
        policeStation2.setUpdateById(UUID.randomUUID());

        Crime crime = new Crime();
        crime.setId(UUID.randomUUID());
        crime.setUpdatedAt(mock(Timestamp.class));
        crime.setCrimeReportStatus(CrimeReportStatus.PENDING);
        crime.setOfficers(officerList);
        crime.setCrimeType(CrimeType.FELONY);
        crime.setCreatedById(UUID.randomUUID());
        crime.setDeleted(true);
        crime.setName("Name");
        crime.setCrimeStatus(CrimeStatus.UNDER_INVESTIGATION);
        crime.setAddress("42 Main St");
        crime.setUpdateById(UUID.randomUUID());
        crime.setCreatedAt(mock(Timestamp.class));
        crime.setCrimeDescription("Crime Description");
        crime.setCrimeTime(LocalDateTime.of(1, 1, 1, 1, 1));
        crime.setPoliceStation(policeStation2);

        ArrayList<Crime> crimeList = new ArrayList<Crime>();
        crimeList.add(crime);

        Victim victim = new Victim();
        victim.setRemark("Remark");
        UUID randomUUIDResult = UUID.randomUUID();
        victim.setId(randomUUIDResult);
        victim.setUpdatedAt(mock(Timestamp.class));
        victim.setCardNumber(1L);
        victim.setCreatedById(UUID.randomUUID());
        victim.setDeleted(true);
        victim.setName("Name");
        victim.setSurname("Doe");
        victim.setUpdateById(UUID.randomUUID());
        victim.setDead(true);
        victim.setCreatedAt(mock(Timestamp.class));
        LocalDateTime atStartOfDayResult2 = LocalDate.of(1970, 1, 1).atStartOfDay();
        Date fromResult = Date.from(atStartOfDayResult2.atZone(ZoneId.of("UTC")).toInstant());
        victim.setBirthDate(fromResult);
        victim.setCrime(crimeList);
        LocalDateTime atStartOfDayResult3 = LocalDate.of(1970, 1, 1).atStartOfDay();
        Date fromResult1 = Date.from(atStartOfDayResult3.atZone(ZoneId.of("UTC")).toInstant());
        victim.setDeathDate(fromResult1);
        Optional<Victim> ofResult = Optional.<Victim>of(victim);
        when(this.victimRepository.findById((UUID) any())).thenReturn(ofResult);
        VictimResponseDTO actualVictimById = this.victimServiceImpl.getVictimById(UUID.randomUUID());
        assertSame(fromResult, actualVictimById.getBirthDate());
        assertTrue(actualVictimById.isDead());
        assertEquals("Doe", actualVictimById.getSurname());
        assertEquals("Remark", actualVictimById.getRemark());
        assertNull(actualVictimById.getPhotoId());
        assertEquals("Name", actualVictimById.getName());
        assertSame(randomUUIDResult, actualVictimById.getId());
        assertSame(fromResult1, actualVictimById.getDeathDate());
        assertEquals(1L, actualVictimById.getCardNumber().longValue());
        assertEquals(1, actualVictimById.getCrimes().size());
        verify(this.victimRepository).findById((UUID) any());
    }

    @Test
    void testAddVictim() {
        when(this.victimRepository.existsByCardNumber((Long) any())).thenReturn(true);
        assertThrows(RestException.class, () -> this.victimServiceImpl.addVictim(new VictimDTO()));
        verify(this.victimRepository).existsByCardNumber((Long) any());
    }

    @Test
    void testAddVictim2() {
        Victim victim = new Victim();
        victim.setRemark("Remark");
        victim.setId(UUID.randomUUID());
        victim.setUpdatedAt(mock(Timestamp.class));
        victim.setCardNumber(1L);
        victim.setCreatedById(UUID.randomUUID());
        victim.setDeleted(true);
        victim.setName("Name");
        victim.setSurname("Doe");
        victim.setUpdateById(UUID.randomUUID());
        victim.setDead(true);
        victim.setCreatedAt(mock(Timestamp.class));
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        victim.setBirthDate(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        victim.setCrime(new ArrayList<Crime>());
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        victim.setDeathDate(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        when(this.victimRepository.save((Victim) any())).thenReturn(victim);
        when(this.victimRepository.existsByCardNumber((Long) any())).thenReturn(false);

        CitizenDTO citizenDTO = new CitizenDTO();
        citizenDTO.setCardNumber(1L);
        LocalDateTime atStartOfDayResult2 = LocalDate.of(1970, 1, 1).atStartOfDay();
        citizenDTO.setBirthDate(Date.from(atStartOfDayResult2.atZone(ZoneId.of("UTC")).toInstant()));
        citizenDTO.setFirstName("Jane");
        citizenDTO.setSurname("Doe");
        when(this.citizenExternalApiServiceImpl.getCrimesWithId((List<UUID>) any())).thenReturn(new ArrayList<Crime>());
        when(this.citizenExternalApiServiceImpl.getCitizenByCardNumber(anyLong())).thenReturn(citizenDTO);
        ApiResponse actualAddVictimResult = this.victimServiceImpl.addVictim(new VictimDTO());
        assertEquals("Victim Saved Successfully", actualAddVictimResult.getMessage());
        assertTrue(actualAddVictimResult.isSuccess());
        verify(this.victimRepository).existsByCardNumber((Long) any());
        verify(this.victimRepository).save((Victim) any());
        verify(this.citizenExternalApiServiceImpl).getCitizenByCardNumber(anyLong());
        verify(this.citizenExternalApiServiceImpl).getCrimesWithId((List<UUID>) any());
    }

    @Test
    void testAddVictim3() {
        Victim victim = new Victim();
        victim.setRemark("Remark");
        victim.setId(UUID.randomUUID());
        victim.setUpdatedAt(mock(Timestamp.class));
        victim.setCardNumber(1L);
        victim.setCreatedById(UUID.randomUUID());
        victim.setDeleted(true);
        victim.setName("Name");
        victim.setSurname("Doe");
        victim.setUpdateById(UUID.randomUUID());
        victim.setDead(true);
        victim.setCreatedAt(mock(Timestamp.class));
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        victim.setBirthDate(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        victim.setCrime(new ArrayList<Crime>());
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        victim.setDeathDate(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        when(this.victimRepository.save((Victim) any())).thenReturn(victim);
        when(this.victimRepository.existsByCardNumber((Long) any())).thenReturn(false);

        CitizenDTO citizenDTO = new CitizenDTO();
        citizenDTO.setCardNumber(1L);
        LocalDateTime atStartOfDayResult2 = LocalDate.of(1970, 1, 1).atStartOfDay();
        citizenDTO.setBirthDate(Date.from(atStartOfDayResult2.atZone(ZoneId.of("UTC")).toInstant()));
        citizenDTO.setFirstName("Jane");
        citizenDTO.setSurname("Doe");
        doNothing().when(this.citizenExternalApiServiceImpl).sendDeathPersonToCityManagement(anyLong());
        when(this.citizenExternalApiServiceImpl.getCrimesWithId((List<UUID>) any())).thenReturn(new ArrayList<Crime>());
        when(this.citizenExternalApiServiceImpl.getCitizenByCardNumber(anyLong())).thenReturn(citizenDTO);
        Date deathDate = new Date(1L);
        ApiResponse actualAddVictimResult = this.victimServiceImpl
                .addVictim(new VictimDTO(1L, deathDate, "Remark", true, new ArrayList<UUID>()));
        assertEquals("Victim Saved Successfully", actualAddVictimResult.getMessage());
        assertTrue(actualAddVictimResult.isSuccess());
        verify(this.victimRepository).existsByCardNumber((Long) any());
        verify(this.victimRepository).save((Victim) any());
        verify(this.citizenExternalApiServiceImpl).getCitizenByCardNumber(anyLong());
        verify(this.citizenExternalApiServiceImpl).getCrimesWithId((List<UUID>) any());
        verify(this.citizenExternalApiServiceImpl).sendDeathPersonToCityManagement(anyLong());
    }

    @Test
    void testEditVictim() {
        Victim victim = new Victim();
        victim.setRemark("Remark");
        victim.setId(UUID.randomUUID());
        victim.setUpdatedAt(mock(Timestamp.class));
        victim.setCardNumber(1L);
        victim.setCreatedById(UUID.randomUUID());
        victim.setDeleted(true);
        victim.setName("Name");
        victim.setSurname("Doe");
        victim.setUpdateById(UUID.randomUUID());
        victim.setDead(true);
        victim.setCreatedAt(mock(Timestamp.class));
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        victim.setBirthDate(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        victim.setCrime(new ArrayList<Crime>());
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        victim.setDeathDate(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        Optional<Victim> ofResult = Optional.<Victim>of(victim);
        when(this.victimRepository.existsByCardNumberAndIdNot((Long) any(), (UUID) any())).thenReturn(true);
        when(this.victimRepository.findById((UUID) any())).thenReturn(ofResult);
        UUID id = UUID.randomUUID();
        assertThrows(RestException.class, () -> this.victimServiceImpl.editVictim(id, new VictimDTO()));
        verify(this.victimRepository).existsByCardNumberAndIdNot((Long) any(), (UUID) any());
        verify(this.victimRepository).findById((UUID) any());
    }

    @Test
    void testEditVictim2() {
        Victim victim = new Victim();
        victim.setRemark("Remark");
        victim.setId(UUID.randomUUID());
        victim.setUpdatedAt(mock(Timestamp.class));
        victim.setCardNumber(1L);
        victim.setCreatedById(UUID.randomUUID());
        victim.setDeleted(true);
        victim.setName("Name");
        victim.setSurname("Doe");
        victim.setUpdateById(UUID.randomUUID());
        victim.setDead(true);
        victim.setCreatedAt(mock(Timestamp.class));
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        victim.setBirthDate(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        victim.setCrime(new ArrayList<Crime>());
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        victim.setDeathDate(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        Optional<Victim> ofResult = Optional.<Victim>of(victim);

        Victim victim1 = new Victim();
        victim1.setRemark("Remark");
        victim1.setId(UUID.randomUUID());
        victim1.setUpdatedAt(mock(Timestamp.class));
        victim1.setCardNumber(1L);
        victim1.setCreatedById(UUID.randomUUID());
        victim1.setDeleted(true);
        victim1.setName("Name");
        victim1.setSurname("Doe");
        victim1.setUpdateById(UUID.randomUUID());
        victim1.setDead(true);
        victim1.setCreatedAt(mock(Timestamp.class));
        LocalDateTime atStartOfDayResult2 = LocalDate.of(1970, 1, 1).atStartOfDay();
        victim1.setBirthDate(Date.from(atStartOfDayResult2.atZone(ZoneId.of("UTC")).toInstant()));
        victim1.setCrime(new ArrayList<Crime>());
        LocalDateTime atStartOfDayResult3 = LocalDate.of(1970, 1, 1).atStartOfDay();
        victim1.setDeathDate(Date.from(atStartOfDayResult3.atZone(ZoneId.of("UTC")).toInstant()));
        when(this.victimRepository.save((Victim) any())).thenReturn(victim1);
        when(this.victimRepository.existsByCardNumberAndIdNot((Long) any(), (UUID) any())).thenReturn(false);
        when(this.victimRepository.findById((UUID) any())).thenReturn(ofResult);

        CitizenDTO citizenDTO = new CitizenDTO();
        citizenDTO.setCardNumber(1L);
        LocalDateTime atStartOfDayResult4 = LocalDate.of(1970, 1, 1).atStartOfDay();
        citizenDTO.setBirthDate(Date.from(atStartOfDayResult4.atZone(ZoneId.of("UTC")).toInstant()));
        citizenDTO.setFirstName("Jane");
        citizenDTO.setSurname("Doe");
        when(this.citizenExternalApiServiceImpl.getCrimesWithId((List<UUID>) any())).thenReturn(new ArrayList<Crime>());
        when(this.citizenExternalApiServiceImpl.getCitizenByCardNumber(anyLong())).thenReturn(citizenDTO);
        UUID id = UUID.randomUUID();
        ApiResponse actualEditVictimResult = this.victimServiceImpl.editVictim(id, new VictimDTO());
        assertEquals("Victim Updated Successfully", actualEditVictimResult.getMessage());
        assertTrue(actualEditVictimResult.isSuccess());
        verify(this.victimRepository).existsByCardNumberAndIdNot((Long) any(), (UUID) any());
        verify(this.victimRepository).findById((UUID) any());
        verify(this.victimRepository).save((Victim) any());
        verify(this.citizenExternalApiServiceImpl).getCitizenByCardNumber(anyLong());
        verify(this.citizenExternalApiServiceImpl).getCrimesWithId((List<UUID>) any());
    }

    @Test
    void testDeleteVictim() {
        doNothing().when(this.victimRepository).deleteById((UUID) any());
        ApiResponse actualDeleteVictimResult = this.victimServiceImpl.deleteVictim(UUID.randomUUID());
        assertEquals("Successfully deleted", actualDeleteVictimResult.getMessage());
        assertTrue(actualDeleteVictimResult.isSuccess());
        verify(this.victimRepository).deleteById((UUID) any());
    }

    @Test
    void testDeleteVictim2() {
        doThrow(new RestException("An error occurred", HttpStatus.CONTINUE)).when(this.victimRepository)
                .deleteById((UUID) any());
        assertThrows(RestException.class, () -> this.victimServiceImpl.deleteVictim(UUID.randomUUID()));
        verify(this.victimRepository).deleteById((UUID) any());
    }
}

