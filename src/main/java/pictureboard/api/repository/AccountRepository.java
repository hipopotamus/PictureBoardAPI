package pictureboard.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pictureboard.api.domain.entity.Account;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account, Long>, AccountRepositoryCustom {

    boolean existsByUsername(String username);

    boolean existsByNickname(String username);

    Account findByUsername(String username);

    @Query("select pa from Follow f " +
            "join f.activeAccount aa " +
            "join f.passiveAccount pa where aa.id = :accountId")
    List<Account> findPassiveFollowAccount(@Param("accountId") Long accountId);

    @Query("select aa from Follow f " +
            "join f.activeAccount aa " +
            "join f.passiveAccount pa where pa.id = :accountId")
    List<Account> findActiveFollowAccount(@Param("accountId") Long accountId);

    Account findByNickname(String nickname);
}
