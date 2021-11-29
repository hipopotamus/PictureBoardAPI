package pictureboard.api.repository;

import com.querydsl.core.annotations.QueryProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pictureboard.api.domain.Picture;
import pictureboard.api.domain.Tag;

import java.util.List;

public interface TagRepository extends JpaRepository<Tag, Long> {

    boolean existsByTitle(String tagTitle);

    Tag findByTitle(String tagTitle);
}
