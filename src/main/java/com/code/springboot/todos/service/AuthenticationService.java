package com.code.springboot.todos.service;

import com.code.springboot.todos.request.AuthenticationRequest;
import com.code.springboot.todos.request.RegisterRequest;
import com.code.springboot.todos.response.AuthenticationResponse;

public interface AuthenticationService {
    void register(RegisterRequest input )throws Exception;

    AuthenticationResponse login(AuthenticationRequest request) throws IllegalAccessException;
}
