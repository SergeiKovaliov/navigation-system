package com.navigation.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Embeddable
public class ReportID implements Serializable {
    @ManyToOne(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    @JoinColumn(name = "bsId", referencedColumnName = "id")
    private BaseStation bsId;

    @Column(name = "ms_id", columnDefinition = "UUID")

    private UUID msId;

    public ReportID() {}

    public ReportID(BaseStation bsId, UUID msId) {
        this.bsId = bsId;
        this.msId = msId;
    }

    public BaseStation getBsId() {
        return bsId;
    }

    public void setBsId(BaseStation bsId) {
        this.bsId = bsId;
    }

    public UUID getMsId() {
        return msId;
    }

    public void setMsId(UUID msId) {
        this.msId = msId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ReportID)) return false;
        ReportID reportID = (ReportID) o;
        return Objects.equals(bsId, reportID.bsId) && Objects.equals(msId, reportID.msId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bsId, msId);
    }

    @Override
    public String toString() {
        return "ReportID{" +
                "bsId=" + bsId +
                ", msId=" + msId +
                '}';
    }
}
