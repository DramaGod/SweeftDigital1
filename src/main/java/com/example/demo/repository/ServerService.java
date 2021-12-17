package com.example.demo.repository;

import com.example.demo.model.Server;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

public interface ServerService {

    public void addServer(ge.springboot.sweeftdigital.request.ServerRequest request);

    public Server findServerByName(String name);



}
