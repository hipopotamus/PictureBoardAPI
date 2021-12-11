package pictureboard.api.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pictureboard.api.domain.entity.Account;
import pictureboard.api.domain.entity.Likes;
import pictureboard.api.domain.entity.Picture;

import java.util.List;
import java.util.Optional;

public interface LikesRepository extends JpaRepository<Likes, Long> {

    @Query("select l from Likes l join l.account a join l.picture p where a = :account and p = :picture")
    Optional<Likes> findByAccountAndPicture(@Param("account") Account account, @Param("picture") Picture picture);

    @Query("select l from Likes l " +
            "join l.account a " +
            "where a.id =:accountId")
    List<Likes> findByAccountId(@Param("account") Long accountId);

    List<Likes> findByPictureId(Long pictureId);

    @Modifying
    @Query("update Likes l " +
            "set l.deleted = true " +
            "where l.account.id =:accountId " +
            "or l.picture in (select p From Picture p join p.account a where a.id =:accountId)")
    void deleteByAccount(@Param("accountId") Long accountId);

    @Modifying
    @Query("update Likes l " +
            "set l.deleted = true " +
            "where l.picture.id =:pictureId")
    void deleteByPicture(@Param("pictureId") Long pictureId);
}
