package pictureboard.api.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
@EqualsAndHashCode(of = "id", callSuper = false)
public class Likes extends BaseTime{

    @Id
    @GeneratedValue
    @Column(name = "likes_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "picture_id")
    private Picture picture;

    @Enumerated(EnumType.STRING)
    private OnClickStatus onClickStatus;

    protected Likes() {
    }

    //creatLikes
    public Likes(Account account, Picture picture, OnClickStatus onClickStatus) {
        this.account = account;
        this.picture = picture;
        this.onClickStatus = onClickStatus;
    }

    public void switchStatus() {
        if (onClickStatus == OnClickStatus.OFF) {
            onClickStatus = OnClickStatus.ON;
            picture.addLikeCount();
        } else {
            onClickStatus = OnClickStatus.OFF;
            picture.removeLikeCount();
        }
    }
}
