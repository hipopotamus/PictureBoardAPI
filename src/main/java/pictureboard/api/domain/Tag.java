package pictureboard.api.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
@EqualsAndHashCode(of = "id", callSuper = false)
public class Tag extends BaseTime{

    @Id @GeneratedValue
    @Column(name = "tag_id")
    private Long id;

    private String title;

    protected Tag() {
    }

    public Tag(String title) {
        this.title = title;
    }
}
