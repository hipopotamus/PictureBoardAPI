package pictureboard.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pictureboard.api.domain.PictureTag;
import pictureboard.api.domain.Tag;

import java.util.List;

public interface PictureTagRepository extends JpaRepository<PictureTag, Long> {

    @Query("select pt from PictureTag pt join fetch pt.tag where pt.picture.id = :pictureId")
    List<PictureTag> findTitlesByPictureId(@Param("pictureId") Long pictureId);
}
