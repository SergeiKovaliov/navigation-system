package com.navigation.repository;

import static org.assertj.core.api.Assertions.assertThat;
import com.navigation.model.BaseStation;
import com.navigation.model.DistanceAndPosition;
import com.navigation.model.Report;
import com.navigation.model.ReportID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@DataJpaTest
class ReportRepositoryTest {
    private static final String BS_NAME = "Askeladd";

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    ReportRepository reportRepo;

    @Test
    void findByIdTest() {
        UUID bsID = UUID.randomUUID();
        UUID msID = UUID.randomUUID();
        BaseStation bs = new BaseStation(bsID, BS_NAME, 5, 5, 10);
        ReportID id = new ReportID(bs, msID);
        Report report = new Report(id, 15, new Timestamp(System.currentTimeMillis()));
        entityManager.persist(bs);
        entityManager.persist(report);
        Report expectedReport = reportRepo.findById(bsID, msID);
        assertThat(report).isEqualTo(expectedReport);
    }

    @Test
    void dontFindByIdTest() {
        UUID bsID = UUID.randomUUID();
        UUID msID = UUID.randomUUID();
        BaseStation bs = new BaseStation(bsID, BS_NAME, 5, 5, 10);
        ReportID id = new ReportID(bs, msID);
        Report report = new Report(id, 15, new Timestamp(System.currentTimeMillis()));
        entityManager.persist(bs);
        entityManager.persist(report);
        UUID wrongID = UUID.randomUUID();
        Report expectedReport = reportRepo.findById(bsID, wrongID);
        assertThat(expectedReport).isNull();
    }

    @Test
    void findReportsByMSIdTest() {
        UUID msID = UUID.randomUUID();
        BaseStation bsOne = new BaseStation(UUID.randomUUID(), BS_NAME, 5, 5, 10);
        BaseStation bsTwo = new BaseStation(UUID.randomUUID(), BS_NAME + "1", 10, 10, 15);
        BaseStation bsThree = new BaseStation(UUID.randomUUID(), BS_NAME + "2", 15, 15, 20);
        entityManager.persist(bsOne);
        entityManager.persist(bsTwo);
        entityManager.persist(bsThree);
        ReportID idOne = new ReportID(bsOne, msID);
        ReportID idTwo = new ReportID(bsTwo, msID);
        ReportID idThree = new ReportID(bsThree, msID);
        Report reportOne = new Report(idOne, 15, new Timestamp(System.currentTimeMillis()));
        Report reportTwo = new Report(idTwo, 10, new Timestamp(System.currentTimeMillis()));
        Report reportThree = new Report(idThree, 5, new Timestamp(System.currentTimeMillis()));
        entityManager.persist(reportOne);
        entityManager.persist(reportTwo);
        entityManager.persist(reportThree);
        List<Report> reports = reportRepo.findReportsByMSId(msID);
        assertThat(reports).hasSize(3).contains(reportOne, reportTwo, reportThree);
    }

    @Test
    void dontFindReportsByMSIdTest() {
        UUID msID = UUID.randomUUID();
        UUID wrongMSID = UUID.randomUUID();
        BaseStation bsOne = new BaseStation(UUID.randomUUID(), BS_NAME, 5, 5, 10);
        BaseStation bsTwo = new BaseStation(UUID.randomUUID(), BS_NAME + "1", 10, 10, 15);
        BaseStation bsThree = new BaseStation(UUID.randomUUID(), BS_NAME + "2", 15, 15, 20);
        entityManager.persist(bsOne);
        entityManager.persist(bsTwo);
        entityManager.persist(bsThree);
        ReportID idOne = new ReportID(bsOne, wrongMSID);
        ReportID idTwo = new ReportID(bsTwo, wrongMSID);
        ReportID idThree = new ReportID(bsThree, wrongMSID);
        Report reportOne = new Report(idOne, 15, new Timestamp(System.currentTimeMillis()));
        Report reportTwo = new Report(idTwo, 10, new Timestamp(System.currentTimeMillis()));
        Report reportThree = new Report(idThree, 5, new Timestamp(System.currentTimeMillis()));
        entityManager.persist(reportOne);
        entityManager.persist(reportTwo);
        entityManager.persist(reportThree);
        List<Report> reports = reportRepo.findReportsByMSId(msID);
        assertThat(reports).isEmpty();
    }

    @Test
    void findBSInfoTest() {
        UUID bsID = UUID.randomUUID();
        UUID msID = UUID.randomUUID();
        List<DistanceAndPosition> bsInfo = new ArrayList<>();
        DistanceAndPosition info = new DistanceAndPosition(30, 10, 10);
        BaseStation bs = new BaseStation(bsID, BS_NAME, 10, 10, 30);
        entityManager.persist(bs);
        ReportID id = new ReportID(bs, msID);
        entityManager.persist(new Report(id, 30, new Timestamp(System.currentTimeMillis())));
        List<Object[]> bsObj = reportRepo.findBSInfo(bsID, msID);
        for (Object [] obj : bsObj) {
            DistanceAndPosition bsInf = new DistanceAndPosition();
            bsInf.setDistance((float) obj[0]);
            bsInf.setX((float) obj[1]);
            bsInf.setY((float) obj[2]);
            bsInfo.add(bsInf);
        }
        assertThat(bsInfo).hasSize(1).contains(info);
    }

    @Test
    void dontFindBSInfoTest() {
        UUID bsID = UUID.randomUUID();
        UUID msID = UUID.randomUUID();
        List<Object[]> bsObj = reportRepo.findBSInfo(bsID, msID);
        assertThat(bsObj).isEmpty();
    }
}