package com.navigation.model;

import java.util.Objects;

public class DistanceAndPosition {
    private float distance;
    private float x;
    private float y;

    public DistanceAndPosition() {
    }

    public DistanceAndPosition(float distance, float x, float y) {
        this.distance = distance;
        this.x = x;
        this.y = y;
    }

    public float getDistance() {
        return distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
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
        if (!(o instanceof DistanceAndPosition)) return false;
        DistanceAndPosition that = (DistanceAndPosition) o;
        return Float.compare(that.distance, distance) == 0 && Float.compare(that.x, x) == 0 && Float.compare(that.y, y) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(distance, x, y);
    }

    @Override
    public String toString() {
        return "BaseStationRadiusAndPosition{" +
                "distance=" + distance +
                ", x=" + x +
                ", y=" + y +
                '}';
    }
}
