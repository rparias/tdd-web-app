package com.ronaldarias.backend.controllers;

import com.ronaldarias.backend.model.User;
import com.ronaldarias.backend.model.shared.CurrentUser;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@RestController
public class LoginController {

    @PostMapping("/api/1.0/login")
    Map<String, Object> handleLogin(@CurrentUser User loggedInUser) {
        return Collections.singletonMap("id", loggedInUser.getId());
    }
}
