package pictureboard.api.domain.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.hibernate.annotations.Where;
import pictureboard.api.domain.BaseTime;
import pictureboard.api.domain.Img;
import pictureboard.api.domain.constant.Gender;
import pictureboard.api.exception.NotBelowZeroException;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
@Entity
@EqualsAndHashCode(of = "id", callSuper = false)
@Where(clause = "deleted = false")
public class Account extends BaseTime {

    @Id @GeneratedValue
    @Column(name = "account_id")
    private Long id;

    private String username;

    private String password;

    private String nickname;

    @Embedded
    private Img profileImg;

    private String roles;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private LocalDate birthDate;

    private int activeFollowCount = 0;

    private int passiveFollowCount = 0;

    protected Account() {
    }

    //Oauth 생성자
    public Account(String username, String password, Img profileImg) {
        this.username = username;
        this.password = password;
        this.profileImg = profileImg;
    }

    //회원가입 생성자
    public Account(String username, String password, String nickname, Img profileImg, Gender gender, LocalDate birthDate) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.profileImg = profileImg;
        this.gender = gender;
        this.birthDate = birthDate;
    }

    public void settingRoles(String roles) {
        this.roles = roles;
    }

    public List<String> getRoleList() {
        if (this.roles.length() > 0) {
            return Arrays.asList(roles.split(","));
        }
        return new ArrayList<>();
    }

    //** Follow 추가, 제거
    public void addActiveFollowCount() {
        this.activeFollowCount += 1;
    }

    public void removeActiveFollowCount() {
        int tmpActiveFollowCount = this.activeFollowCount - 1;
        if (tmpActiveFollowCount < 0) {
            throw new NotBelowZeroException("activeFollow don't below zero");
        }
        this.activeFollowCount = tmpActiveFollowCount;
    }

    public void addPassiveFollowCount() {
        this.passiveFollowCount += 1;
    }

    public void removePassiveFollowCount() {
        int tmpPassiveFollowCount = this.passiveFollowCount - 1;
        if (tmpPassiveFollowCount < 0) {
            throw new NotBelowZeroException("passiveFollow don't below zero");
        }
        this.passiveFollowCount = tmpPassiveFollowCount;
    }
    //** Follow 추가, 제거

    public void updateNickname(String nickname) {
        this.nickname = nickname;
    }

    public void updateProfileImg(Img profileImg) {
        this.profileImg = profileImg;
    }
}
