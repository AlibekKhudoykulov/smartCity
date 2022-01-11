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
import com.example.smartcity.entity.Witness;
import com.example.smartcity.entity.enums.CrimeReportStatus;
import com.example.smartcity.entity.enums.CrimeStatus;
import com.example.smartcity.entity.enums.CrimeType;
import com.example.smartcity.entity.enums.OfficerRank;
import com.example.smartcity.exception.RestException;
import com.example.smartcity.payload.ApiResponse;
import com.example.smartcity.payload.CitizenDTO;
import com.example.smartcity.payload.WitnessDTO;
import com.example.smartcity.payload.responseDTO.CustomPage;
import com.example.smartcity.payload.responseDTO.WitnessResponseDTO;
import com.example.smartcity.repository.WitnessRepository;
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

@ContextConfiguration(classes = {WitnessServiceImpl.class, CitizenExternalApiServiceImpl.class, Mappers.class})
@ExtendWith(SpringExtension.class)
class WitnessServiceImplTest {
    @MockBean
    private CitizenExternalApiServiceImpl citizenExternalApiServiceImpl;

    @MockBean
    private Mappers mappers;

    @MockBean
    private WitnessRepository witnessRepository;

    @Autowired
    private WitnessServiceImpl witnessServiceImpl;

    @Test
    void testGetAllWitnesses() {
        when(this.witnessRepository.findAll((org.springframework.data.domain.Pageable) any()))
                .thenReturn(new PageImpl<Witness>(new ArrayList<Witness>()));
        CustomPage<WitnessResponseDTO> actualAllWitnesses = this.witnessServiceImpl.getAllWitnesses(1);
        assertTrue(actualAllWitnesses.getContent().isEmpty());
        assertEquals(1, actualAllWitnesses.getTotalPages());
        assertEquals(0L, actualAllWitnesses.getTotalElements());
        assertEquals(0, actualAllWitnesses.getSize());
        assertEquals(0, actualAllWitnesses.getNumberOfElements());
        assertEquals(0, actualAllWitnesses.getNumber());
        verify(this.witnessRepository).findAll((org.springframework.data.domain.Pageable) any());
    }

    @Test
    void testGetAllWitnesses2() {
        Witness witness = new Witness();
        witness.setRemark("createdAt");
        witness.setId(UUID.randomUUID());
        witness.setUpdatedAt(mock(Timestamp.class));
        witness.setCardNumber(0L);
        witness.setCreatedById(UUID.randomUUID());
        witness.setCreatedAt(mock(Timestamp.class));
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        witness.setBirthDate(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        witness.setCrime(new ArrayList<Crime>());
        witness.setDeleted(true);
        witness.setPhoneNumber("4105551212");
        witness.setSurname("Doe");
        witness.setFirstName("Jane");
        witness.setUpdateById(UUID.randomUUID());

        ArrayList<Witness> witnessList = new ArrayList<Witness>();
        witnessList.add(witness);
        PageImpl<Witness> pageImpl = new PageImpl<Witness>(witnessList);
        when(this.witnessRepository.findAll((org.springframework.data.domain.Pageable) any())).thenReturn(pageImpl);
        CustomPage<WitnessResponseDTO> actualAllWitnesses = this.witnessServiceImpl.getAllWitnesses(1);
        assertEquals(1, actualAllWitnesses.getContent().size());
        assertEquals(1, actualAllWitnesses.getTotalPages());
        assertEquals(1L, actualAllWitnesses.getTotalElements());
        assertEquals(1, actualAllWitnesses.getSize());
        assertEquals(1, actualAllWitnesses.getNumberOfElements());
        assertEquals(0, actualAllWitnesses.getNumber());
        verify(this.witnessRepository).findAll((org.springframework.data.domain.Pageable) any());
    }

    @Test
    void testGetAllWitnesses3() {
        Witness witness = new Witness();
        witness.setRemark("createdAt");
        witness.setId(UUID.randomUUID());
        witness.setUpdatedAt(mock(Timestamp.class));
        witness.setCardNumber(0L);
        witness.setCreatedById(UUID.randomUUID());
        witness.setCreatedAt(mock(Timestamp.class));
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        witness.setBirthDate(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        witness.setCrime(new ArrayList<Crime>());
        witness.setDeleted(true);
        witness.setPhoneNumber("4105551212");
        witness.setSurname("Doe");
        witness.setFirstName("Jane");
        witness.setUpdateById(UUID.randomUUID());

        Witness witness1 = new Witness();
        witness1.setRemark("createdAt");
        witness1.setId(UUID.randomUUID());
        witness1.setUpdatedAt(mock(Timestamp.class));
        witness1.setCardNumber(0L);
        witness1.setCreatedById(UUID.randomUUID());
        witness1.setCreatedAt(mock(Timestamp.class));
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        witness1.setBirthDate(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        witness1.setCrime(new ArrayList<Crime>());
        witness1.setDeleted(true);
        witness1.setPhoneNumber("4105551212");
        witness1.setSurname("Doe");
        witness1.setFirstName("Jane");
        witness1.setUpdateById(UUID.randomUUID());

        ArrayList<Witness> witnessList = new ArrayList<Witness>();
        witnessList.add(witness1);
        witnessList.add(witness);
        PageImpl<Witness> pageImpl = new PageImpl<Witness>(witnessList);
        when(this.witnessRepository.findAll((org.springframework.data.domain.Pageable) any())).thenReturn(pageImpl);
        CustomPage<WitnessResponseDTO> actualAllWitnesses = this.witnessServiceImpl.getAllWitnesses(1);
        assertEquals(2, actualAllWitnesses.getContent().size());
        assertEquals(1, actualAllWitnesses.getTotalPages());
        assertEquals(2L, actualAllWitnesses.getTotalElements());
        assertEquals(2, actualAllWitnesses.getSize());
        assertEquals(2, actualAllWitnesses.getNumberOfElements());
        assertEquals(0, actualAllWitnesses.getNumber());
        verify(this.witnessRepository).findAll((org.springframework.data.domain.Pageable) any());
    }

    @Test
    void testGetAllWitnesses4() {
        when(this.witnessRepository.findAll((org.springframework.data.domain.Pageable) any()))
                .thenReturn(new PageImpl<Witness>(new ArrayList<Witness>()));
        CustomPage<WitnessResponseDTO> actualAllWitnesses = this.witnessServiceImpl.getAllWitnesses(0);
        assertTrue(actualAllWitnesses.getContent().isEmpty());
        assertEquals(1, actualAllWitnesses.getTotalPages());
        assertEquals(0L, actualAllWitnesses.getTotalElements());
        assertEquals(0, actualAllWitnesses.getSize());
        assertEquals(0, actualAllWitnesses.getNumberOfElements());
        assertEquals(0, actualAllWitnesses.getNumber());
        verify(this.witnessRepository).findAll((org.springframework.data.domain.Pageable) any());
    }

    @Test
    void testGetWitnessById() {
        Witness witness = new Witness();
        witness.setRemark("Remark");
        UUID randomUUIDResult = UUID.randomUUID();
        witness.setId(randomUUIDResult);
        witness.setUpdatedAt(mock(Timestamp.class));
        witness.setCardNumber(1L);
        witness.setCreatedById(UUID.randomUUID());
        witness.setCreatedAt(mock(Timestamp.class));
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        Date fromResult = Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant());
        witness.setBirthDate(fromResult);
        witness.setCrime(new ArrayList<Crime>());
        witness.setDeleted(true);
        witness.setPhoneNumber("4105551212");
        witness.setSurname("Doe");
        witness.setFirstName("Jane");
        witness.setUpdateById(UUID.randomUUID());
        Optional<Witness> ofResult = Optional.<Witness>of(witness);
        when(this.witnessRepository.findById((UUID) any())).thenReturn(ofResult);
        WitnessResponseDTO actualWitnessById = this.witnessServiceImpl.getWitnessById(UUID.randomUUID());
        assertSame(fromResult, actualWitnessById.getBirthDate());
        assertEquals("Doe", actualWitnessById.getSurname());
        assertEquals("Remark", actualWitnessById.getRemark());
        assertNull(actualWitnessById.getPhotoId());
        assertEquals("4105551212", actualWitnessById.getPhoneNumber());
        assertSame(randomUUIDResult, actualWitnessById.getId());
        assertEquals("Jane", actualWitnessById.getFirstName());
        assertEquals(1L, actualWitnessById.getCardNumber().longValue());
        assertTrue(actualWitnessById.getCrime().isEmpty());
        verify(this.witnessRepository).findById((UUID) any());
    }

    @Test
    void testGetWitnessById2() {
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

        Witness witness = new Witness();
        witness.setRemark("Remark");
        UUID randomUUIDResult = UUID.randomUUID();
        witness.setId(randomUUIDResult);
        witness.setUpdatedAt(mock(Timestamp.class));
        witness.setCardNumber(1L);
        witness.setCreatedById(UUID.randomUUID());
        witness.setCreatedAt(mock(Timestamp.class));
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        Date fromResult = Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant());
        witness.setBirthDate(fromResult);
        witness.setCrime(crimeList);
        witness.setDeleted(true);
        witness.setPhoneNumber("4105551212");
        witness.setSurname("Doe");
        witness.setFirstName("Jane");
        witness.setUpdateById(UUID.randomUUID());
        Optional<Witness> ofResult = Optional.<Witness>of(witness);
        when(this.witnessRepository.findById((UUID) any())).thenReturn(ofResult);
        WitnessResponseDTO actualWitnessById = this.witnessServiceImpl.getWitnessById(UUID.randomUUID());
        assertSame(fromResult, actualWitnessById.getBirthDate());
        assertEquals("Doe", actualWitnessById.getSurname());
        assertEquals("Remark", actualWitnessById.getRemark());
        assertNull(actualWitnessById.getPhotoId());
        assertEquals("4105551212", actualWitnessById.getPhoneNumber());
        assertSame(randomUUIDResult, actualWitnessById.getId());
        assertEquals("Jane", actualWitnessById.getFirstName());
        assertEquals(1L, actualWitnessById.getCardNumber().longValue());
        assertEquals(1, actualWitnessById.getCrime().size());
        verify(this.witnessRepository).findById((UUID) any());
    }

    @Test
    void testGetWitnessById3() {
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

        Witness witness = new Witness();
        witness.setRemark("Remark");
        UUID randomUUIDResult = UUID.randomUUID();
        witness.setId(randomUUIDResult);
        witness.setUpdatedAt(mock(Timestamp.class));
        witness.setCardNumber(1L);
        witness.setCreatedById(UUID.randomUUID());
        witness.setCreatedAt(mock(Timestamp.class));
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        Date fromResult = Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant());
        witness.setBirthDate(fromResult);
        witness.setCrime(crimeList);
        witness.setDeleted(true);
        witness.setPhoneNumber("4105551212");
        witness.setSurname("Doe");
        witness.setFirstName("Jane");
        witness.setUpdateById(UUID.randomUUID());
        Optional<Witness> ofResult = Optional.<Witness>of(witness);
        when(this.witnessRepository.findById((UUID) any())).thenReturn(ofResult);
        WitnessResponseDTO actualWitnessById = this.witnessServiceImpl.getWitnessById(UUID.randomUUID());
        assertSame(fromResult, actualWitnessById.getBirthDate());
        assertEquals("Doe", actualWitnessById.getSurname());
        assertEquals("Remark", actualWitnessById.getRemark());
        assertNull(actualWitnessById.getPhotoId());
        assertEquals("4105551212", actualWitnessById.getPhoneNumber());
        assertSame(randomUUIDResult, actualWitnessById.getId());
        assertEquals("Jane", actualWitnessById.getFirstName());
        assertEquals(1L, actualWitnessById.getCardNumber().longValue());
        assertEquals(2, actualWitnessById.getCrime().size());
        verify(this.witnessRepository).findById((UUID) any());
    }

    @Test
    void testGetWitnessById4() {
        when(this.witnessRepository.findById((UUID) any())).thenReturn(Optional.<Witness>empty());
        assertThrows(RestException.class, () -> this.witnessServiceImpl.getWitnessById(UUID.randomUUID()));
        verify(this.witnessRepository).findById((UUID) any());
    }

    @Test
    void testGetWitnessById5() {
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

        Witness witness = new Witness();
        witness.setRemark("Remark");
        UUID randomUUIDResult = UUID.randomUUID();
        witness.setId(randomUUIDResult);
        witness.setUpdatedAt(mock(Timestamp.class));
        witness.setCardNumber(1L);
        witness.setCreatedById(UUID.randomUUID());
        witness.setCreatedAt(mock(Timestamp.class));
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        Date fromResult = Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant());
        witness.setBirthDate(fromResult);
        witness.setCrime(crimeList);
        witness.setDeleted(true);
        witness.setPhoneNumber("4105551212");
        witness.setSurname("Doe");
        witness.setFirstName("Jane");
        witness.setUpdateById(UUID.randomUUID());
        Optional<Witness> ofResult = Optional.<Witness>of(witness);
        when(this.witnessRepository.findById((UUID) any())).thenReturn(ofResult);
        WitnessResponseDTO actualWitnessById = this.witnessServiceImpl.getWitnessById(UUID.randomUUID());
        assertSame(fromResult, actualWitnessById.getBirthDate());
        assertEquals("Doe", actualWitnessById.getSurname());
        assertEquals("Remark", actualWitnessById.getRemark());
        assertNull(actualWitnessById.getPhotoId());
        assertEquals("4105551212", actualWitnessById.getPhoneNumber());
        assertSame(randomUUIDResult, actualWitnessById.getId());
        assertEquals("Jane", actualWitnessById.getFirstName());
        assertEquals(1L, actualWitnessById.getCardNumber().longValue());
        assertEquals(1, actualWitnessById.getCrime().size());
        verify(this.witnessRepository).findById((UUID) any());
    }

    @Test
    void testGetWitnessById6() {
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

        Witness witness = new Witness();
        witness.setRemark("Remark");
        UUID randomUUIDResult = UUID.randomUUID();
        witness.setId(randomUUIDResult);
        witness.setUpdatedAt(mock(Timestamp.class));
        witness.setCardNumber(1L);
        witness.setCreatedById(UUID.randomUUID());
        witness.setCreatedAt(mock(Timestamp.class));
        LocalDateTime atStartOfDayResult2 = LocalDate.of(1970, 1, 1).atStartOfDay();
        Date fromResult = Date.from(atStartOfDayResult2.atZone(ZoneId.of("UTC")).toInstant());
        witness.setBirthDate(fromResult);
        witness.setCrime(crimeList);
        witness.setDeleted(true);
        witness.setPhoneNumber("4105551212");
        witness.setSurname("Doe");
        witness.setFirstName("Jane");
        witness.setUpdateById(UUID.randomUUID());
        Optional<Witness> ofResult = Optional.<Witness>of(witness);
        when(this.witnessRepository.findById((UUID) any())).thenReturn(ofResult);
        WitnessResponseDTO actualWitnessById = this.witnessServiceImpl.getWitnessById(UUID.randomUUID());
        assertSame(fromResult, actualWitnessById.getBirthDate());
        assertEquals("Doe", actualWitnessById.getSurname());
        assertEquals("Remark", actualWitnessById.getRemark());
        assertNull(actualWitnessById.getPhotoId());
        assertEquals("4105551212", actualWitnessById.getPhoneNumber());
        assertSame(randomUUIDResult, actualWitnessById.getId());
        assertEquals("Jane", actualWitnessById.getFirstName());
        assertEquals(1L, actualWitnessById.getCardNumber().longValue());
        assertEquals(1, actualWitnessById.getCrime().size());
        verify(this.witnessRepository).findById((UUID) any());
    }

    @Test
    void testAddWitness() {
        when(this.witnessRepository.existsByCardNumber((Long) any())).thenReturn(true);
        assertThrows(RestException.class, () -> this.witnessServiceImpl.addWitness(new WitnessDTO()));
        verify(this.witnessRepository).existsByCardNumber((Long) any());
    }

    @Test
    void testAddWitness2() {
        Witness witness = new Witness();
        witness.setRemark("Remark");
        witness.setId(UUID.randomUUID());
        witness.setUpdatedAt(mock(Timestamp.class));
        witness.setCardNumber(1L);
        witness.setCreatedById(UUID.randomUUID());
        witness.setCreatedAt(mock(Timestamp.class));
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        witness.setBirthDate(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        witness.setCrime(new ArrayList<Crime>());
        witness.setDeleted(true);
        witness.setPhoneNumber("4105551212");
        witness.setSurname("Doe");
        witness.setFirstName("Jane");
        witness.setUpdateById(UUID.randomUUID());
        when(this.witnessRepository.save((Witness) any())).thenReturn(witness);
        when(this.witnessRepository.existsByCardNumber((Long) any())).thenReturn(false);

        CitizenDTO citizenDTO = new CitizenDTO();
        citizenDTO.setCardNumber(1L);
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        citizenDTO.setBirthDate(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        citizenDTO.setFirstName("Jane");
        citizenDTO.setSurname("Doe");
        when(this.citizenExternalApiServiceImpl.getCrimesWithId((List<UUID>) any())).thenReturn(new ArrayList<Crime>());
        when(this.citizenExternalApiServiceImpl.getCitizenByCardNumber(anyLong())).thenReturn(citizenDTO);
        ApiResponse actualAddWitnessResult = this.witnessServiceImpl.addWitness(new WitnessDTO());
        assertEquals("Successfully Added", actualAddWitnessResult.getMessage());
        assertTrue(actualAddWitnessResult.isSuccess());
        verify(this.witnessRepository).existsByCardNumber((Long) any());
        verify(this.witnessRepository).save((Witness) any());
        verify(this.citizenExternalApiServiceImpl).getCitizenByCardNumber(anyLong());
        verify(this.citizenExternalApiServiceImpl).getCrimesWithId((List<UUID>) any());
    }

    @Test
    void testEditWitness() {
        Witness witness = new Witness();
        witness.setRemark("Remark");
        witness.setId(UUID.randomUUID());
        witness.setUpdatedAt(mock(Timestamp.class));
        witness.setCardNumber(1L);
        witness.setCreatedById(UUID.randomUUID());
        witness.setCreatedAt(mock(Timestamp.class));
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        witness.setBirthDate(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        witness.setCrime(new ArrayList<Crime>());
        witness.setDeleted(true);
        witness.setPhoneNumber("4105551212");
        witness.setSurname("Doe");
        witness.setFirstName("Jane");
        witness.setUpdateById(UUID.randomUUID());
        Optional<Witness> ofResult = Optional.<Witness>of(witness);
        when(this.witnessRepository.existsByCardNumberAndIdNot((Long) any(), (UUID) any())).thenReturn(true);
        when(this.witnessRepository.findById((UUID) any())).thenReturn(ofResult);
        UUID id = UUID.randomUUID();
        assertThrows(RestException.class, () -> this.witnessServiceImpl.editWitness(id, new WitnessDTO()));
        verify(this.witnessRepository).existsByCardNumberAndIdNot((Long) any(), (UUID) any());
        verify(this.witnessRepository).findById((UUID) any());
    }

    @Test
    void testEditWitness2() {
        Witness witness = new Witness();
        witness.setRemark("Remark");
        witness.setId(UUID.randomUUID());
        witness.setUpdatedAt(mock(Timestamp.class));
        witness.setCardNumber(1L);
        witness.setCreatedById(UUID.randomUUID());
        witness.setCreatedAt(mock(Timestamp.class));
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        witness.setBirthDate(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        witness.setCrime(new ArrayList<Crime>());
        witness.setDeleted(true);
        witness.setPhoneNumber("4105551212");
        witness.setSurname("Doe");
        witness.setFirstName("Jane");
        witness.setUpdateById(UUID.randomUUID());
        Optional<Witness> ofResult = Optional.<Witness>of(witness);

        Witness witness1 = new Witness();
        witness1.setRemark("Remark");
        witness1.setId(UUID.randomUUID());
        witness1.setUpdatedAt(mock(Timestamp.class));
        witness1.setCardNumber(1L);
        witness1.setCreatedById(UUID.randomUUID());
        witness1.setCreatedAt(mock(Timestamp.class));
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        witness1.setBirthDate(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        witness1.setCrime(new ArrayList<Crime>());
        witness1.setDeleted(true);
        witness1.setPhoneNumber("4105551212");
        witness1.setSurname("Doe");
        witness1.setFirstName("Jane");
        witness1.setUpdateById(UUID.randomUUID());
        when(this.witnessRepository.save((Witness) any())).thenReturn(witness1);
        when(this.witnessRepository.existsByCardNumberAndIdNot((Long) any(), (UUID) any())).thenReturn(false);
        when(this.witnessRepository.findById((UUID) any())).thenReturn(ofResult);

        CitizenDTO citizenDTO = new CitizenDTO();
        citizenDTO.setCardNumber(1L);
        LocalDateTime atStartOfDayResult2 = LocalDate.of(1970, 1, 1).atStartOfDay();
        citizenDTO.setBirthDate(Date.from(atStartOfDayResult2.atZone(ZoneId.of("UTC")).toInstant()));
        citizenDTO.setFirstName("Jane");
        citizenDTO.setSurname("Doe");
        when(this.citizenExternalApiServiceImpl.getCrimesWithId((List<UUID>) any())).thenReturn(new ArrayList<Crime>());
        when(this.citizenExternalApiServiceImpl.getCitizenByCardNumber(anyLong())).thenReturn(citizenDTO);
        UUID id = UUID.randomUUID();
        ApiResponse actualEditWitnessResult = this.witnessServiceImpl.editWitness(id, new WitnessDTO());
        assertEquals("Witness Updated Successfully", actualEditWitnessResult.getMessage());
        assertTrue(actualEditWitnessResult.isSuccess());
        verify(this.witnessRepository).existsByCardNumberAndIdNot((Long) any(), (UUID) any());
        verify(this.witnessRepository).findById((UUID) any());
        verify(this.witnessRepository).save((Witness) any());
        verify(this.citizenExternalApiServiceImpl).getCitizenByCardNumber(anyLong());
        verify(this.citizenExternalApiServiceImpl).getCrimesWithId((List<UUID>) any());
    }

    @Test
    void testDeleteWitness() {
        doNothing().when(this.witnessRepository).deleteById((UUID) any());
        ApiResponse actualDeleteWitnessResult = this.witnessServiceImpl.deleteWitness(UUID.randomUUID());
        assertEquals("Successfully deleted", actualDeleteWitnessResult.getMessage());
        assertTrue(actualDeleteWitnessResult.isSuccess());
        verify(this.witnessRepository).deleteById((UUID) any());
    }

    @Test
    void testDeleteWitness2() {
        doThrow(new RestException("An error occurred", HttpStatus.CONTINUE)).when(this.witnessRepository)
                .deleteById((UUID) any());
        assertThrows(RestException.class, () -> this.witnessServiceImpl.deleteWitness(UUID.randomUUID()));
        verify(this.witnessRepository).deleteById((UUID) any());
    }
}

