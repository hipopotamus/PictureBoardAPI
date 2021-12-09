package pictureboard.api.domain.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import pictureboard.api.domain.BaseBy;

import javax.persistence.*;

@Entity
@Getter
@EqualsAndHashCode(of = "id", callSuper = false)
@Where(clause = "deleted = false")
public class Comment extends BaseBy {

    @Id @GeneratedValue
    @Column(name = "comment_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "picture_id")
    private Picture picture;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;

    private String content;

    protected Comment() {
    }

    public Comment(Picture picture, Account account, String content) {
        this.picture = picture;
        this.account = account;
        this.content = content;
    }
}
