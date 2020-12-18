package pl.edu.pjwstk.skmapi.utils;

public class Utils {
    public static <T> T fallbackIfNull(T mainChoice, T alternativeChoice) {
        return mainChoice != null
                ? mainChoice
                : alternativeChoice;
    }
}
