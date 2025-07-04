package com.cognixia.fh.capstone.dao;

import com.cognixia.fh.capstone.model.User;

public class UserNotFoundException extends Exception {
    public static final long serialVersionUID = 1L;

    public UserNotFoundException(User user) {
        super("User was not found: " + user);
    }
}
