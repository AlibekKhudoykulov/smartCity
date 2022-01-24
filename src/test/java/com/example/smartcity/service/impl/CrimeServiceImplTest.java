package com.example.smartcity.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.smartcity.entity.Crime;
import com.example.smartcity.entity.Officer;
import com.example.smartcity.entity.PoliceStation;
import com.example.smartcity.entity.enums.CrimeReportStatus;
import com.example.smartcity.entity.enums.CrimeStatus;
import com.example.smartcity.entity.enums.CrimeType;
import com.example.smartcity.entity.enums.OfficerRank;
import com.example.smartcity.exception.RestException;
import com.example.smartcity.payload.ApiResponse;
import com.example.smartcity.payload.CrimeDTO;
import com.example.smartcity.payload.responseDTO.CrimeResponseDTO;
import com.example.smartcity.payload.responseDTO.CustomPage;
import com.example.smartcity.payload.responseDTO.PoliceStationResponseDTO;
import com.example.smartcity.repository.CrimeRepository;
import com.example.smartcity.repository.OfficerRepository;
import com.example.smartcity.repository.PoliceStationRepository;
import com.example.smartcity.service.Mapper.Mappers;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
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

@ContextConfiguration(classes = {CrimeServiceImpl.class, CitizenExternalApiServiceImpl.class, Mappers.class})
@ExtendWith(SpringExtension.class)
class CrimeServiceImplTest {
    @MockBean
    private CitizenExternalApiServiceImpl citizenExternalApiServiceImpl;

    @MockBean
    private CrimeRepository crimeRepository;

    @Autowired
    private CrimeServiceImpl crimeServiceImpl;

    @MockBean
    private OfficerRepository officerRepository;

    @MockBean
    private PoliceStationRepository policeStationRepository;

    @Test
    void testGetAllCrimes() {
        when(this.crimeRepository.findAll((org.springframework.data.domain.Pageable) any()))
                .thenReturn(new PageImpl<Crime>(new ArrayList<Crime>()));
        CustomPage<CrimeResponseDTO> actualAllCrimes = this.crimeServiceImpl.getAllCrimes(1);
        assertTrue(actualAllCrimes.getContent().isEmpty());
        assertEquals(1, actualAllCrimes.getTotalPages());
        assertEquals(0L, actualAllCrimes.getTotalElements());
        assertEquals(0, actualAllCrimes.getSize());
        assertEquals(0, actualAllCrimes.getNumberOfElements());
        assertEquals(0, actualAllCrimes.getNumber());
        verify(this.crimeRepository).findAll((org.springframework.data.domain.Pageable) any());
    }


    @Test
    void testGetCrimeById() {
        PoliceStation policeStation = new PoliceStation();
        policeStation.setRemark("Remark");
        UUID randomUUIDResult = UUID.randomUUID();
        policeStation.setId(randomUUIDResult);
        policeStation.setUpdatedAt(mock(Timestamp.class));
        policeStation.setCreatedById(UUID.randomUUID());
        policeStation.setCreatedAt(mock(Timestamp.class));
        policeStation.setName("Name");
        policeStation.setDeleted(true);
        policeStation.setPhoneNumber("4105551212");
        policeStation.setAddress("42 Main St");
        policeStation.setUpdateById(UUID.randomUUID());

        Crime crime = new Crime();
        UUID randomUUIDResult1 = UUID.randomUUID();
        crime.setId(randomUUIDResult1);
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
        Optional<Crime> ofResult = Optional.<Crime>of(crime);
        when(this.crimeRepository.findById((UUID) any())).thenReturn(ofResult);
        CrimeResponseDTO actualCrimeById = this.crimeServiceImpl.getCrimeById(UUID.randomUUID());
        assertEquals("42 Main St", actualCrimeById.getAddress());
        assertSame(randomUUIDResult1, actualCrimeById.getUuid());
        assertEquals("Crime Description", actualCrimeById.getCrimeDescription());
        assertEquals(CrimeStatus.UNDER_INVESTIGATION, actualCrimeById.getCrimeStatus());
        assertTrue(actualCrimeById.getOfficers().isEmpty());
        assertEquals(CrimeReportStatus.PENDING, actualCrimeById.getCrimeReportStatus());
        assertEquals("01:01", actualCrimeById.getCrimeTime().toLocalTime().toString());
        assertEquals(CrimeType.FELONY, actualCrimeById.getCrimeType());
        assertEquals("Name", actualCrimeById.getName());
        PoliceStationResponseDTO policeStation1 = actualCrimeById.getPoliceStation();
        assertSame(randomUUIDResult, policeStation1.getId());
        assertEquals("Name", policeStation1.getName());
        assertEquals("4105551212", policeStation1.getPhoneNumber());
        assertEquals("Remark", policeStation1.getRemark());
        assertEquals("42 Main St", policeStation1.getAddress());
        verify(this.crimeRepository).findById((UUID) any());
    }

    @Test
    void testGetCrimeByIdNotFound() {
        when(this.crimeRepository.findById((UUID) any())).thenReturn(Optional.<Crime>empty());
        assertThrows(RestException.class, () -> this.crimeServiceImpl.getCrimeById(UUID.randomUUID()));
        verify(this.crimeRepository).findById((UUID) any());
    }

    @Test
    void testGetCrimeById2() {
        when(this.crimeRepository.findById((UUID) any())).thenReturn(Optional.<Crime>empty());
        assertThrows(RestException.class, () -> this.crimeServiceImpl.getCrimeById(UUID.randomUUID()));
        verify(this.crimeRepository).findById((UUID) any());
    }

    @Test
    void testAddCrime() {
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
        when(this.crimeRepository.save((Crime) any())).thenReturn(crime);
        ApiResponse actualAddCrimeResult = this.crimeServiceImpl.addCrime(new CrimeDTO());
        assertEquals("Saved successfully", actualAddCrimeResult.getMessage());
        assertTrue(actualAddCrimeResult.isSuccess());
        verify(this.crimeRepository).save((Crime) any());
    }

    @Test
    void testEditCrime() {
        when(this.crimeRepository.findById((UUID) any()))
                .thenThrow(new RestException("An error occurred", HttpStatus.CONTINUE));
        UUID id = UUID.randomUUID();
        assertThrows(RestException.class, () -> this.crimeServiceImpl.editCrime(id, new CrimeDTO()));
        verify(this.crimeRepository).findById((UUID) any());
    }


    @Test
    void testDeleteCrime() {
        doNothing().when(this.crimeRepository).deleteById((UUID) any());
        ApiResponse actualDeleteCrimeResult = this.crimeServiceImpl.deleteCrime(UUID.randomUUID());
        assertEquals("Successfully deleted", actualDeleteCrimeResult.getMessage());
        assertTrue(actualDeleteCrimeResult.isSuccess());
        verify(this.crimeRepository).deleteById((UUID) any());
    }

    @Test
    void testGetOfficerList() {
        assertTrue(this.crimeServiceImpl.getOfficerList(new ArrayList<UUID>()).isEmpty());
    }

    @Test
    void testGetOfficerList2() {
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
        officer.setCardNumber(1L);
        officer.setCertificate(1L);
        officer.setFirstName("Jane");
        officer.setUpdateById(UUID.randomUUID());
        officer.setRank(OfficerRank.DETECTIVE);
        officer.setPoliceStation(policeStation);
        Optional<Officer> ofResult = Optional.<Officer>of(officer);
        when(this.officerRepository.findById((UUID) any())).thenReturn(ofResult);

        ArrayList<UUID> uuidList = new ArrayList<UUID>();
        uuidList.add(UUID.randomUUID());
        assertEquals(1, this.crimeServiceImpl.getOfficerList(uuidList).size());
        verify(this.officerRepository).findById((UUID) any());
    }

    @Test
    void testGetOfficerList3() {
        when(this.officerRepository.findById((UUID) any())).thenReturn(Optional.<Officer>empty());

        ArrayList<UUID> uuidList = new ArrayList<UUID>();
        uuidList.add(UUID.randomUUID());
        assertTrue(this.crimeServiceImpl.getOfficerList(uuidList).isEmpty());
        verify(this.officerRepository).findById((UUID) any());
    }

    @Test
    void testCheckPoliceStation() {
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
        Optional<PoliceStation> ofResult = Optional.<PoliceStation>of(policeStation);
        when(this.policeStationRepository.findById((UUID) any())).thenReturn(ofResult);
        assertSame(policeStation, this.crimeServiceImpl.checkPoliceStation(UUID.randomUUID()));
        verify(this.policeStationRepository).findById((UUID) any());
    }

    @Test
    void testCheckPoliceStation2() {
        when(this.policeStationRepository.findById((UUID) any())).thenReturn(Optional.<PoliceStation>empty());
        assertNull(this.crimeServiceImpl.checkPoliceStation(UUID.randomUUID()));
        verify(this.policeStationRepository).findById((UUID) any());
    }
}

