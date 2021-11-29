package pictureboard.api.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import pictureboard.api.exception.NotBelowZeroException;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@EqualsAndHashCode(of = "id", callSuper = false)
public class Picture extends BaseBy{

    @Id @GeneratedValue
    @Column(name = "picture_id")
    private Long id;

    private String title;

    private String description;

    @Embedded
    private Img pictureImg;

    private int likeCount = 0;

    @Enumerated(EnumType.STRING)
    private PictureType pictureType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;

    @OneToMany(mappedBy = "picture")
    private List<PictureTag> pictureTags = new ArrayList<PictureTag>();

    protected Picture() {
    }

    //createPicture
    public Picture(String title, String description, Img pictureImg, PictureType pictureType, Account account) {
        this.title = title;
        this.description = description;
        this.pictureImg = pictureImg;
        this.pictureType = pictureType;
        this.account = account;
    }

    public void addLikeCount() {
        this.likeCount += 1;
    }

    public void removeLikeCount() {
        int resetLikeCount = this.likeCount - 1;
        if (resetLikeCount < 0) {
            throw new NotBelowZeroException("likeCount don't below zero");
        }
        this.likeCount -= 1;
    }

}
