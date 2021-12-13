package pictureboard.api.domain.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.hibernate.annotations.Where;
import pictureboard.api.domain.BaseTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@EqualsAndHashCode(of = "id", callSuper = false)
@Where(clause = "deleted = false")
public class ChatRoom extends BaseTime {

    @Id @GeneratedValue
    @Column(name = "chatRoom_id")
    private Long id;
    private String name;

    protected ChatRoom() {
    }

    public ChatRoom(String name) {
        this.name = name;
    }
}
