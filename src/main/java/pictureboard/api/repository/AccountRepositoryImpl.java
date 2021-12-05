package pictureboard.api.repository;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;
import pictureboard.api.domain.entity.QAccount;
import pictureboard.api.dto.AccountDto;
import pictureboard.api.dto.QAccountDto;

import javax.persistence.EntityManager;

import java.util.List;

import static pictureboard.api.domain.entity.QAccount.*;

public class AccountRepositoryImpl implements AccountRepositoryCustom{

    private final JPAQueryFactory jpaQueryFactory;

    public AccountRepositoryImpl(EntityManager em) {
        this.jpaQueryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<AccountDto> searchPageByNickname(String nickname, Pageable pageable) {

        QueryResults<AccountDto> results = jpaQueryFactory
                .select(new QAccountDto(
                                account.id,
                                account.username,
                                account.nickname,
                                account.profileImg,
                                account.gender,
                                account.birthDate,
                                account.activeFollowCount,
                                account.passiveFollowCount
                        )
                )
                .from(account)
                .where(nicknameEq(nickname))
                .fetchResults();

        List<AccountDto> content = results.getResults();
        long total = results.getTotal();
        return new PageImpl<>(content, pageable, total);
    }

    private BooleanExpression nicknameEq(String nickname) {
        return StringUtils.hasText(nickname) ? account.nickname.contains(nickname) : null;
    }
}
