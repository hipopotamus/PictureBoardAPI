package pictureboard.api.repository;


import com.querydsl.core.QueryResults;
import org.springframework.data.domain.Pageable;
import pictureboard.api.domain.entity.Picture;
import pictureboard.api.form.PictureSearchCond;

import java.util.List;

public interface PictureRepositoryCustom {

    Picture findPictureForDto(Long pictureId);

    List<Picture> findPictureByFollow(Long accountId, int size);

    List<Picture> findPictureOrderByLikes(int size);

    QueryResults<Picture> picturePage(Pageable pageable);

    QueryResults<Picture> pictureSearchPage(PictureSearchCond pictureSearchCond, Pageable pageable);
}
