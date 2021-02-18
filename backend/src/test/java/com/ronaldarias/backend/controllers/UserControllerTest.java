package com.ronaldarias.backend.controllers;

import com.ronaldarias.backend.error.ApiError;
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
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
        User user = TestUtil.createValidUser();

        // Act
        ResponseEntity<Object> response = postSignup(user, Object.class);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void postUserWhenUserIsValidThenUserSavedToDatabase() {
        // Arrange
        User user = TestUtil.createValidUser();

        // Act
        postSignup(user, Object.class);

        // Assert
        assertThat(userRepository.count()).isEqualTo(1);
    }

    @Test
    public void postUserWhenUserIsValidThenReceiveSuccessMessage() {
        // Arrange
        User user = TestUtil.createValidUser();

        // Act
        ResponseEntity<GenericResponse> response = postSignup(user, GenericResponse.class);

        // Assert
        assertThat(response.getBody().getMessage()).isNotNull();
    }

    @Test
    public void postUserWhenUserIsValidThenPasswordIsHashedInDatabase() {
        // Arrange
        User user = TestUtil.createValidUser();

        // Act
        postSignup(user, GenericResponse.class);
        List<User> userList = userRepository.findAll();
        User userInDB = userList.get(0);

        // Assert
        assertThat(userInDB.getPassword()).isNotEqualTo(user.getPassword());
    }

    @Test
    public void postUserWhenUserHasNullDisplayNameThenReceiveBadRequest() {
        User user = TestUtil.createValidUser();
        user.setDisplayName(null);
        ResponseEntity<Object> response = postSignup(user, Object.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void postUserWhenUserHasNullUsernameThenReceiveBadRequest() {
        User user = TestUtil.createValidUser();
        user.setUsername(null);
        ResponseEntity<Object> response = postSignup(user, Object.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void postUserWhenUserHasNullPasswordThenReceiveBadRequest() {
        User user = TestUtil.createValidUser();
        user.setPassword(null);
        ResponseEntity<Object> response = postSignup(user, Object.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void postUserWhenUserHasUsernameWithLessThanRequiredThenReceiveBadRequest() {
        User user = TestUtil.createValidUser();
        user.setUsername("abc");
        ResponseEntity<Object> response = postSignup(user, Object.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void postUserWhenUserHasDisplayNameWithLessThanRequiredThenReceiveBadRequest() {
        User user = TestUtil.createValidUser();
        user.setDisplayName("abc");
        ResponseEntity<Object> response = postSignup(user, Object.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void postUserWhenUserHasPasswordWithLessThanRequiredThenReceiveBadRequest() {
        User user = TestUtil.createValidUser();
        user.setPassword("P4sswd");
        ResponseEntity<Object> response = postSignup(user, Object.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void postUserWhenUserHasUsernameExceedsLengthLimitThanRequiredThenReceiveBadRequest() {
        User user = TestUtil.createValidUser();
        String valueOf256Chars = IntStream.rangeClosed(1, 256).mapToObj(x -> "a").collect(Collectors.joining());
        user.setUsername(valueOf256Chars);
        ResponseEntity<Object> response = postSignup(user, Object.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void postUserWhenUserHasDisplayNameExceedsLengthLimitThanRequiredThenReceiveBadRequest() {
        User user = TestUtil.createValidUser();
        String valueOf256Chars = IntStream.rangeClosed(1, 256).mapToObj(x -> "a").collect(Collectors.joining());
        user.setDisplayName(valueOf256Chars);
        ResponseEntity<Object> response = postSignup(user, Object.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void postUserWhenUserHasPasswordExceedsLengthLimitThanRequiredThenReceiveBadRequest() {
        User user = TestUtil.createValidUser();
        String valueOf256Chars = IntStream.rangeClosed(1, 256).mapToObj(x -> "a").collect(Collectors.joining());
        user.setDisplayName(valueOf256Chars + "A1");
        ResponseEntity<Object> response = postSignup(user, Object.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void postUserWhenUserHasPasswordWithAllLowerCaseThenReceiveBadRequest() {
        User user = TestUtil.createValidUser();
        user.setPassword("alllowercase");
        ResponseEntity<Object> response = postSignup(user, Object.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void postUserWhenUserHasPasswordWithAllUpperCaseThenReceiveBadRequest() {
        User user = TestUtil.createValidUser();
        user.setPassword("ALLUPPERCASE");
        ResponseEntity<Object> response = postSignup(user, Object.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void postUserWhenUserHasPasswordWithAllNumberThenReceiveBadRequest() {
        User user = TestUtil.createValidUser();
        user.setPassword("1234567890");
        ResponseEntity<Object> response = postSignup(user, Object.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void postUserWhenUserIsInvalidThenReceiveApiError() {
        User user = new User();
        ResponseEntity<ApiError> responseEntity = postSignup(user, ApiError.class);
        assertThat(responseEntity.getBody().getUrl()).isEqualTo(API_USERS);
    }

    @Test
    public void postUserWhenUserIsInvalidThenReceiveApiErrorWithValidationErrors() {
        User user = new User();
        ResponseEntity<ApiError> responseEntity = postSignup(user, ApiError.class);
        assertThat(responseEntity.getBody().getValidationErrors().size()).isEqualTo(3);
    }

    @Test
    public void postUserWhenUserHasNullUsernameThenReceiveMessageOfNullErrorForUsername() {
        User user = TestUtil.createValidUser();
        user.setUsername(null);
        ResponseEntity<ApiError> response = postSignup(user, ApiError.class);
        Map<String, String> validationErrors = response.getBody().getValidationErrors();
        assertThat(validationErrors.get("username")).isEqualTo("must not be null");
    }

    @Test
    public void postUserWhenUserHasNullPasswordThenReceiveGenericMessageOfNullError() {
        User user = TestUtil.createValidUser();
        user.setPassword(null);
        ResponseEntity<ApiError> response = postSignup(user, ApiError.class);
        Map<String, String> validationErrors = response.getBody().getValidationErrors();
        assertThat(validationErrors.get("password")).isEqualTo("must not be null");
    }

    @Test
    public void postUserWhenUserHasInvalidLengthUsernameThenReceiveGenericMessageOfSizeError() {
        User user = TestUtil.createValidUser();
        user.setUsername("abc");
        ResponseEntity<ApiError> response = postSignup(user, ApiError.class);
        Map<String, String> validationErrors = response.getBody().getValidationErrors();
        assertThat(validationErrors.get("username")).isEqualTo("size must be between 4 and 255");
    }

    @Test
    public void postUserWhenUserHasInvalidPasswordPatternThenReceiveGenericMessageOfPasswordPatternError() {
        User user = TestUtil.createValidUser();
        user.setPassword("alllowercase");
        ResponseEntity<ApiError> response = postSignup(user, ApiError.class);
        Map<String, String> validationErrors = response.getBody().getValidationErrors();
        assertThat(validationErrors.get("password")).isEqualTo("Password must have at least one uppercase, one lowercase letter and one number");
    }

    @Test
    public void postUserWhenAnotherUserHasSameUsernameThenReceiveBadRequest() {
        userRepository.save(TestUtil.createValidUser());
        User user = TestUtil.createValidUser();

        ResponseEntity<Object> response = postSignup(user, Object.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void postUserWhenAnotherUserHasSameUsernameThenReceiveMessage() {
        userRepository.save(TestUtil.createValidUser());
        User user = TestUtil.createValidUser();

        ResponseEntity<ApiError> response = postSignup(user, ApiError.class);
        Map<String, String> validationErrors = response.getBody().getValidationErrors();

        assertThat(validationErrors.get("username")).isEqualTo("This username is already in use");
    }

    public <T> ResponseEntity<T> postSignup(Object request, Class<T> response) {
        return testRestTemplate.postForEntity(API_USERS, request, response);
    }

    private void cleanUpDataBase() {
        userRepository.deleteAll();
    }
}
