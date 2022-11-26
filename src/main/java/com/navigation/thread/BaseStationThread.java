package com.navigation.thread;

import com.navigation.model.*;
import com.navigation.repository.BaseStationRepository;
import com.navigation.repository.ReportRepository;
import java.sql.Timestamp;
import java.util.Map;
import java.util.UUID;

public class BaseStationThread extends Thread {
    private BaseStation bs;
    private BaseStationRepository bsRepository;
    private ReportRepository reportRepository;
    private Map<UUID, Point> mobileStations;

    public BaseStationThread(BaseStationRepository bsRepository, ReportRepository reportRepository) {
        this.bsRepository = bsRepository;
        this.reportRepository = reportRepository;
    }

    public BaseStationThread(String name, BaseStation bs, BaseStationRepository bsRepository, ReportRepository reportRepository, Map<UUID, Point> mobileStations) {
        super(name);
        this.bs = bs;
        this.bsRepository = bsRepository;
        this.reportRepository = reportRepository;
        this.mobileStations = mobileStations;
    }

    public void run() {
        bsRepository.save(bs);
        while (true) {
            detectMS();
        }
    }

    private void detectMS() {
        for (Map.Entry<UUID, Point> ms : mobileStations.entrySet()) {
            float msX = ms.getValue().getX();
            float msY = ms.getValue().getY();
            float distance = getDistance(msX, bs.getX(), msY, bs.getY());
            if (isInRange(distance, bs.getDetectionRadiusInMeters())) {
                UUID msID = ms.getKey();
                ReportID id = new ReportID(bs, msID);
                Timestamp time = new Timestamp((System.currentTimeMillis() / 1000) * 1000);
                Report report = new Report(id, distance, time);
                if (reportRepository.findById(bs.getId(), msID) != null) {
                    reportRepository.deleteById(report.getId());
                }
                reportRepository.save(report);
            }
        }
        try {
            sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    //the next 2 methods are public because we need to test them
    public float getDistance(float msX, float bsX, float msY, float bsY) {
        return (float) Math.sqrt(Math.pow((msX - bsX), 2) + Math.pow((msY - bsY), 2));
    }

    public boolean isInRange(float distance, float radius) {
        return distance <= radius;
    }
}
