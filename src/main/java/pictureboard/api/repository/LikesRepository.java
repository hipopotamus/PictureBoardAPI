package pictureboard.api.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pictureboard.api.domain.Account;
import pictureboard.api.domain.Likes;
import pictureboard.api.domain.Picture;

import java.util.List;

public interface LikesRepository extends JpaRepository<Likes, Long> {

    @Query("select l from Likes l join l.account a join l.picture p where a = :account and p = :picture")
    Likes findByMemberAndPicture(@Param("account") Account account, @Param("picture") Picture picture);
}
