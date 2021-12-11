package pictureboard.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pictureboard.api.domain.entity.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("select c From Comment c " +
            "join fetch c.account a " +
            "join c.picture p " +
            "where p.id = :pictureId")
    List<Comment> findByPictureFetchAccount(@Param("pictureId") Long pictureId);

    List<Comment> findByPictureId(Long pictureId);

    @Modifying
    @Query("update Comment c " +
            "set c.deleted = true " +
            "where c.account.id =:accountId " +
            "or c.picture in (select p from Picture p join p.account a where a.id =:accountId)")
    void deleteByAccount(@Param("accountId") Long accountId);

    @Modifying
    @Query("update Comment c " +
            "set c.deleted = true " +
            "where c.picture.id =:pictureId")
    void deleteByPicture(@Param("pictureId") Long pictureId);

    @Query("select c from Comment c join fetch c.account a where c.id =:commentId")
    Optional<Comment> findWithAccount(@Param("commentId") Long commentId);
}
