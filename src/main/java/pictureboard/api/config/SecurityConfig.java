package pictureboard.api.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.filter.CorsFilter;
import pictureboard.api.filter.Oauth2SuccessHandler;
import pictureboard.api.repository.AccountRepository;
import pictureboard.api.filter.JwtAuthenticationFilter;
import pictureboard.api.filter.JwtAuthorizationFilter;
import pictureboard.api.service.CustomOAuth2UserService;
import pictureboard.api.service.JwtService;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CorsFilter corsFilter;
    private final AccountRepository accountRepository;
    private final CustomOAuth2UserService customOAuth2UserService;
    private final JwtService jwtService;
    private final Oauth2SuccessHandler oauth2SuccessHandler;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .formLogin().disable()
                .httpBasic().disable()
                .headers().frameOptions().disable();


        http
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http
                .addFilter(corsFilter)
                .addFilter(new JwtAuthenticationFilter(authenticationManager(), jwtService))
                .addFilter(new JwtAuthorizationFilter(authenticationManager(), accountRepository, jwtService));

        http
                .authorizeRequests()
                .mvcMatchers("/", "/account", "/login", "/myLogin","/swagger-ui.html", "/webjars/**",
                        "/v2/**", "/swagger-resources/**", "/stomp/**", "/pub/**", "/sub/**", "/chat/**").permitAll()
                .mvcMatchers(HttpMethod.GET, "/picture/**").permitAll()
                .anyRequest().permitAll();

        http
                .oauth2Login()
                .successHandler(oauth2SuccessHandler)
                .userInfoEndpoint()
                .userService(customOAuth2UserService);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }
}
