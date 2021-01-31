package com.ronaldarias.backend.annotations.validators;

import com.ronaldarias.backend.annotations.UniqueUsername;
import com.ronaldarias.backend.model.User;
import com.ronaldarias.backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueUsernameValidator implements ConstraintValidator<UniqueUsername, String> {

    @Autowired
    UserRepository userRepository;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        User userInDB = userRepository.findByUsername(value);
        return userInDB == null;
    }
}
