package pictureboard.api.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pictureboard.api.domain.constant.Gender;
import pictureboard.api.domain.entity.Account;
import pictureboard.api.domain.entity.Follow;
import pictureboard.api.repository.AccountRepository;

import java.io.IOException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SoftDeleteServiceTest {

    @Autowired
    AccountService accountService;

    @Autowired
    SoftDeleteService softDeleteService;

    @Test
    public void softTest() throws IOException {
        //Account 생성
        for (int i = 0; i < 30; i++) {
            accountService.joinAccountTest("account" + i, "1234", "nickname" + i,
                    Gender.MAN, LocalDate.now());
        }

        Account account = accountService.findByNickname("nickname1");

    }

}