package pictureboard.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.context.SecurityContextHolder;
import pictureboard.api.domain.UserAccount;

import java.util.Optional;

@EnableJpaAuditing
@SpringBootApplication
public class PictureboardApplication {

    public static void main(String[] args) {
        SpringApplication.run(PictureboardApplication.class, args);
    }
}
