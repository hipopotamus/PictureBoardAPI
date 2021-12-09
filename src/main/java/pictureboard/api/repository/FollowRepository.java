package pictureboard.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pictureboard.api.domain.entity.Account;
import pictureboard.api.domain.entity.Follow;

import java.util.List;

public interface FollowRepository extends JpaRepository<Follow, Long> {

    @Query("select f from Follow f " +
            "join f.activeAccount a " +
            "join f.passiveAccount p " +
            "where a = :activeFollowAccount and p = :passiveFollowAccount")
    Follow findByActiveFollowAccountAndPassiveFollowAccount(@Param("activeFollowAccount") Account activeFollowAccount,
                                                            @Param("passiveFollowAccount") Account passiveFollowAccount);

    @Query("select f from Follow f " +
            "join f.activeAccount a " +
            "join f.passiveAccount p " +
            "where a.id =:accountId or p.id =:accountId")
    List<Follow> findByAccountId(@Param("accountId") Long accountId);
}
