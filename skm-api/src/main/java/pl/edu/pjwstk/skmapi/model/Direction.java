package pl.edu.pjwstk.skmapi.model;

import java.util.Random;

public class Direction {

    public static int START_TO_END = 1;
    public static int END_TO_START = -1;

    private int direction;

    Direction() {
        Random rand = new Random();
        this.direction = rand.nextBoolean() ? 1 : -1;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

}
