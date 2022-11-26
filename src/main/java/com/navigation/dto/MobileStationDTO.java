package com.navigation.dto;

import java.util.Objects;

public class MobileStationDTO {
    private float x;
    private float y;

    public MobileStationDTO() {}

    public MobileStationDTO(float x, float y) {
        this.x = x;
        this.y = y;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MobileStationDTO)) return false;
        MobileStationDTO that = (MobileStationDTO) o;
        return Float.compare(that.x, x) == 0 && Float.compare(that.y, y) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "MobileStationDTO{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
