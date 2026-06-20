package com.code.springboot.todos.util;

import com.code.springboot.todos.entity.User;

import java.nio.file.AccessDeniedException;

public interface FindAuthenticatedUser {

    User getAuthenticatedUser() throws AccessDeniedException;
}
