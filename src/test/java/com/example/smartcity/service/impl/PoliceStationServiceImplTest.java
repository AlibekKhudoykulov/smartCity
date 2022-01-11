package com.example.smartcity.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.smartcity.entity.PoliceStation;
import com.example.smartcity.exception.RestException;
import com.example.smartcity.payload.ApiResponse;
import com.example.smartcity.payload.PoliceStationDTO;
import com.example.smartcity.payload.responseDTO.CustomPage;
import com.example.smartcity.payload.responseDTO.PoliceStationResponseDTO;
import com.example.smartcity.repository.PoliceStationRepository;
import com.example.smartcity.service.Mapper.Mappers;

import java.sql.Timestamp;
import java.util.ArrayList;
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

@ContextConfiguration(classes = {PoliceStationServiceImpl.class, Mappers.class})
@ExtendWith(SpringExtension.class)
class PoliceStationServiceImplTest {
    @MockBean
    private Mappers mappers;

    @MockBean
    private PoliceStationRepository policeStationRepository;

    @Autowired
    private PoliceStationServiceImpl policeStationServiceImpl;

    @Test
    void testGetAllStations() {
        when(this.policeStationRepository.findAll((org.springframework.data.domain.Pageable) any()))
                .thenReturn(new PageImpl<PoliceStation>(new ArrayList<PoliceStation>()));
        CustomPage<PoliceStationResponseDTO> actualAllStations = this.policeStationServiceImpl.getAllStations(1);
        assertTrue(actualAllStations.getContent().isEmpty());
        assertEquals(1, actualAllStations.getTotalPages());
        assertEquals(0L, actualAllStations.getTotalElements());
        assertEquals(0, actualAllStations.getSize());
        assertEquals(0, actualAllStations.getNumberOfElements());
        assertEquals(0, actualAllStations.getNumber());
        verify(this.policeStationRepository).findAll((org.springframework.data.domain.Pageable) any());
    }

    @Test
    void testGetAllStations2() {
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

        ArrayList<PoliceStation> policeStationList = new ArrayList<PoliceStation>();
        policeStationList.add(policeStation);
        PageImpl<PoliceStation> pageImpl = new PageImpl<PoliceStation>(policeStationList);
        when(this.policeStationRepository.findAll((org.springframework.data.domain.Pageable) any())).thenReturn(pageImpl);
        CustomPage<PoliceStationResponseDTO> actualAllStations = this.policeStationServiceImpl.getAllStations(1);
        assertEquals(1, actualAllStations.getContent().size());
        assertEquals(1, actualAllStations.getTotalPages());
        assertEquals(1L, actualAllStations.getTotalElements());
        assertEquals(1, actualAllStations.getSize());
        assertEquals(1, actualAllStations.getNumberOfElements());
        assertEquals(0, actualAllStations.getNumber());
        verify(this.policeStationRepository).findAll((org.springframework.data.domain.Pageable) any());
    }

    @Test
    void testGetAllStations3() {
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

        PoliceStation policeStation1 = new PoliceStation();
        policeStation1.setRemark("createdAt");
        policeStation1.setId(UUID.randomUUID());
        policeStation1.setUpdatedAt(mock(Timestamp.class));
        policeStation1.setCreatedById(UUID.randomUUID());
        policeStation1.setCreatedAt(mock(Timestamp.class));
        policeStation1.setName("createdAt");
        policeStation1.setDeleted(true);
        policeStation1.setPhoneNumber("4105551212");
        policeStation1.setAddress("42 Main St");
        policeStation1.setUpdateById(UUID.randomUUID());

        ArrayList<PoliceStation> policeStationList = new ArrayList<PoliceStation>();
        policeStationList.add(policeStation1);
        policeStationList.add(policeStation);
        PageImpl<PoliceStation> pageImpl = new PageImpl<PoliceStation>(policeStationList);
        when(this.policeStationRepository.findAll((org.springframework.data.domain.Pageable) any())).thenReturn(pageImpl);
        CustomPage<PoliceStationResponseDTO> actualAllStations = this.policeStationServiceImpl.getAllStations(1);
        assertEquals(2, actualAllStations.getContent().size());
        assertEquals(1, actualAllStations.getTotalPages());
        assertEquals(2L, actualAllStations.getTotalElements());
        assertEquals(2, actualAllStations.getSize());
        assertEquals(2, actualAllStations.getNumberOfElements());
        assertEquals(0, actualAllStations.getNumber());
        verify(this.policeStationRepository).findAll((org.springframework.data.domain.Pageable) any());
    }

    @Test
    void testGetAllStations4() {
        when(this.policeStationRepository.findAll((org.springframework.data.domain.Pageable) any()))
                .thenReturn(new PageImpl<PoliceStation>(new ArrayList<PoliceStation>()));
        CustomPage<PoliceStationResponseDTO> actualAllStations = this.policeStationServiceImpl.getAllStations(0);
        assertTrue(actualAllStations.getContent().isEmpty());
        assertEquals(1, actualAllStations.getTotalPages());
        assertEquals(0L, actualAllStations.getTotalElements());
        assertEquals(0, actualAllStations.getSize());
        assertEquals(0, actualAllStations.getNumberOfElements());
        assertEquals(0, actualAllStations.getNumber());
        verify(this.policeStationRepository).findAll((org.springframework.data.domain.Pageable) any());
    }

    @Test
    void testGetStationById() {
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
        Optional<PoliceStation> ofResult = Optional.<PoliceStation>of(policeStation);
        when(this.policeStationRepository.findById((UUID) any())).thenReturn(ofResult);
        PoliceStationResponseDTO actualStationById = this.policeStationServiceImpl.getStationById(UUID.randomUUID());
        assertEquals("42 Main St", actualStationById.getAddress());
        assertEquals("Remark", actualStationById.getRemark());
        assertEquals("4105551212", actualStationById.getPhoneNumber());
        assertEquals("Name", actualStationById.getName());
        assertSame(randomUUIDResult, actualStationById.getId());
        verify(this.policeStationRepository).findById((UUID) any());
    }

    @Test
    void testGetStationById2() {
        when(this.policeStationRepository.findById((UUID) any())).thenReturn(Optional.<PoliceStation>empty());
        assertThrows(RestException.class, () -> this.policeStationServiceImpl.getStationById(UUID.randomUUID()));
        verify(this.policeStationRepository).findById((UUID) any());
    }

    @Test
    void testAddStation() {
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
        when(this.policeStationRepository.findByName((String) any())).thenReturn(ofResult);
        assertThrows(RestException.class, () -> this.policeStationServiceImpl
                .addStation(new PoliceStationDTO("Name", "42 Main St", "4105551212", "Remark")));
        verify(this.policeStationRepository).findByName((String) any());
    }

    @Test
    void testAddStation2() {
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
        when(this.policeStationRepository.save((PoliceStation) any())).thenReturn(policeStation);
        when(this.policeStationRepository.findByName((String) any())).thenReturn(Optional.<PoliceStation>empty());
        ApiResponse actualAddStationResult = this.policeStationServiceImpl
                .addStation(new PoliceStationDTO("Name", "42 Main St", "4105551212", "Remark"));
        assertEquals("Police Station Saved Successfully", actualAddStationResult.getMessage());
        assertTrue(actualAddStationResult.isSuccess());
        verify(this.policeStationRepository).findByName((String) any());
        verify(this.policeStationRepository).save((PoliceStation) any());
    }

    @Test
    void testEditStation() {
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
        when(this.policeStationRepository.existsByNameAndIdNot((String) any(), (UUID) any())).thenReturn(true);
        when(this.policeStationRepository.findById((UUID) any())).thenReturn(ofResult);
        UUID id = UUID.randomUUID();
        assertThrows(RestException.class, () -> this.policeStationServiceImpl.editStation(id,
                new PoliceStationDTO("Name", "42 Main St", "4105551212", "Remark")));
        verify(this.policeStationRepository).existsByNameAndIdNot((String) any(), (UUID) any());
        verify(this.policeStationRepository).findById((UUID) any());
    }

    @Test
    void testEditStation2() {
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
        when(this.policeStationRepository.save((PoliceStation) any())).thenReturn(policeStation1);
        when(this.policeStationRepository.existsByNameAndIdNot((String) any(), (UUID) any())).thenReturn(false);
        when(this.policeStationRepository.findById((UUID) any())).thenReturn(ofResult);
        UUID id = UUID.randomUUID();
        ApiResponse actualEditStationResult = this.policeStationServiceImpl.editStation(id,
                new PoliceStationDTO("Name", "42 Main St", "4105551212", "Remark"));
        assertEquals("Edited Successfully", actualEditStationResult.getMessage());
        assertTrue(actualEditStationResult.isSuccess());
        verify(this.policeStationRepository).existsByNameAndIdNot((String) any(), (UUID) any());
        verify(this.policeStationRepository).findById((UUID) any());
        verify(this.policeStationRepository).save((PoliceStation) any());
    }

    @Test
    void testEditStation3() {
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
        when(this.policeStationRepository.save((PoliceStation) any())).thenReturn(policeStation);
        when(this.policeStationRepository.existsByNameAndIdNot((String) any(), (UUID) any())).thenReturn(false);
        when(this.policeStationRepository.findById((UUID) any())).thenReturn(Optional.<PoliceStation>empty());
        UUID id = UUID.randomUUID();
        assertThrows(RestException.class, () -> this.policeStationServiceImpl.editStation(id,
                new PoliceStationDTO("Name", "42 Main St", "4105551212", "Remark")));
        verify(this.policeStationRepository).findById((UUID) any());
    }

    @Test
    void testDeleteStation() {
        doNothing().when(this.policeStationRepository).deleteById((UUID) any());
        ApiResponse actualDeleteStationResult = this.policeStationServiceImpl.deleteStation(UUID.randomUUID());
        assertEquals("Successfully deleted", actualDeleteStationResult.getMessage());
        assertTrue(actualDeleteStationResult.isSuccess());
        verify(this.policeStationRepository).deleteById((UUID) any());
    }

    @Test
    void testDeleteStation2() {
        doThrow(new RestException("An error occurred", HttpStatus.CONTINUE)).when(this.policeStationRepository)
                .deleteById((UUID) any());
        assertThrows(RestException.class, () -> this.policeStationServiceImpl.deleteStation(UUID.randomUUID()));
        verify(this.policeStationRepository).deleteById((UUID) any());
    }
}

