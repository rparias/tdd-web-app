package com.ronaldarias.backend.controllers;

import com.ronaldarias.backend.error.ApiError;
import com.ronaldarias.backend.model.User;
import com.ronaldarias.backend.repositories.UserRepository;
import com.ronaldarias.backend.services.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.support.BasicAuthenticationInterceptor;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class LoginControllerTest {

    public static final String API_LOGIN = "/api/1.0/login";

    @Autowired
    TestRestTemplate testRestTemplate;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @Before
    public void cleanup() {
        userRepository.deleteAll();
        testRestTemplate.getRestTemplate().getInterceptors().clear();
    }

    @Test
    public void postLoginWithoutUserCredentialsReceivesUnauthorized() {
        ResponseEntity<Object> response = login(Object.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    @Test
    public void postLoginWithIncorrectCredentialsReceivesUnauthorized() {
        authenticate();
        ResponseEntity<Object> response = login(Object.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    @Test
    public void postLoginWithoutUserCredentialsReceivesApiError() {
        ResponseEntity<ApiError> response = login(ApiError.class);
        assertThat(response.getBody().getUrl()).isEqualTo(API_LOGIN);
    }

    @Test
    public void postLoginWithoutUserCredentialsReceivesApiErrorWithoutValidationErrors() {
        ResponseEntity<String> response = login(String.class);
        assertThat(response.getBody().contains("validationErrors")).isFalse();
    }

    @Test
    public void postLoginWithIncorrectCredentialsReceivesUnauthorizedWithoutWWWAuthenticationHeader() {
        authenticate();
        ResponseEntity<Object> response = login(Object.class);
        assertThat(response.getHeaders().containsKey("WWW-Authenticate")).isFalse();
    }

    @Test
    public void postLoginWithValidCredentialsReceivesOk() {
        User user = TestUtil.createValidUser();
        userService.save(user);
        authenticate();

        ResponseEntity<Object> response = login(Object.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void postLoginWithValidCredentialsReceivesLoggedInUserId() {
        User user = TestUtil.createValidUser();
        User userInDB = userService.save(user);
        authenticate();

        ResponseEntity<Map<String, Object>> response = login(new ParameterizedTypeReference<Map<String, Object>>() {});
        Map<String, Object> body = response.getBody();
        Integer id = (Integer) body.get("id");

        assertThat(id).isEqualTo(userInDB.getId());
    }

    public <T> ResponseEntity<T> login(Class<T> responseType) {
        return testRestTemplate.postForEntity(API_LOGIN, null, responseType);
    }

    public <T> ResponseEntity<T> login(ParameterizedTypeReference<T> responseType) {
        return testRestTemplate.exchange(API_LOGIN, HttpMethod.POST, null, responseType);
    }

    private void authenticate() {
        testRestTemplate.getRestTemplate().
                getInterceptors().add(new BasicAuthenticationInterceptor("test-user", "P4ssword"));
    }
}
