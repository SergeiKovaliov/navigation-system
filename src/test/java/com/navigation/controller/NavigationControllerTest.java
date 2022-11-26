package com.navigation.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.navigation.dto.BaseStationDTO;
import com.navigation.dto.MobileStationDTO;
import com.navigation.service.NavigationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import java.util.UUID;
import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(NavigationController.class)
class NavigationControllerTest {
    private static final String BASE_STATION_NAME = "Askeladd";
    private static final String ADD_BS_URL = "/bs/add";
    private static final String BS_OK_RESPONSE = "Base station added";
    private static final String BS_BAD_RESPONSE = "Base station not added. The counter is full";
    private static final String ADD_MS_URL = "/ms/add";
    private static final String MS_OK_RESPONSE = "Mobile station added";
    private static final String MS_BAD_RESPONSE = "Mobile station not added. The counter is full";

    @MockBean
    NavigationService navigationService;

    @Autowired
    MockMvc mockMvc;

    @Test
    void addBaseStationOkResponseTest() throws Exception{
        BaseStationDTO bs = new BaseStationDTO(BASE_STATION_NAME, 5, 5, 10);
        ObjectMapper om = new ObjectMapper();
        String requestBody = om.writeValueAsString(bs);
        Mockito.when(navigationService.addBaseStation(bs)).thenReturn(true);
        mockMvc.perform(post(ADD_BS_URL).contentType(MediaType.APPLICATION_JSON).content(requestBody))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(BS_OK_RESPONSE)));
    }

    @Test
    void addBaseStationBadResponseTest() throws Exception{
        BaseStationDTO bs = new BaseStationDTO(BASE_STATION_NAME, 5, 5, 10);
        ObjectMapper om = new ObjectMapper();
        String requestBody = om.writeValueAsString(bs);
        Mockito.when(navigationService.addBaseStation(bs)).thenReturn(false);
        mockMvc.perform(post(ADD_BS_URL).contentType(MediaType.APPLICATION_JSON).content(requestBody))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString(BS_BAD_RESPONSE)));
    }

    @Test
    void addMobileStationOkResponseTest() throws Exception{
        MobileStationDTO ms = new MobileStationDTO(10, 10);
        ObjectMapper om = new ObjectMapper();
        String requestBody = om.writeValueAsString(ms);
        Mockito.when(navigationService.addMobileStation(ms)).thenReturn(true);
        mockMvc.perform(post(ADD_MS_URL).contentType(MediaType.APPLICATION_JSON).content(requestBody))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(MS_OK_RESPONSE)));
    }

    @Test
    void addMobileStationBadResponseTest() throws Exception{
        MobileStationDTO ms = new MobileStationDTO(10, 10);
        ObjectMapper om = new ObjectMapper();
        String requestBody = om.writeValueAsString(ms);
        Mockito.when(navigationService.addMobileStation(ms)).thenReturn(false);
        mockMvc.perform(post(ADD_MS_URL).contentType(MediaType.APPLICATION_JSON).content(requestBody))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString(MS_BAD_RESPONSE)));
    }
}