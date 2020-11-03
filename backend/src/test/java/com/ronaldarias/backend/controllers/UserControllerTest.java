package com.ronaldarias.backend.controllers;

import com.ronaldarias.backend.model.User;
import com.ronaldarias.backend.model.shared.GenericResponse;
import com.ronaldarias.backend.repositories.UserRepository;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserControllerTest {

    public static final String API_USERS = "/api/1.0/users";

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private UserRepository userRepository;

    @Before
    public void before() {
        cleanUpDataBase();
    }

    @Test
    public void postUserWhenUserIsValidThenResponseOk() {
        // Arrange
        User user = createValidUser();

        // Act
        ResponseEntity<Object> response = testRestTemplate.postForEntity(API_USERS, user, Object.class);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void postUserWhenUserIsValidThenUserSavedToDatabase() {
        // Arrange
        User user = createValidUser();

        // Act
        testRestTemplate.postForEntity(API_USERS, user, Object.class);

        // Assert
        assertThat(userRepository.count()).isEqualTo(1);
    }

    @Test
    public void postUserWhenUserIsValidThenReceiveSuccessMessage() {
        // Arrange
        User user = createValidUser();

        // Act
        ResponseEntity<GenericResponse> response = testRestTemplate.postForEntity(API_USERS, user, GenericResponse.class);

        // Assert
        assertThat(response.getBody().getMessage()).isNotNull();
    }

    @Test
    public void postUserWhenUserIsValidThenPasswordIsHashedInDatabase() {
        // Arrange
        User user = createValidUser();

        // Act
        testRestTemplate.postForEntity(API_USERS, user, GenericResponse.class);
        List<User> userList = userRepository.findAll();
        User userInDB = userList.get(0);

        // Assert
        assertThat(userInDB.getPassword()).isNotEqualTo(user.getPassword());
    }

    private User createValidUser() {
        User user = new User();
        user.setUsername("test-user");
        user.setDisplayName("test-display");
        user.setPassword("P4ssword");
        return user;
    }

    private void cleanUpDataBase() {
        userRepository.deleteAll();
    }
}
