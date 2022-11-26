package com.navigation.thread;

import com.navigation.model.Point;
import com.navigation.util.Singleton;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

public class MobileStationThread extends Thread {
    private UUID id;
    private Point ms;
    private Map<UUID, Point> mobileStations;

    public MobileStationThread(String name, UUID id, Point ms, Map<UUID, Point> mobileStations) {
        super(name);
        this.id = id;
        this.ms = ms;
        this.mobileStations = mobileStations;
    }

    public void run() {
        Singleton.getLogger().info(ms.toString());
        while (true) {
            makeMove();
            Singleton.getLogger().info("MS#" + id + " on position: x: " + ms.getX() + " y: " + ms.getY());
            try {
                sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void makeMove() {
        Random random = new Random();
        int randomNum = random.nextInt(4);
        switch (randomNum) {
            case 0 :
                moveUp();
                break;
            case 1 :
                moveDown();
                break;
            case 2 :
                moveLeft();
                break;
            case 3 :
                moveRight();
                break;
        }
    }

    private void moveUp() {
        if (ms.getY() < 100) {
            ms.setY(ms.getY() + 1);
            mobileStations.replace(id, ms);
        }
    }

    private void moveDown() {
        if (ms.getY() > 0) {
            ms.setY(ms.getY() - 1);
            mobileStations.replace(id, ms);
        }
    }

    private void moveLeft() {
        if (ms.getX() > 0) {
            ms.setX(ms.getX() - 1);
            mobileStations.replace(id, ms);
        }
    }

    private void moveRight() {
        if (ms.getX() < 100) {
            ms.setX(ms.getX() + 1);
            mobileStations.replace(id, ms);
        }
    }
}
