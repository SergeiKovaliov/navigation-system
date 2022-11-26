package com.navigation.service;

import com.navigation.dto.BaseStationDTO;
import com.navigation.dto.MobileStationDTO;
import com.navigation.model.BaseStation;
import com.navigation.model.DistanceAndPosition;
import com.navigation.model.Point;
import com.navigation.repository.BaseStationRepository;
import com.navigation.repository.ReportRepository;
import com.navigation.thread.BaseStationThread;
import com.navigation.thread.MobileStationThread;
import com.navigation.thread.MobileStationsDetectionThread;
import com.navigation.util.Singleton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Map;
import java.util.UUID;

@Service
public class NavigationService {
    private Map<UUID, Point> mobileStations;
    private Map<UUID, DistanceAndPosition> detectedMobileStations;
    private ReportRepository reportRepository;
    private BaseStationRepository bsRepo;
    private int baseStationCounter = 0;
    private int mobileStationCounter = 0;
    private boolean isOn = false;

    public NavigationService() {}

    @Autowired
    public NavigationService(Map<UUID, Point> mobileStations, Map<UUID, DistanceAndPosition> detectedMobileStations, ReportRepository reportRepository, BaseStationRepository bsRepo) {
        this.mobileStations = mobileStations;
        this.detectedMobileStations = detectedMobileStations;
        this.reportRepository = reportRepository;
        this.bsRepo = bsRepo;
    }

    public boolean addBaseStation(BaseStationDTO bsDTO) {
        if (baseStationCounter < 99) {
            baseStationCounter++;
            UUID id = UUID.randomUUID();
            BaseStation bs = new BaseStation(id, bsDTO.getName(), bsDTO.getX(), bsDTO.getY(), bsDTO.getDetectionRadiusInMeters());
            String threadName = bsDTO.getName();
            Singleton.getBaseStationsExecutor().submit(new BaseStationThread(threadName, bs, bsRepo, reportRepository, mobileStations));
            return true;
        }
        return false;
    }

    public boolean addMobileStation(MobileStationDTO msDTO) {
        if (!isOn) {
            Thread msDetector = new MobileStationsDetectionThread(mobileStations, detectedMobileStations, reportRepository);
            isOn = true;
            msDetector.start();
        }
        if (mobileStationCounter < 99) {
            mobileStationCounter++;
            Point ms = new Point();
            UUID id = UUID.randomUUID();
            System.out.println(id);
            ms.setX(msDTO.getX());
            ms.setY(msDTO.getY());
            mobileStations.put(id, ms);
            String threadName = "MS#" + id;
            Singleton.getMobileStationsExecutor().submit(new MobileStationThread(threadName, id, ms, mobileStations));
            return true;
        }
        return false;
    }
}
