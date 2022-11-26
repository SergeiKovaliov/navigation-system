package com.navigation.model;

import java.util.Objects;
import java.util.UUID;

public class MobileStationInfo {
    private UUID mobileID;
    private float x;
    private float y;
    private float errorRadius;
    private int errorCode;
    private String errorDescription;

    public MobileStationInfo() {}

    public MobileStationInfo(UUID mobileID, float x, float y, float errorRadius, int errorCode, String errorDescription) {
        this.mobileID = mobileID;
        this.x = x;
        this.y = y;
        this.errorRadius = errorRadius;
        this.errorCode = errorCode;
        this.errorDescription = errorDescription;
    }

    public UUID getMobileID() {
        return mobileID;
    }

    public void setMobileID(UUID mobileID) {
        this.mobileID = mobileID;
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

    public float getErrorRadius() {
        return errorRadius;
    }

    public void setErrorRadius(float errorRadius) {
        this.errorRadius = errorRadius;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorDescription() {
        return errorDescription;
    }

    public void setErrorDescription(String errorDescription) {
        this.errorDescription = errorDescription;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MobileStationInfo)) return false;
        MobileStationInfo that = (MobileStationInfo) o;
        return Float.compare(that.x, x) == 0 && Float.compare(that.y, y) == 0 && Float.compare(that.errorRadius, errorRadius) == 0 && errorCode == that.errorCode && Objects.equals(mobileID, that.mobileID) && Objects.equals(errorDescription, that.errorDescription);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mobileID, x, y, errorRadius, errorCode, errorDescription);
    }

    @Override
    public String toString() {
        return "MobileStationInfo{" +
                "mobileID=" + mobileID +
                ", x=" + x +
                ", y=" + y +
                ", errorRadius=" + errorRadius +
                ", errorCode=" + errorCode +
                ", errorDescription='" + errorDescription + '\'' +
                '}';
    }
}
