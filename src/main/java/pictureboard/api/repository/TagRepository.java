package pictureboard.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pictureboard.api.domain.entity.Tag;

import java.util.List;

public interface TagRepository extends JpaRepository<Tag, Long> {

    boolean existsByTitle(String tagTitle);

    Tag findByTitle(String tagTitle);

    @Query("select t from Tag t order by t.relatedPictureCount desc")
    List<Tag> findAllAndOrderByRelatedPictureCount();
}
