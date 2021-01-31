package com.ronaldarias.backend.repositories;

import com.ronaldarias.backend.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@ActiveProfiles("test")
public class UserRepositoryTest {

    @Autowired
    TestEntityManager testEntityManager;

    @Autowired
    UserRepository userRepository;

    @Test
    public void findByUsernameWhenUserExistsThenReturnsUser() {
        User user = new User();
        user.setUsername("test-user");
        user.setDisplayName("test-display");
        user.setPassword("P4ssword");

        testEntityManager.persist(user);
        User userInDB = userRepository.findByUsername("test-user");

        assertThat(userInDB).isEqualTo(user);
    }

    @Test
    public void findByUsernameWhenUserDoesNotExistThenReturnsNull() {
        User userInDB = userRepository.findByUsername("test-user");
        assertThat(userInDB).isNull();
    }
}
