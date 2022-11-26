package com.navigation.controller;

import com.navigation.model.MobileStationInfo;
import com.navigation.service.MobileStationInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.UUID;

@RestController
public class MobileStationInfoController {
    private MobileStationInfoService service;

    @Autowired
    public MobileStationInfoController(MobileStationInfoService service) {
        this.service = service;
    }

    @GetMapping("/location/{uuid}")
    @ResponseBody
    public MobileStationInfo getMobileStationInfo(@PathVariable UUID uuid) {
        return service.getMobileStationByID(uuid);
    }
}
