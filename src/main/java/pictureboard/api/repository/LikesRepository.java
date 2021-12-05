package pictureboard.api.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pictureboard.api.domain.entity.Account;
import pictureboard.api.domain.entity.Likes;
import pictureboard.api.domain.entity.Picture;

public interface LikesRepository extends JpaRepository<Likes, Long> {

    @Query("select l from Likes l join l.account a join l.picture p where a = :account and p = :picture")
    Likes findByMemberAndPicture(@Param("account") Account account, @Param("picture") Picture picture);
}
