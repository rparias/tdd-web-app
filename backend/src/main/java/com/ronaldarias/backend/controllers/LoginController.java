package com.ronaldarias.backend.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import com.ronaldarias.backend.configuration.Views;
import com.ronaldarias.backend.model.User;
import com.ronaldarias.backend.model.shared.CurrentUser;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RestController
public class LoginController {

    @PostMapping("/api/1.0/login")
    @JsonView(Views.Base.class)
    User handleLogin(@CurrentUser User loggedInUser) {
        return loggedInUser;
    }
}
