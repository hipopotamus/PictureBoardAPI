package pictureboard.api.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.NameTokenizers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import pictureboard.api.domain.UserAccount;

import java.util.Optional;

@Configuration(proxyBeanMethods = false)
public class AppConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                .setSourceNameTokenizer(NameTokenizers.UNDERSCORE)
                .setDestinationNameTokenizer(NameTokenizers.UNDERSCORE)
                ;
        return modelMapper;
    }

    @Bean
    public AuditorAware<String> auditorProvider() {
        return new AuditorAware<String>() {
            @Override
            public Optional<String> getCurrentAuditor() {
                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                if (authentication == null) {
                    return Optional.of("testUser");
                }
                Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                if (principal == "anonymousUser") {
                    return Optional.of(principal.toString());
                }
                UserAccount userAccount = (UserAccount) principal;
                return Optional.of(userAccount.getUsername());
            }
        };
    }
}
