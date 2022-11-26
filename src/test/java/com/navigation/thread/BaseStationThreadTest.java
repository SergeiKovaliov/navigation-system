package com.navigation.thread;

import com.navigation.repository.BaseStationRepository;
import com.navigation.repository.ReportRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;

@AutoConfigureTestEntityManager
class BaseStationThreadTest {
    @Autowired
    BaseStationRepository bsRepo;

    @Autowired
    ReportRepository reportRepository;

    BaseStationThread bsThreadTest = new BaseStationThread(bsRepo, reportRepository);

    static Arguments[] getDistanceTestArgs() {
        return new Arguments[]{
                Arguments.arguments(5, 10, 5, 10, 7.071068f),
                Arguments.arguments(5, 10, 5, 10, 10f)
        };
    }

    @ParameterizedTest
    @MethodSource("getDistanceTestArgs")
    void getDistanceTest(float msX, float bsX, float msY, float bsY, float expected) {
        float actual = bsThreadTest.getDistance(msX, bsX, msY, bsY);
        Assertions.assertEquals(expected, actual);
    }

    static Arguments[] isInRangeTestArgs() {
        return new Arguments[]{
                Arguments.arguments(7, 10, true),
                Arguments.arguments(10, 7, false),
                Arguments.arguments(10, 7, true)
        };
    }

    @ParameterizedTest
    @MethodSource("isInRangeTestArgs")
    void isInRangeTest(float distance, float radius, boolean expected) {
        boolean actual = bsThreadTest.isInRange(distance, radius);
        Assertions.assertEquals(expected, actual);
    }
}