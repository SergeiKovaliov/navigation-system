package com.navigation.service;

import com.navigation.model.DistanceAndPosition;
import com.navigation.model.MobileStationInfo;
import com.navigation.util.Singleton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class MobileStationInfoService {
    private static final String MS_INFO_DOESNT_EXIST_ERROR = "No info about mobile station with this ID";
    private static final String NO_ERRORS = "No errors";
    private Map<UUID, DistanceAndPosition> detectedMobileStations;

    @Autowired
    public MobileStationInfoService(Map<UUID, DistanceAndPosition> detectedMobileStations) {
        this.detectedMobileStations = detectedMobileStations;
    }

    public MobileStationInfo getMobileStationByID(UUID id) {
        MobileStationInfo msInfo = new MobileStationInfo();
        Singleton.getLogger().info("DETECTED MS : " + detectedMobileStations);
        DistanceAndPosition detectedMs = detectedMobileStations.get(id);
        msInfo.setMobileID(id);
        if (detectedMs != null) {
            msInfo.setX(detectedMs.getX());
            msInfo.setY(detectedMs.getY());
            msInfo.setErrorCode(0);
            msInfo.setErrorDescription(NO_ERRORS);
            msInfo.setErrorRadius(detectedMs.getDistance());
        } else {
            msInfo.setX(0);
            msInfo.setY(0);
            msInfo.setErrorCode(1);
            msInfo.setErrorDescription(MS_INFO_DOESNT_EXIST_ERROR);
            msInfo.setErrorRadius(0);
        }
        return msInfo;
    }
}
