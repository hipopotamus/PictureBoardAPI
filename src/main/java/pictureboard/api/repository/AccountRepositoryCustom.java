package pictureboard.api.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pictureboard.api.dto.AccountDto;

public interface AccountRepositoryCustom {

    Page<AccountDto> searchPageByNickname(String nickname, Pageable pageable);
}
