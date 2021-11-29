package pictureboard.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pictureboard.api.domain.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {

    boolean existsByUsername(String username);

    boolean existsByNickname(String username);

    Account findByUsername(String username);

    Account findByNickname(String nickname);
}
