package pictureboard.api.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pictureboard.api.domain.UserAccount;
import pictureboard.api.variable.JwtProperties;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class JwtService {

    public String createAuthJwtToken(UserAccount userAccount) {
        return JWT.create()
                .withSubject(userAccount.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + JwtProperties.EXPIRATION_TIME))
                .withClaim("id", userAccount.getAccount().getId())
                .withClaim("username", userAccount.getAccount().getUsername())
                .sign(Algorithm.HMAC512(JwtProperties.SECRET));

    }

    public String verifyToken(String jwtToken, String secretKey, String claim) {
        return JWT.require(Algorithm.HMAC512(secretKey)).build()
                .verify(jwtToken)
                .getClaim(claim)
                .asString();
    }
}
