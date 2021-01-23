package pl.edu.pjwstk.skmapi.security.util;

public class SecurityConstants {
    public static final String SECRET = "SKMAPI_SECRET_KEY";
    public static final long EXPIRATION_TIME = 900_000; // 15 minutes
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
}
