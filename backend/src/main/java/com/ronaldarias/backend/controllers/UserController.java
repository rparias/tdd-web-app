package com.ronaldarias.backend.controllers;

import com.ronaldarias.backend.model.User;
import com.ronaldarias.backend.model.shared.GenericResponse;
import com.ronaldarias.backend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("api/1.0/users")
    public GenericResponse createUser(@RequestBody User user){
        userService.save(user);
        return new GenericResponse("User saved successfully");
    }
}
