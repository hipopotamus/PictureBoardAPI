package pictureboard.api.domain.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import pictureboard.api.domain.BaseTime;
import pictureboard.api.domain.entity.Picture;
import pictureboard.api.domain.entity.Tag;

import javax.persistence.*;

@Entity
@Getter
@EqualsAndHashCode(of = "id", callSuper = false)
@Where(clause = "deleted = false")
public class PictureTag extends BaseTime {

    @Id @GeneratedValue
    @Column(name = "pictureTag_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "picture_id")
    private Picture picture;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tag_id")
    private Tag tag;

    protected PictureTag() {
    }

    public PictureTag(Picture picture, Tag tag) {
        this.picture = picture;
        this.tag = tag;
    }

    public void changePicture(Picture picture) {
        this.picture = picture;
        picture.getPictureTags().add(this);
    }
}
