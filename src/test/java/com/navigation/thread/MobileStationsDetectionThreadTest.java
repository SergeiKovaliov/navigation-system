package com.navigation.thread;

import com.navigation.model.DistanceAndPosition;
import com.navigation.model.Point;
import com.navigation.repository.ReportRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import java.util.*;

@AutoConfigureTestEntityManager
class MobileStationsDetectionThreadTest {
    @Autowired
    private ReportRepository reportRepository;

    MobileStationsDetectionThread msThread = new MobileStationsDetectionThread(reportRepository);
    static Arguments[] getPointTestArgs() {
        return new Arguments[]{
                Arguments.arguments(new Point(10, 10), new Point(5, 5), 12, 12, new Point(5, 5)),
                Arguments.arguments(new Point(10, 10), new Point(5, 5), 12, 12, new Point(10, 10))
        };
    }

    @ParameterizedTest
    @MethodSource("getPointTestArgs")
    void getPointTest(Point firstPoint, Point secondPoint, float a, float d, Point expected) {
        Point actual = msThread.getPoint(firstPoint, secondPoint, a, d);
        Assertions.assertEquals(expected, actual);
    }

    static Arguments[] getATestArgs() {
        return new Arguments[]{
                Arguments.arguments(10, 10, 12, 6),
                Arguments.arguments(10, 10, 12, 10)
        };
    }

    @ParameterizedTest
    @MethodSource("getATestArgs")
    void getATest(float radiusOne, float radiusTwo, float distance, float expected) {
        float actual = msThread.getA(radiusOne, radiusTwo, distance);
        Assertions.assertEquals(expected, actual);
    }

    static Arguments[] getBestMobileStationPositionTestArgs() {
        return new Arguments[]{
                Arguments.arguments(new DistanceAndPosition(5, 5, 10),
                                    new DistanceAndPosition(10,10, 10),
                                    new DistanceAndPosition(20, 0, 20),
                                    new DistanceAndPosition(7.071068f, 5f, 13.333333f))
        };
    }

    @ParameterizedTest
    @MethodSource("getBestMobileStationPositionTestArgs")
    void getBestMobileStationPositionTest(DistanceAndPosition positionOne, DistanceAndPosition positionTwo,
                                          DistanceAndPosition positionThree, DistanceAndPosition expected) {
        List<DistanceAndPosition> positions = new LinkedList<>();
        positions.add(positionOne);
        positions.add(positionTwo);
        positions.add(positionThree);
        DistanceAndPosition actual = msThread.getBestMobileStationPosition(positions);
        Assertions.assertEquals(expected, actual);
    }

    static Arguments[] getLongestDistanceTestArgs() {
        return new Arguments[]{
                Arguments.arguments(new DistanceAndPosition(5, 5, 10),
                                    new DistanceAndPosition(10,10, 10),
                                    new DistanceAndPosition(20, 0, 20), 7.071068f)
        };
    }

    @ParameterizedTest
    @MethodSource("getLongestDistanceTestArgs")
    void getLongestDistanceTest(DistanceAndPosition distOne, DistanceAndPosition distTwo, DistanceAndPosition distThree,
                                float expected) {
        List<DistanceAndPosition> positions = new LinkedList<>();
        positions.add(distOne);
        positions.add(distTwo);
        positions.add(distThree);
        float actual = msThread.getLongestDistance(positions);
        Assertions.assertEquals(expected, actual);
    }

    static Arguments[] getAllPositionsTestArgs() {
        return new Arguments[]{
                Arguments.arguments(new DistanceAndPosition(5, 5, 10),
                                    new DistanceAndPosition(10,10, 10),
                                    new DistanceAndPosition(5, 0, 10))
        };
    }

    @ParameterizedTest
    @MethodSource("getAllPositionsTestArgs")
    void getAllPositionsTest(DistanceAndPosition pointOne, DistanceAndPosition pointTwo, DistanceAndPosition expectedPoint) {
        List<DistanceAndPosition> expected = new LinkedList<>();
        expected.add(expectedPoint);
        List<DistanceAndPosition> info = new LinkedList<>();
        info.add(pointOne);
        info.add(pointTwo);
        List<DistanceAndPosition> actual = msThread.getAllPositions(info);
        Assertions.assertEquals(expected, actual);
    }

    static Arguments[] isCircleInCircleTestArgs() {
        return new Arguments[]{
                Arguments.arguments(10, 20, 15, false),
                Arguments.arguments(2, 20, 10, true)
        };
    }

    @ParameterizedTest
    @MethodSource("isCircleInCircleTestArgs")
    void isCircleInCircleTest(float distance, float firstRadius, float secondRadius, boolean expected) {
        boolean actual = msThread.isCircleInCircle(distance, firstRadius, secondRadius);
        Assertions.assertEquals(expected, actual);
    }

    static Arguments[] isTheSameCircleTestArgs() {
        return new Arguments[]{
                Arguments.arguments(0, 10, 10, true),
                Arguments.arguments(10, 10, 10, false)
        };
    }

    @ParameterizedTest
    @MethodSource("isTheSameCircleTestArgs")
    void isTheSameCircleTest(float distance, float firstRadius, float secondRadius, boolean expected) {
        boolean actual = msThread.isTheSameCircle(distance, firstRadius, secondRadius);
        Assertions.assertEquals(expected, actual);
    }
}