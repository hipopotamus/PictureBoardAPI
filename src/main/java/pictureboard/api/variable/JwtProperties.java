package pictureboard.api.variable;

public interface JwtProperties {
    String SECRET = "cos";
    int EXPIRATION_TIME = 60000 * 30;
    String TOKEN_PREFIX = "Bearer ";
    String HEADER_STRING = "Authorization";
}
