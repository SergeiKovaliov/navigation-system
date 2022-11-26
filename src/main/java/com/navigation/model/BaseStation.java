package com.navigation.model;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "base_stations")
public class BaseStation {
    @Id
    @Column(columnDefinition = "UUID", unique = true)
    UUID id;
    String name;
    float x;
    float y;

    @Column(name = "detection_radius_in_meters")
    float detectionRadiusInMeters;

    @OneToMany(mappedBy = "id.bsId", fetch = FetchType.EAGER)
    Set<Report> reports;

    public BaseStation() {}

    public BaseStation(UUID id, String name, float x, float y, float detectionRadiusInMeters) {
        this.id = id;
        this.name = name;
        this.x = x;
        this.y = y;
        this.detectionRadiusInMeters = detectionRadiusInMeters;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
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
        if (!(o instanceof BaseStation)) return false;
        BaseStation that = (BaseStation) o;
        return Float.compare(that.x, x) == 0 && Float.compare(that.y, y) == 0 && Float.compare(that.detectionRadiusInMeters, detectionRadiusInMeters) == 0 && Objects.equals(id, that.id) && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, x, y, detectionRadiusInMeters);
    }

    @Override
    public String toString() {
        return "BaseStation{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", x=" + x +
                ", y=" + y +
                ", detectionRadiusInMeters=" + detectionRadiusInMeters +
                '}';
    }
}
