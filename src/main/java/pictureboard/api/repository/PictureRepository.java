package pictureboard.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pictureboard.api.domain.entity.Account;
import pictureboard.api.domain.entity.Picture;

import java.util.List;
import java.util.Optional;

public interface PictureRepository extends JpaRepository<Picture, Long>, PictureRepositoryCustom {

    @Query("select distinct p from Picture p " +
            "join fetch p.account a " +
            "join fetch p.pictureTags pt " +
            "join fetch pt.tag t " +
            "where a.id = :accountId " +
            "order by p.lastModifiedDate desc")
    List<Picture> findByAccountId(@Param("accountId") Long accountId);

    @Query("select distinct p from Likes l " +
            "join l.account a " +
            "join l.picture p " +
            "join fetch p.account pa " +
            "join fetch p.pictureTags pt " +
            "join fetch pt.tag t " +
            "where a.id = :accountId")
    List<Picture> findByAccountLikes(@Param("accountId") Long accountId);

    @Modifying
    @Query("update Picture p set p.deleted = true where p.account.id =:accountId")
    void deleteByAccount(@Param("accountId") Long accountId);

    @Query("select p from Picture p join fetch p.account a where p.id =:pictureId")
    Optional<Picture> findWithAccount(@Param("pictureId") Long pictureId);

}
