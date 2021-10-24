package com.example.smartcity.controller;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

import com.example.smartcity.entity.enums.CrimeReportStatus;
import com.example.smartcity.entity.enums.CrimeStatus;
import com.example.smartcity.entity.enums.CrimeType;
import com.example.smartcity.payload.ApiResponse;
import com.example.smartcity.payload.CrimeDTO;
import com.example.smartcity.service.impl.CrimeServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {CrimeController.class})
@ExtendWith(SpringExtension.class)
class CrimeControllerTest {
    @Autowired
    private CrimeController crimeController;

    @MockBean
    private CrimeServiceImpl crimeServiceImpl;

    @Test
    void testEditCrime() throws Exception {
        when(this.crimeServiceImpl.editCrime((UUID) any(), (CrimeDTO) any()))
                .thenReturn(new ApiResponse("Not all who wander are lost", true));

        CrimeDTO crimeDTO = new CrimeDTO();
        crimeDTO.setCrimeReportStatus(CrimeReportStatus.PENDING);
        crimeDTO.setOfficers(new ArrayList<UUID>());
        crimeDTO.setCrimeType(CrimeType.FELONY);
        crimeDTO.setName("Name");
        crimeDTO.setCrimeStatus(CrimeStatus.UNDER_INVESTIGATION);
        crimeDTO.setAddress("42 Main St");
        crimeDTO.setCrimeDescription("Crime Description");
        crimeDTO.setPoliceStationId(UUID.randomUUID());
        String content = (new ObjectMapper()).writeValueAsString(crimeDTO);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/api/crime/{id}", UUID.randomUUID())
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.crimeController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isAccepted())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"message\":\"Not all who wander are lost\",\"success\":true,\"object\":null}"));
    }
}

