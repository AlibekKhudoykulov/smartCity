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

import com.example.smartcity.entity.Officer;
import com.example.smartcity.entity.PoliceStation;
import com.example.smartcity.entity.enums.OfficerRank;
import com.example.smartcity.exception.RestException;
import com.example.smartcity.payload.ApiResponse;
import com.example.smartcity.payload.CitizenDTO;
import com.example.smartcity.payload.OfficerDTO;
import com.example.smartcity.payload.responseDTO.CustomPage;
import com.example.smartcity.payload.responseDTO.OfficerResponseDTO;
import com.example.smartcity.payload.responseDTO.PoliceStationResponseDTO;
import com.example.smartcity.repository.OfficerRepository;
import com.example.smartcity.repository.PoliceStationRepository;
import com.example.smartcity.repository.UserRepository;
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
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {OfficerServiceImpl.class, CitizenExternalApiServiceImpl.class, Mappers.class,
        UserServiceImpl.class})
@ExtendWith(SpringExtension.class)
class OfficerServiceImplTest {
    @MockBean
    private CitizenExternalApiServiceImpl citizenExternalApiServiceImpl;

    @MockBean
    private Mappers mappers;

    @MockBean
    private OfficerRepository officerRepository;

    @Autowired
    private OfficerServiceImpl officerServiceImpl;

    @MockBean
    private PoliceStationRepository policeStationRepository;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private UserServiceImpl userServiceImpl;



    @Test
    void testGetAllOfficers() {
        when(this.officerRepository.findAll((org.springframework.data.domain.Pageable) any()))
                .thenReturn(new PageImpl<Officer>(new ArrayList<Officer>()));
        CustomPage<OfficerResponseDTO> actualAllOfficers = this.officerServiceImpl.getAllOfficers(1);
        assertTrue(actualAllOfficers.getContent().isEmpty());
        assertEquals(1, actualAllOfficers.getTotalPages());
        assertEquals(0L, actualAllOfficers.getTotalElements());
        assertEquals(0, actualAllOfficers.getSize());
        assertEquals(0, actualAllOfficers.getNumberOfElements());
        assertEquals(0, actualAllOfficers.getNumber());
        verify(this.officerRepository).findAll((org.springframework.data.domain.Pageable) any());
    }

    @Test
    void testGetAllOfficers2() {
        PoliceStation policeStation = new PoliceStation();
        policeStation.setRemark("createdAt");
        policeStation.setId(UUID.randomUUID());
        policeStation.setUpdatedAt(mock(Timestamp.class));
        policeStation.setCreatedById(UUID.randomUUID());
        policeStation.setCreatedAt(mock(Timestamp.class));
        policeStation.setName("createdAt");
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
        PageImpl<Officer> pageImpl = new PageImpl<Officer>(officerList);
        when(this.officerRepository.findAll((org.springframework.data.domain.Pageable) any())).thenReturn(pageImpl);
        CustomPage<OfficerResponseDTO> actualAllOfficers = this.officerServiceImpl.getAllOfficers(1);
        assertEquals(1, actualAllOfficers.getContent().size());
        assertEquals(1, actualAllOfficers.getTotalPages());
        assertEquals(1L, actualAllOfficers.getTotalElements());
        assertEquals(1, actualAllOfficers.getSize());
        assertEquals(1, actualAllOfficers.getNumberOfElements());
        assertEquals(0, actualAllOfficers.getNumber());
        verify(this.officerRepository).findAll((org.springframework.data.domain.Pageable) any());
    }

    @Test
    void testGetAllOfficers4() {
        when(this.officerRepository.findAll((org.springframework.data.domain.Pageable) any()))
                .thenReturn(new PageImpl<Officer>(new ArrayList<Officer>()));
        CustomPage<OfficerResponseDTO> actualAllOfficers = this.officerServiceImpl.getAllOfficers(0);
        assertTrue(actualAllOfficers.getContent().isEmpty());
        assertEquals(1, actualAllOfficers.getTotalPages());
        assertEquals(0L, actualAllOfficers.getTotalElements());
        assertEquals(0, actualAllOfficers.getSize());
        assertEquals(0, actualAllOfficers.getNumberOfElements());
        assertEquals(0, actualAllOfficers.getNumber());
        verify(this.officerRepository).findAll((org.springframework.data.domain.Pageable) any());
    }

    @Test
    void testGetOfficerById() {
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

        Officer officer = new Officer();
        officer.setLastName("Doe");
        UUID randomUUIDResult1 = UUID.randomUUID();
        officer.setId(randomUUIDResult1);
        officer.setUpdatedAt(mock(Timestamp.class));
        officer.setCreatedById(UUID.randomUUID());
        officer.setCreatedAt(mock(Timestamp.class));
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        Date fromResult = Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant());
        officer.setBirthDate(fromResult);
        officer.setDeleted(true);
        officer.setCardNumber(1L);
        officer.setCertificate(1L);
        officer.setFirstName("Jane");
        officer.setUpdateById(UUID.randomUUID());
        officer.setRank(OfficerRank.DETECTIVE);
        officer.setPoliceStation(policeStation);
        Optional<Officer> ofResult = Optional.<Officer>of(officer);
        when(this.officerRepository.findById((UUID) any())).thenReturn(ofResult);
        OfficerResponseDTO actualOfficerById = this.officerServiceImpl.getOfficerById(UUID.randomUUID());
        assertSame(fromResult, actualOfficerById.getBirthDate());
        assertSame(randomUUIDResult1, actualOfficerById.getUuid());
        assertEquals(OfficerRank.DETECTIVE, actualOfficerById.getRank());
        assertEquals(1L, actualOfficerById.getCardNumber());
        assertEquals("Jane", actualOfficerById.getFirstName());
        assertEquals("Doe", actualOfficerById.getLastName());
        assertEquals(1L, actualOfficerById.getCertificate());
        assertNull(actualOfficerById.getPhotoId());
        PoliceStationResponseDTO policeStation1 = actualOfficerById.getPoliceStation();
        assertEquals("Remark", policeStation1.getRemark());
        assertEquals("4105551212", policeStation1.getPhoneNumber());
        assertEquals("Name", policeStation1.getName());
        assertSame(randomUUIDResult, policeStation1.getId());
        assertEquals("42 Main St", policeStation1.getAddress());
        verify(this.officerRepository).findById((UUID) any());
    }


    @Test
    void testGetOfficerById2() {
        when(this.officerRepository.findById((UUID) any())).thenReturn(Optional.<Officer>empty());
        assertThrows(RestException.class, () -> this.officerServiceImpl.getOfficerById(UUID.randomUUID()));
        verify(this.officerRepository).findById((UUID) any());
    }


    @Test
    void testGetOfficerByCardNumber() {
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

        Officer officer = new Officer();
        officer.setLastName("Doe");
        UUID randomUUIDResult1 = UUID.randomUUID();
        officer.setId(randomUUIDResult1);
        officer.setUpdatedAt(mock(Timestamp.class));
        officer.setCreatedById(UUID.randomUUID());
        officer.setCreatedAt(mock(Timestamp.class));
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        Date fromResult = Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant());
        officer.setBirthDate(fromResult);
        officer.setDeleted(true);
        officer.setCardNumber(1L);
        officer.setCertificate(1L);
        officer.setFirstName("Jane");
        officer.setUpdateById(UUID.randomUUID());
        officer.setRank(OfficerRank.DETECTIVE);
        officer.setPoliceStation(policeStation);
        Optional<Officer> ofResult = Optional.<Officer>of(officer);
        when(this.officerRepository.findByCardNumber(anyLong())).thenReturn(ofResult);
        OfficerResponseDTO actualOfficerByCardNumber = this.officerServiceImpl.getOfficerByCardNumber(1L);
        assertSame(fromResult, actualOfficerByCardNumber.getBirthDate());
        assertSame(randomUUIDResult1, actualOfficerByCardNumber.getUuid());
        assertEquals(OfficerRank.DETECTIVE, actualOfficerByCardNumber.getRank());
        assertEquals(1L, actualOfficerByCardNumber.getCardNumber());
        assertEquals("Jane", actualOfficerByCardNumber.getFirstName());
        assertEquals("Doe", actualOfficerByCardNumber.getLastName());
        assertEquals(1L, actualOfficerByCardNumber.getCertificate());
        assertNull(actualOfficerByCardNumber.getPhotoId());
        PoliceStationResponseDTO policeStation1 = actualOfficerByCardNumber.getPoliceStation();
        assertEquals("Remark", policeStation1.getRemark());
        assertEquals("4105551212", policeStation1.getPhoneNumber());
        assertEquals("Name", policeStation1.getName());
        assertSame(randomUUIDResult, policeStation1.getId());
        assertEquals("42 Main St", policeStation1.getAddress());
        verify(this.officerRepository).findByCardNumber(anyLong());
    }

    @Test
    void testGetOfficerByCardNumber2() {
        when(this.officerRepository.findByCardNumber(anyLong())).thenReturn(Optional.<Officer>empty());
        assertThrows(RestException.class, () -> this.officerServiceImpl.getOfficerByCardNumber(1L));
        verify(this.officerRepository).findByCardNumber(anyLong());
    }

    @Test
    void testAddOfficer() {
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
        when(this.officerRepository.findByCardNumber(anyLong())).thenReturn(ofResult);
        assertThrows(RestException.class, () -> this.officerServiceImpl.addOfficer(new OfficerDTO()));
        verify(this.officerRepository).findByCardNumber(anyLong());
    }

    @Test
    void testAddOfficer2() {
        when(this.userRepository.existsByCardNumber((Long) any())).thenReturn(true);

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
        when(this.officerRepository.save((Officer) any())).thenReturn(officer);
        when(this.officerRepository.findByCardNumber(anyLong())).thenReturn(Optional.<Officer>empty());

        CitizenDTO citizenDTO = new CitizenDTO();
        citizenDTO.setCardNumber(1L);
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        citizenDTO.setBirthDate(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        citizenDTO.setFirstName("Jane");
        citizenDTO.setSurname("Doe");
        doNothing().when(this.citizenExternalApiServiceImpl).sendCheckingCertificate((Officer) any());
        when(this.citizenExternalApiServiceImpl.generateCertificate()).thenReturn(1L);
        when(this.citizenExternalApiServiceImpl.getCitizenByCardNumber(anyLong())).thenReturn(citizenDTO);
        ApiResponse actualAddOfficerResult = this.officerServiceImpl.addOfficer(new OfficerDTO());
        assertEquals("Officer saved successfully and sent for checking certificate", actualAddOfficerResult.getMessage());
        assertTrue(actualAddOfficerResult.isSuccess());
        verify(this.userRepository).existsByCardNumber((Long) any());
        verify(this.officerRepository).findByCardNumber(anyLong());
        verify(this.officerRepository).save((Officer) any());
        verify(this.citizenExternalApiServiceImpl).generateCertificate();
        verify(this.citizenExternalApiServiceImpl).getCitizenByCardNumber(anyLong());
        verify(this.citizenExternalApiServiceImpl).sendCheckingCertificate((Officer) any());
    }

    @Test
    void testEditOfficer() {
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
        officer1.setCardNumber(1L);
        officer1.setCertificate(1L);
        officer1.setFirstName("Jane");
        officer1.setUpdateById(UUID.randomUUID());
        officer1.setRank(OfficerRank.DETECTIVE);
        officer1.setPoliceStation(policeStation1);
        when(this.officerRepository.save((Officer) any())).thenReturn(officer1);
        when(this.officerRepository.findById((UUID) any())).thenReturn(ofResult);
        UUID id = UUID.randomUUID();
        ApiResponse actualEditOfficerResult = this.officerServiceImpl.editOfficer(id, new OfficerDTO());
        assertEquals("Officer updated successfully", actualEditOfficerResult.getMessage());
        assertTrue(actualEditOfficerResult.isSuccess());
        verify(this.officerRepository).findById((UUID) any());
        verify(this.officerRepository).save((Officer) any());
    }

    @Test
    void testDeleteOfficer() {
        doNothing().when(this.officerRepository).deleteById((UUID) any());
        ApiResponse actualDeleteOfficerResult = this.officerServiceImpl.deleteOfficer(UUID.randomUUID());
        assertEquals("Successfully deleted", actualDeleteOfficerResult.getMessage());
        assertTrue(actualDeleteOfficerResult.isSuccess());
        verify(this.officerRepository).deleteById(any());
    }

    @Test
    void testDeleteOfficer2() {
        doThrow(new RestException("An error occurred", HttpStatus.CONTINUE)).when(this.officerRepository)
                .deleteById((UUID) any());
        assertThrows(RestException.class, () -> this.officerServiceImpl.deleteOfficer(UUID.randomUUID()));
        verify(this.officerRepository).deleteById((UUID) any());
    }
}

