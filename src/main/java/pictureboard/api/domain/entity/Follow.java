package pictureboard.api.domain.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import pictureboard.api.domain.BaseTime;
import pictureboard.api.domain.constant.OnClickStatus;
import pictureboard.api.domain.entity.Account;

import javax.persistence.*;

@Entity
@Getter
@EqualsAndHashCode(of = "id", callSuper = false)
@Where(clause = "deleted = false")
public class Follow extends BaseTime {

    @Id @GeneratedValue
    @Column(name = "follow_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "activeAccount_id")
    private Account activeAccount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "passiveAccount_id")
    private Account passiveAccount;


    @Enumerated(EnumType.STRING)
    private OnClickStatus onClickStatus;

    protected Follow() {
    }

    public Follow(Account activeAccount, Account passiveAccount, OnClickStatus onClickStatus) {
        this.activeAccount = activeAccount;
        this.passiveAccount = passiveAccount;
        this.onClickStatus = onClickStatus;
    }

    public void switchStatus() {
        if (onClickStatus == OnClickStatus.OFF) {
            onClickStatus = OnClickStatus.ON;
            activeAccount.addActiveFollowCount();
            passiveAccount.addPassiveFollowCount();
        } else {
            onClickStatus = OnClickStatus.OFF;
            activeAccount.removeActiveFollowCount();
            passiveAccount.removePassiveFollowCount();
        }
    }
}
