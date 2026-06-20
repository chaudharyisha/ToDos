package com.code.springboot.todos.service;

import com.code.springboot.todos.response.UserResponse;

import java.nio.file.AccessDeniedException;

public interface UserService {

    UserResponse getUserInfo() throws AccessDeniedException;

    void deleteUser() throws AccessDeniedException;
}
