package com.example.smartcity.controller;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

import com.example.smartcity.Entity.Enums.CrimeReportStatus;
import com.example.smartcity.Entity.Enums.CrimeStatus;
import com.example.smartcity.Entity.Enums.CrimeType;
import com.example.smartcity.Service.impl.CrimeServiceImpl;
import com.example.smartcity.payload.ApiResponse;
import com.example.smartcity.payload.CrimeDTO;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders;
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
    void testGetCrimeById() throws Exception {
        when(this.crimeServiceImpl.getCrimeById((UUID) any()))
                .thenReturn(new ApiResponse("Not all who wander are lost", true));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/crime/{id}", UUID.randomUUID());
        MockMvcBuilders.standaloneSetup(this.crimeController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"message\":\"Not all who wander are lost\",\"success\":true,\"object\":null}"));
    }

    @Test
    void testGetCrimeById2() throws Exception {
        when(this.crimeServiceImpl.getCrimeById((UUID) any()))
                .thenReturn(new ApiResponse("Not all who wander are lost", false));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/crime/{id}", UUID.randomUUID());
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.crimeController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"message\":\"Not all who wander are lost\",\"success\":false,\"object\":null}"));
    }

    @Test
    void testGetCrimeById3() throws Exception {
        when(this.crimeServiceImpl.getAllCrimes()).thenReturn(new ApiResponse("Not all who wander are lost", true));
        when(this.crimeServiceImpl.getCrimeById((UUID) any())).thenReturn(null);
        UUID.randomUUID();
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/crime/{id}", "", "Uri Vars");
        MockMvcBuilders.standaloneSetup(this.crimeController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"message\":\"Not all who wander are lost\",\"success\":true,\"object\":null}"));
    }

    @Test
    void testEditCrime() throws Exception {
        when(this.crimeServiceImpl.editCrime((UUID) any(), (CrimeDTO) any()))
                .thenReturn(new ApiResponse("Not all who wander are lost", true));

        CrimeDTO crimeDTO = new CrimeDTO();
        crimeDTO.setCrimeType(CrimeType.FELONY);
        crimeDTO.setOfficers(new ArrayList<UUID>());
        crimeDTO.setName("Name");
        crimeDTO.setCrimeStatus(CrimeStatus.UNDER_INVESTIGATION);
        crimeDTO.setAddress("42 Main St");
        crimeDTO.setCrimeReportStatus(CrimeReportStatus.PENDING);
        crimeDTO.setCrimeDescription("Crime Description");
        crimeDTO.setPoliceStationId(UUID.randomUUID());
        String content = (new ObjectMapper()).writeValueAsString(crimeDTO);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/api/crime/{id}", UUID.randomUUID())
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.crimeController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"message\":\"Not all who wander are lost\",\"success\":true,\"object\":null}"));
    }

    @Test
    void testEditCrime2() throws Exception {
        when(this.crimeServiceImpl.editCrime((UUID) any(), (CrimeDTO) any()))
                .thenReturn(new ApiResponse("Not all who wander are lost", false));

        CrimeDTO crimeDTO = new CrimeDTO();
        crimeDTO.setCrimeType(CrimeType.FELONY);
        crimeDTO.setOfficers(new ArrayList<UUID>());
        crimeDTO.setName("Name");
        crimeDTO.setCrimeStatus(CrimeStatus.UNDER_INVESTIGATION);
        crimeDTO.setAddress("42 Main St");
        crimeDTO.setCrimeReportStatus(CrimeReportStatus.PENDING);
        crimeDTO.setCrimeDescription("Crime Description");
        crimeDTO.setPoliceStationId(UUID.randomUUID());
        String content = (new ObjectMapper()).writeValueAsString(crimeDTO);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/api/crime/{id}", UUID.randomUUID())
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.crimeController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(400))
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"message\":\"Not all who wander are lost\",\"success\":false,\"object\":null}"));
    }

    @Test
    void testAddCrime() throws Exception {
        when(this.crimeServiceImpl.getAllCrimes()).thenReturn(new ApiResponse("Not all who wander are lost", true));

        CrimeDTO crimeDTO = new CrimeDTO();
        crimeDTO.setCrimeType(CrimeType.FELONY);
        crimeDTO.setOfficers(new ArrayList<UUID>());
        crimeDTO.setName("Name");
        crimeDTO.setCrimeStatus(CrimeStatus.UNDER_INVESTIGATION);
        crimeDTO.setAddress("42 Main St");
        crimeDTO.setCrimeReportStatus(CrimeReportStatus.PENDING);
        crimeDTO.setCrimeDescription("Crime Description");
        crimeDTO.setPoliceStationId(UUID.randomUUID());
        String content = (new ObjectMapper()).writeValueAsString(crimeDTO);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/crime")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(this.crimeController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"message\":\"Not all who wander are lost\",\"success\":true,\"object\":null}"));
    }

    @Test
    void testDeleteCrime() throws Exception {
        when(this.crimeServiceImpl.deleteCrime((UUID) any()))
                .thenReturn(new ApiResponse("Not all who wander are lost", true));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/crime/{id}", UUID.randomUUID());
        MockMvcBuilders.standaloneSetup(this.crimeController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"message\":\"Not all who wander are lost\",\"success\":true,\"object\":null}"));
    }

    @Test
    void testDeleteCrime2() throws Exception {
        when(this.crimeServiceImpl.deleteCrime((UUID) any()))
                .thenReturn(new ApiResponse("Not all who wander are lost", false));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/crime/{id}", UUID.randomUUID());
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.crimeController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"message\":\"Not all who wander are lost\",\"success\":false,\"object\":null}"));
    }

    @Test
    void testAddCrime2() throws Exception {
        when(this.crimeServiceImpl.getAllCrimes()).thenReturn(new ApiResponse("Not all who wander are lost", false));

        CrimeDTO crimeDTO = new CrimeDTO();
        crimeDTO.setCrimeType(CrimeType.FELONY);
        crimeDTO.setOfficers(new ArrayList<UUID>());
        crimeDTO.setName("Name");
        crimeDTO.setCrimeStatus(CrimeStatus.UNDER_INVESTIGATION);
        crimeDTO.setAddress("42 Main St");
        crimeDTO.setCrimeReportStatus(CrimeReportStatus.PENDING);
        crimeDTO.setCrimeDescription("Crime Description");
        crimeDTO.setPoliceStationId(UUID.randomUUID());
        String content = (new ObjectMapper()).writeValueAsString(crimeDTO);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/crime")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.crimeController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"message\":\"Not all who wander are lost\",\"success\":false,\"object\":null}"));
    }

    @Test
    void testGetAll() throws Exception {
        when(this.crimeServiceImpl.getAllCrimes()).thenReturn(new ApiResponse("Not all who wander are lost", true));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/crime");
        MockMvcBuilders.standaloneSetup(this.crimeController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"message\":\"Not all who wander are lost\",\"success\":true,\"object\":null}"));
    }

    @Test
    void testGetAll2() throws Exception {
        when(this.crimeServiceImpl.getAllCrimes()).thenReturn(new ApiResponse("Not all who wander are lost", false));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/crime");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.crimeController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"message\":\"Not all who wander are lost\",\"success\":false,\"object\":null}"));
    }

    @Test
    void testGetAll3() throws Exception {
        when(this.crimeServiceImpl.getAllCrimes()).thenReturn(null);
        SecurityMockMvcRequestBuilders.FormLoginRequestBuilder requestBuilder = SecurityMockMvcRequestBuilders.formLogin();
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.crimeController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}

