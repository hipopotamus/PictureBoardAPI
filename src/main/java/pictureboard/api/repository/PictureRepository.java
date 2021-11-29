package pictureboard.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pictureboard.api.domain.Account;
import pictureboard.api.domain.Picture;

import java.util.List;

public interface PictureRepository extends JpaRepository<Picture, Long>, PictureRepositoryCustom {
    List<Picture> findByAccount(Account account);
}
