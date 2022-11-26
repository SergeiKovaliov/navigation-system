package com.navigation.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class PointTest {
    static Arguments [] sumTestArgs() {
        return new Arguments[]{
            Arguments.arguments(new Point(15, 10), new Point(10, 10), new Point(25, 20)),
            Arguments.arguments(new Point(15, 10), new Point(10, 10), new Point(30, 40))
        };
    }

    @ParameterizedTest
    @MethodSource("sumTestArgs")
    void sumTest(Point firstPoint, Point secondPoint, Point expected) {
        Point actual = firstPoint.sum(secondPoint);
        Assertions.assertEquals(expected, actual);
    }

    static Arguments [] diffTestArgs() {
        return new Arguments[]{
                Arguments.arguments(new Point(15, 10), new Point(10, 10), new Point(5, 0)),
                Arguments.arguments(new Point(15, 10), new Point(10, 10), new Point(30, 40))
        };
    }

    @ParameterizedTest
    @MethodSource("diffTestArgs")
    void diffTest(Point firstPoint, Point secondPoint, Point expected) {
        Point actual = firstPoint.diff(secondPoint);
        Assertions.assertEquals(expected, actual);
    }

    static Arguments [] multiplyTestArgs() {
        return new Arguments[]{
                Arguments.arguments(new Point(15, 10), 10, new Point(150, 100)),
                Arguments.arguments(new Point(15, 10), 10, new Point(30, 40))
        };
    }

    @ParameterizedTest
    @MethodSource("multiplyTestArgs")
    void multiplyTest(Point point, float multiplier, Point expected) {
        Point actual = point.multiply(multiplier);
        Assertions.assertEquals(expected, actual);
    }
}