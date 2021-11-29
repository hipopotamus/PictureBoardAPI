package pictureboard.api.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import pictureboard.api.repository.PictureTagRepository;

import javax.persistence.*;

@Entity
@Getter
@EqualsAndHashCode(of = "id", callSuper = false)
public class PictureTag extends BaseTime{

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
}
