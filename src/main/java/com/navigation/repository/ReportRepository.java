package com.navigation.repository;

import com.navigation.model.Report;
import com.navigation.model.ReportID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.UUID;

public interface ReportRepository extends JpaRepository<Report, ReportID> {
    String SQL_SELECT_REPORTS_BY_MS_ID = "SELECT * FROM reports WHERE ms_id = ?;";
    String SQL_SELECT_REPORT_BY_ID = "SELECT * FROM reports WHERE bs_id = ? AND ms_id = ?;";
    String SQL_SELECT_DISTANCE_AND_POSITION_BY_BS_ID = "SELECT reports.distance, base_stations.x, base_stations.y FROM reports JOIN base_stations " +
            "ON base_stations.id = reports.bs_id WHERE reports.bs_id = ? AND reports.ms_id = ?;";

    @Query(value = SQL_SELECT_REPORT_BY_ID, nativeQuery = true)
    Report findById(UUID bsID, UUID msID);

    @Query(value = SQL_SELECT_REPORTS_BY_MS_ID, nativeQuery = true)
    List<Report> findReportsByMSId(@Param("ms_id") UUID id);

    @Transactional
    @Query(value = SQL_SELECT_DISTANCE_AND_POSITION_BY_BS_ID, nativeQuery = true)
    List<Object[]> findBSInfo(UUID bsIDs, UUID msID);
}
