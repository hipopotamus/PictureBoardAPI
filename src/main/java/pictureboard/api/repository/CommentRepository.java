package pictureboard.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pictureboard.api.domain.entity.Comment;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("select c From Comment c " +
            "join fetch c.account a " +
            "join c.picture p " +
            "where p.id = :pictureId")
    List<Comment> findByPictureFetchAccount(@Param("pictureId") Long pictureId);
}
