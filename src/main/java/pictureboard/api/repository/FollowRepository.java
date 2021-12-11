package pictureboard.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pictureboard.api.domain.entity.Account;
import pictureboard.api.domain.entity.Follow;

import java.util.List;
import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow, Long> {

    @Query("select f from Follow f " +
            "join f.activeAccount a " +
            "join f.passiveAccount p " +
            "where a.id = :activeFollowId and p.id = :passiveFollowId")
    Optional<Follow> findByActiveAndPassive(@Param("activeFollowId") Long activeFollowId,
                                           @Param("passiveFollowId") Long passiveFollowId);

    @Query("select f from Follow f " +
            "join f.activeAccount a " +
            "join f.passiveAccount p " +
            "where a.id =:accountId or p.id =:accountId")
    List<Follow> findByAccountId(@Param("accountId") Long accountId);

    @Modifying
    @Query("update Follow f " +
            "set f.deleted = true " +
            "where f.activeAccount.id = :accountId or f.passiveAccount.id =:accountId")
    void deleteByAccount(@Param("accountId") Long accountId);
}
