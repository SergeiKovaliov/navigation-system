package com.navigation.dto;

import java.util.Objects;

public class BaseStationDTO {
    private String name;
    private float x;
    private float y;
    private float detectionRadiusInMeters;

    public BaseStationDTO() {}

    public BaseStationDTO(String name, float x, float y, float detectionRadiusInMeters) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.detectionRadiusInMeters = detectionRadiusInMeters;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getDetectionRadiusInMeters() {
        return detectionRadiusInMeters;
    }

    public void setDetectionRadiusInMeters(float detectionRadiusInMeters) {
        this.detectionRadiusInMeters = detectionRadiusInMeters;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BaseStationDTO)) return false;
        BaseStationDTO that = (BaseStationDTO) o;
        return Float.compare(that.x, x) == 0 && Float.compare(that.y, y) == 0 && Float.compare(that.detectionRadiusInMeters, detectionRadiusInMeters) == 0 && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, x, y, detectionRadiusInMeters);
    }

    @Override
    public String toString() {
        return "BaseStationDTO{" +
                "name='" + name + '\'' +
                ", x=" + x +
                ", y=" + y +
                ", detectionRadiusInMeters=" + detectionRadiusInMeters +
                '}';
    }
}
