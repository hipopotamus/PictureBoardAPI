package pictureboard.api.repository;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import pictureboard.api.domain.entity.*;
import pictureboard.api.dto.*;
import pictureboard.api.form.PictureSearchCond;

import javax.persistence.EntityManager;

import java.util.List;

import static org.springframework.util.StringUtils.*;
import static pictureboard.api.domain.entity.QAccount.*;
import static pictureboard.api.domain.entity.QFollow.*;
import static pictureboard.api.domain.entity.QLikes.*;
import static pictureboard.api.domain.entity.QPicture.*;
import static pictureboard.api.domain.entity.QPictureTag.*;
import static pictureboard.api.domain.entity.QTag.*;

public class PictureRepositoryImpl implements PictureRepositoryCustom{

    private final JPAQueryFactory jpaQueryFactory;

    public PictureRepositoryImpl(EntityManager em) {
        this.jpaQueryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Picture findPictureForDto(Long pictureId) {
        return jpaQueryFactory
                .selectFrom(picture)
                .distinct()
                .join(picture.account, account).fetchJoin()
                .join(picture.pictureTags, pictureTag).fetchJoin()
                .join(pictureTag.tag, tag).fetchJoin()
                .where(picture.id.eq(pictureId))
                .fetchOne();
    }

    @Override
    public List<Picture> findPictureByFollow(Long accountId, int size) {

        QAccount activeAccount = new QAccount("activeAccount");
        QAccount passiveAccount = new QAccount("passiveAccount");

        return jpaQueryFactory
                .selectFrom(picture)
                .join(picture.account, account).fetchJoin()
                .join(picture.pictureTags, pictureTag).fetchJoin()
                .join(pictureTag.tag, tag).fetchJoin()
                .where(account.id.eq(JPAExpressions
                        .select(follow.passiveAccount.id)
                        .from(follow)
                        .join(follow.activeAccount, activeAccount)
                        .join(follow.passiveAccount, passiveAccount)
                        .where(follow.activeAccount.id.eq(accountId))))
                .orderBy(picture.lastModifiedDate.desc())
                .limit(size)
                .fetch()
        ;
    }

    @Override
    public List<Picture> findPictureOrderByLikes(int size) {
        return jpaQueryFactory
                .selectFrom(picture)
                .join(picture.account, account).fetchJoin()
                .join(picture.pictureTags, pictureTag).fetchJoin()
                .join(pictureTag.tag, tag).fetchJoin()
                .orderBy(picture.likeCount.desc())
                .limit(size)
                .fetch();
    }

    @Override
    public QueryResults<Picture> picturePage(Pageable pageable) {

        return jpaQueryFactory
                .selectFrom(picture)
                .join(picture.account, account).fetchJoin()
                .orderBy(picture.lastModifiedDate.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();
    }

    @Override
    public QueryResults<Picture> pictureSearchPage(PictureSearchCond pictureSearchCond, Pageable pageable) {
        return jpaQueryFactory
                .select(picture)
                .distinct()
                .from(picture)
                .join(picture.account, account).fetchJoin()
                .leftJoin(picture.pictureTags, pictureTag)
                .leftJoin(pictureTag.tag, tag)
                .where(titleOrTagEq(pictureSearchCond.getTitleOrTag()),
                        nicknameEq(pictureSearchCond.getNickname()),
                        titleEq(pictureSearchCond.getTitle()),
                        tagEq(pictureSearchCond.getTagTitle()))
                .orderBy(picture.lastModifiedDate.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();
    }

    private BooleanExpression titleOrTagEq(String titleOrTag) {
        return hasText(titleOrTag) ? picture.title.contains(titleOrTag).or(tag.title.contains(titleOrTag)) : null;
    }

    private BooleanExpression nicknameEq(String nickname) {
        return hasText(nickname) ? account.nickname.contains(nickname) : null;
    }

    private BooleanExpression titleEq(String title) {
        return hasText(title) ? picture.title.contains(title) : null;
    }

    private Predicate tagEq(String tagTitle) {
        return hasText(tagTitle) ? tag.title.contains(tagTitle) : null;
    }
}
