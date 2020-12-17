package pl.edu.pjwstk.skmapi.utils;

import java.util.Random;

public class Randomizer {

    public static int getRandomNumberInRange(int min, int max) {
        if (min >= max) {
            return max;
        }
        Random random = new Random();
        return random.nextInt((max - min) + 1) + min;
    }

    public static Object getRandomElementFromArray(Object[] array) {
        int randomElementIndex = getRandomNumberInRange(0, array.length - 1);
        return array[randomElementIndex];
    }
}
