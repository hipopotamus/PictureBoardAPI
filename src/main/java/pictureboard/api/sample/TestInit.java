package pictureboard.api.sample;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import pictureboard.api.domain.constant.Gender;
import pictureboard.api.domain.constant.PictureType;
import pictureboard.api.domain.entity.Account;
import pictureboard.api.domain.entity.Picture;
import pictureboard.api.service.*;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Profile("local")
@Component
@RequiredArgsConstructor
public class TestInit {

    private final InitService initService;

//    @PostConstruct
    public void init() throws IOException {
        initService.init();
    }

    @Component
    @RequiredArgsConstructor
    static class InitService {

        private final AccountService accountService;
        private final PictureService pictureService;
        private final FollowService followService;
        private final LikesService likesService;
        private final CommentService commentService;

        public void init() throws IOException {

            //Account 생성
            for (int i = 0; i < 30; i++) {
                accountService.joinAccountTest("account" + i, "1234", "nickname" + i,
                        Gender.MAN, LocalDate.now());
            }

            //picture 생성
            Account account1 = accountService.findByNickname("nickname1");
            Account account2 = accountService.findByNickname("nickname2");
            Account account3 = accountService.findByNickname("nickname3");

            for (int i = 0; i < 30; i++) {
                pictureService.createPictureTest("title" + i, "description" +i, PictureType.PEOPLE,
                        account1.getId(), new ArrayList<String>(Arrays.asList(i + "tag", "tag" + i)));
            }
            for (int i = 30; i < 60; i++) {
                pictureService.createPictureTest("title" + i, "description" +i, PictureType.PEOPLE,
                        account2.getId(), new ArrayList<String>(Arrays.asList(i + "tag", "tag" + i)));
            }
            for (int i = 60; i < 90; i++) {
                pictureService.createPictureTest("title" + i, "description" +i, PictureType.PEOPLE,
                        account3.getId(), new ArrayList<String>(Arrays.asList(i + "tag", "tag" + i)));
            }

            //likes 생성
            List<Picture> pictures = pictureService.findAll();

            Random random = new Random();
            for (int i = 4; i < 30; i++) {
                Account account = accountService.findByNickname("nickname" + i);
                for (int j = 0; j < 45; j++) {
                    likesService.onClick(account.getId(), pictures.get(random.nextInt(90)).getId());
                }
            }

            for (int i = 0; i < 45; i++) {
                likesService.onClick(account1.getId(), pictures.get(random.nextInt(55) + 31).getId());
            }

            //follow 생성
            followService.onClick(account1.getId(), account2.getId());
            followService.onClick(account1.getId(), account3.getId());
            followService.onClick(account2.getId(), account1.getId());
            followService.onClick(account2.getId(), account3.getId());
            followService.onClick(account3.getId(), account1.getId());
            followService.onClick(account3.getId(), account2.getId());

            for (int i = 4; i < 30; i++) {
                Account account = accountService.findByNickname("nickname" + i);
                for (int j = 0; j < 29; j++) {
                    int randomNumber = random.nextInt(30);
                    Account byNickname = accountService.findByNickname("nickname" + randomNumber);
                    if (account.getId().equals(byNickname.getId())) {
                        continue;
                    }
                    followService.onClick(account.getId(), byNickname.getId());
                }
            }

            //comment 생성
            for (int i = 0; i < 30; i++) {
                Account account = accountService.findByNickname("nickname" + i);
                for (int j = 0; j < 30; j++) {
                    int randomNumber = random.nextInt(90);
                    commentService.createComment(pictures.get(randomNumber).getId(), account.getId(),
                            account.getNickname() + ": testContent" + i);
                }
            }

        }
    }

}
