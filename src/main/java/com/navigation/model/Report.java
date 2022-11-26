package com.navigation.model;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "reports")
public class Report {
    @EmbeddedId
    ReportID id;
    float distance;
    Timestamp timestamp;

    public Report() {}

    public Report(ReportID id, float distance, Timestamp timestamp) {
        this.id = id;
        this.distance = distance;
        this.timestamp = timestamp;
    }

    public ReportID getId() {
        return id;
    }

    public void setId(ReportID id) {
        this.id = id;
    }

    public float getDistance() {
        return distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Report)) return false;
        Report report = (Report) o;
        return Float.compare(report.distance, distance) == 0 && Objects.equals(id, report.id) && Objects.equals(timestamp, report.timestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, distance, timestamp);
    }

    @Override
    public String toString() {
        return "Report{" +
                "id=" + id +
                ", distance=" + distance +
                ", timestamp=" + timestamp +
                '}';
    }
}
