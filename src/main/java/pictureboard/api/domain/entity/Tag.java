package pictureboard.api.domain.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import pictureboard.api.domain.BaseTime;

import javax.persistence.*;

@Entity
@Getter
@EqualsAndHashCode(of = "id", callSuper = false)
@Where(clause = "deleted = false")
public class Tag extends BaseTime {

    @Id @GeneratedValue
    @Column(name = "tag_id")
    private Long id;

    private String title;

    private int relatedPictureCount = 0;

    protected Tag() {
    }

    public void addRelatedPictureCount() {
        relatedPictureCount++;
    }

    public void removeRelatedPictureCount() {
        relatedPictureCount--;
    }

    public Tag(String title) {
        this.title = title;
    }
}
