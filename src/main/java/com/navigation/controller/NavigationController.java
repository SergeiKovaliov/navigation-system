package com.navigation.controller;

import com.navigation.dto.BaseStationDTO;
import com.navigation.dto.MobileStationDTO;
import com.navigation.service.NavigationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NavigationController {
    private static final String BS_OK_RESPONSE = "Base station added";
    private static final String BS_BAD_RESPONSE = "Base station not added. The counter is full";
    private static final String MS_OK_RESPONSE = "Mobile station added";
    private static final String MS_BAD_RESPONSE = "Mobile station not added. The counter is full";
    private NavigationService navigationService;

    @Autowired
    public NavigationController(NavigationService navigationService) {
        this.navigationService = navigationService;
    }

    @PostMapping("/bs/add")
    public ResponseEntity addBaseStation(@RequestBody BaseStationDTO bsDTO) {
        if (navigationService.addBaseStation(bsDTO)) {
            return ResponseEntity.ok(BS_OK_RESPONSE);
        }
        return ResponseEntity.badRequest().body(BS_BAD_RESPONSE);
    }

    @PostMapping("/ms/add")
    public ResponseEntity addMobileStation(@RequestBody MobileStationDTO msDTO) {
        if (navigationService.addMobileStation(msDTO)) {
            return ResponseEntity.ok(MS_OK_RESPONSE);
        }
        return ResponseEntity.badRequest().body(MS_BAD_RESPONSE);
    }
}
