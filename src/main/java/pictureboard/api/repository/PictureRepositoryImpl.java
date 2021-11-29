package pictureboard.api.repository;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;
import pictureboard.api.domain.QPictureTag;
import pictureboard.api.domain.QTag;
import pictureboard.api.dto.*;
import pictureboard.api.form.PictureSearchCond;

import javax.persistence.EntityManager;

import java.util.List;

import static org.springframework.util.StringUtils.*;
import static pictureboard.api.domain.QAccount.*;
import static pictureboard.api.domain.QFollow.*;
import static pictureboard.api.domain.QLikes.*;
import static pictureboard.api.domain.QPicture.*;
import static pictureboard.api.domain.QPictureTag.*;
import static pictureboard.api.domain.QTag.*;

public class PictureRepositoryImpl implements PictureRepositoryCustom{

    private final JPAQueryFactory jpaQueryFactory;

    public PictureRepositoryImpl(EntityManager em) {
        this.jpaQueryFactory = new JPAQueryFactory(em);
    }

    @Override
    public LookUpPictureDto makeLookUpPictureDto(Long pictureId, Long loginAccountId) {
        return jpaQueryFactory
                .select(new QLookUpPictureDto(
                        picture.id,
                        picture.title,
                        picture.description,
                        picture.pictureImg,
                        picture.likeCount,
                        picture.pictureType,
                        new QLookUpPictureAccountDto(
                                account.id,
                                account.username,
                                account.nickname,
                                account.profileImg,
                                account.activeFollowCount,
                                account.passiveFollowCount
                        ),
                        account.id.eq(loginAccountId),
                        JPAExpressions
                                .select(likes.count().eq(1L))
                                .from(likes)
                                .where(likes.account.id.eq(loginAccountId),
                                        likes.picture.id.eq(pictureId)),
                        JPAExpressions
                                .select(follow.count().eq(1L))
                                .from(follow)
                                .where(follow.activeAccount.id.eq(loginAccountId),
                                        follow.passiveAccount.id.eq(account.id))
                ))
                .from(picture)
                .join(picture.account, account)
                .where(picture.id.eq(pictureId))
                .fetchOne()
                ;
    }

    @Override
    public Page<PicturePageDto> picturePage(Pageable pageable) {
        QueryResults<PicturePageDto> results = jpaQueryFactory
                .select(new QPicturePageDto(
                                picture.id,
                                picture.title,
                                picture.pictureImg,
                                new QPicturePageAccountDto(
                                        account.id,
                                        account.nickname,
                                        account.profileImg
                                ),
                                picture.lastModifiedDate
                        )
                )
                .from(picture)
                .join(picture.account, account)
                .orderBy(picture.lastModifiedDate.desc())
                .fetchResults();

        List<PicturePageDto> content = results.getResults();
        long total = results.getTotal();
        return new PageImpl<>(content, pageable, total);
    }

    @Override
    public Page<PicturePageDto> pictureSearchPage(PictureSearchCond pictureSearchCond, Pageable pageable) {
        QueryResults<PicturePageDto> results = jpaQueryFactory
                .select(new QPicturePageDto(
                                picture.id,
                                picture.title,
                                picture.pictureImg,
                                new QPicturePageAccountDto(
                                        account.id,
                                        account.nickname,
                                        account.profileImg
                                ),
                                picture.lastModifiedDate
                        )
                )
                .distinct()
                .from(picture)
                .join(picture.account, account)
                .leftJoin(picture.pictureTags, pictureTag)
                .leftJoin(pictureTag.tag, tag)
                .where(titleOrTagEq(pictureSearchCond.getTitleOrTag()),
                        nicknameEq(pictureSearchCond.getNickname()))
                .orderBy(picture.lastModifiedDate.desc())
                .fetchResults();

        List<PicturePageDto> content = results.getResults();
        long total = results.getTotal();
        return new PageImpl<>(content, pageable, total);
    }

    private BooleanExpression titleOrTagEq(String titleOrTag) {
        if (hasText(titleOrTag) && hasText(titleOrTag)) {
            return picture.title.contains(titleOrTag).or(tag.title.contains(titleOrTag));
        } else if (hasText(titleOrTag) && !hasText(titleOrTag)){
            return picture.title.contains(titleOrTag);
        } else if (!hasText(titleOrTag) && hasText(titleOrTag)) {
            return tag.title.contains(titleOrTag);
        } else {
            return null;
        }
    }

    private BooleanExpression nicknameEq(String nickname) {
        return hasText(nickname) ? account.nickname.contains(nickname) : null;
    }
}
