package pictureboard.api.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import pictureboard.api.repository.AccountRepository;
import pictureboard.api.domain.entity.Account;
import pictureboard.api.domain.UserAccount;
import pictureboard.api.service.JwtService;
import pictureboard.api.variable.JwtProperties;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private final AccountRepository accountRepository;
    private final JwtService jwtService;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, AccountRepository accountRepository,
                                  JwtService jwtService) {
        super(authenticationManager);
        this.accountRepository = accountRepository;
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String jwtHeader = request.getHeader("Authorization");

        if (jwtHeader == null || !jwtHeader.startsWith("Bearer")) {
            chain.doFilter(request, response);
            return;
        }

        String jwtToken = request.getHeader("Authorization").replace("Bearer ", "");

        String username = jwtService.verifyToken(jwtToken, JwtProperties.SECRET, "username");

        if (username != null) {
            Account account = accountRepository.findByUsername(username);

            UserAccount userAccount = new UserAccount(account);
            Authentication authentication = new UsernamePasswordAuthenticationToken(userAccount, null, userAccount.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        chain.doFilter(request, response);

    }
}
