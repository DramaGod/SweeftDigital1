package com.example.demo.repository;

import com.example.demo.model.Server;
import com.example.demo.model.User;
import com.example.demo.registration.UserRegister;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import org.springframework.security.core.userdetails.UserDetailsService;
import java.util.List;

public interface UserService extends UserDetailsService {

    public void addServerToUser(User user, String serverName);

    User findByEmail(String email);

    List<User> getUserList();



}