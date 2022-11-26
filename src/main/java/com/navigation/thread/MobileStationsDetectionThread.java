package com.navigation.thread;

import com.navigation.model.DistanceAndPosition;
import com.navigation.model.Point;
import com.navigation.model.Report;
import com.navigation.repository.ReportRepository;
import java.util.*;
import java.util.stream.Collectors;

public class MobileStationsDetectionThread extends Thread{
    private Map<UUID, Point> mobileStations;
    private Map<UUID, DistanceAndPosition> detectedMobileStations;
    private ReportRepository reportRepository;

    public MobileStationsDetectionThread(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    public MobileStationsDetectionThread(Map<UUID, Point> mobileStations, Map<UUID, DistanceAndPosition> detectedMobileStations, ReportRepository reportRepository) {
        super();
        this.mobileStations = mobileStations;
        this.detectedMobileStations = detectedMobileStations;
        this.reportRepository = reportRepository;
    }

    public void run() {
        while(true) {
            Set<UUID> msIds = mobileStations.keySet();
            msIds.stream().forEach(this::calculatePosition);
            try {
                sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void calculatePosition(UUID id) {
        List<Report> reports = reportRepository.findReportsByMSId(id);

        if (!reports.isEmpty()) {
            DistanceAndPosition msInfo = new DistanceAndPosition();
            Report newestReport = reports.stream().max(Comparator.comparing(Report::getTimestamp)).get();
            int minimalTime = newestReport.getTimestamp().getNanos() - 2000;
            reports = reports.stream().filter(rep -> rep.getTimestamp().getNanos() >= minimalTime).collect(Collectors.toList());
            List<UUID> bsIDs = reports.stream()
                    .map(report -> report.getId().getBsId().getId())
                    .collect(Collectors.toList());
            List<DistanceAndPosition> bsInfo = getBaseStationsInfoByID(bsIDs, id);
            int size = reports.size();

            if (size == 1) {
                msInfo.setX(reports.get(0).getId().getBsId().getX());
                msInfo.setY(reports.get(0).getId().getBsId().getY());
                msInfo.setDistance(reports.get(0).getDistance());
            } else {
                List<DistanceAndPosition> positions = getAllPositions(bsInfo);
                msInfo = getBestMobileStationPosition(positions);
            }
            detectedMobileStations.put(id, msInfo);
        }
    }

    public Point getPoint(Point firstPoint, Point secondPoint, float a, float d) {
        return firstPoint.sum(secondPoint.diff(firstPoint).multiply(a / d));
    }

    public float getA(float radiusOne, float radiusTwo, float distance) {
        return (float) (Math.pow(radiusOne, 2) - Math.pow(radiusTwo, 2) + Math.pow(distance, 2)) / (2 * distance);
    }

    public float getDistance(float msX, float bsX, float msY, float bsY) {
        return (float) Math.sqrt(Math.pow((msX - bsX), 2) + Math.pow((msY - bsY), 2));
    }

    public DistanceAndPosition getBestMobileStationPosition(List<DistanceAndPosition> positions) {
        DistanceAndPosition msInfo = new DistanceAndPosition();
        int size = positions.size();
        List<Float> x = positions.stream().map(DistanceAndPosition::getX).collect(Collectors.toList());
        float xSum = 0;
        for (float pos : x) {
            xSum += pos;
        }
        float xAverage = xSum / size;
        List<Float> y = positions.stream().map(DistanceAndPosition::getY).collect(Collectors.toList());
        float ySum = 0;
        for (float pos : y) {
            ySum += pos;
        }
        float yAverage = ySum / size;
        float longestDistance = getLongestDistance(positions);
        msInfo.setX(xAverage);
        msInfo.setY(yAverage);
        msInfo.setDistance(longestDistance);
        return msInfo;
    }

    public float getLongestDistance(List<DistanceAndPosition> positions) {
        int size = positions.size();
        List<Float> distances = new LinkedList<>();
        for (int i = 0; i < size; i++) {
            float xOne = positions.get(i).getX();
            float yOne = positions.get(i).getY();
            for (int j = i + 1; j < size; j++) {
                float xTwo = positions.get(j).getX();
                float yTwo = positions.get(j).getY();
                distances.add(getDistance(xTwo, xOne, yTwo, yOne));
            }
        }
        return distances.stream().max(Comparator.comparing(dist -> dist)).get() / 2;
    }

    public List<DistanceAndPosition> getAllPositions(List<DistanceAndPosition> bsInfo) {
        List<DistanceAndPosition> positions = new ArrayList<>();
        int size = bsInfo.size();
        for (int i = 0; i < size; i++) {
            float firstRadius = bsInfo.get(i).getDistance();
            float bsOneX = bsInfo.get(i).getX();
            float bsOneY = bsInfo.get(i).getY();
            for (int j = i + 1; j < size; j++) {
                float secondRadius = bsInfo.get(j).getDistance();
                float bsTwoX = bsInfo.get(j).getX();
                float bsTwoY = bsInfo.get(j).getY();
                float d = getDistance(bsTwoX, bsOneX, bsTwoY, bsOneY);
                DistanceAndPosition position = new DistanceAndPosition();

                if (isTheSameCircle(d, firstRadius, secondRadius)) {
                    position.setDistance(firstRadius);
                    position.setX(bsOneX);
                    position.setY(bsOneY);
                    positions.add(position);
                    continue;
                }

                if (isCircleInCircle(d, firstRadius, secondRadius)) {
                    position.setDistance(secondRadius);
                    position.setX(bsTwoX);
                    position.setY(bsTwoY);
                    positions.add(position);
                    continue;
                }

                float a = getA(firstRadius, secondRadius, d);
                Point pointOne = new Point(bsOneX, bsOneY);
                Point pointTwo = new Point(bsTwoX, bsTwoY);
                Point point = getPoint(pointOne, pointTwo, a, d);
                position.setX(point.getX());
                position.setY(point.getY());
                float bestDistance = Math.min(firstRadius, secondRadius);
                position.setDistance(bestDistance);
                positions.add(position);
            }
        }
        return positions;
    }

    public boolean isCircleInCircle(float distance, float firstRadius, float secondRadius) {
        return distance < Math.abs(firstRadius - secondRadius);
    }

    public boolean isTheSameCircle(float distance, float firstRadius, float secondRadius){
        return distance == 0 && firstRadius == secondRadius;
    }

    private List<DistanceAndPosition> getBaseStationsInfoByID(List<UUID> ids, UUID msID) {
        List<DistanceAndPosition> bsInfo = new ArrayList<>();
        List<Object[]> bsObj = new ArrayList<>();

        for (UUID bsId : ids) {
            bsObj.addAll(reportRepository.findBSInfo(bsId, msID));
        }

        for (Object [] obj : bsObj) {
            DistanceAndPosition bsI = new DistanceAndPosition();
            bsI.setDistance((float) obj[0]);
            bsI.setX((float) obj[1]);
            bsI.setY((float) obj[2]);
            bsInfo.add(bsI);
        }
        return bsInfo;
    }
}
