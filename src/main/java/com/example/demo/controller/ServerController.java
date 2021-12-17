package com.example.demo.controller;

import com.example.demo.enums.ServerStatus;
import com.example.demo.model.Server;
import com.example.demo.model.User;
import com.example.demo.repository.ServerServiceImpl;
import com.example.demo.repository.UserServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ServerController {

    private final UserServiceImpl userService;
    private final ServerServiceImpl serverServiceImp;

    public ServerController(UserServiceImpl userService, ServerServiceImpl serverServiceImp) {
        this.userService = userService;
        this.serverServiceImp = serverServiceImp;
    }

    @RequestMapping(value = "/add/new/server", method = RequestMethod.POST)
    public ResponseEntity<?> addServer(@AuthenticationPrincipal UserDetails userDetails, @RequestBody ge.springboot.sweeftdigital.request.ServerRequest request) {
        try {
            Server existingServer = serverServiceImp.findServerByName(request.getName());
            if (existingServer == null) {
                serverServiceImp.addServer(request);
                return new ResponseEntity<>("Server successfully saved.", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Server already exists!", HttpStatus.CREATED);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Error while saving server ", HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/addServerToUser", method = RequestMethod.POST)
    public ResponseEntity<?> addServerToUser(@AuthenticationPrincipal UserDetails userDetails,
                                             @RequestParam String serverName) {
        User user1 = userService.findByEmail(userDetails.getUsername());
        Server server = serverServiceImp.findServerByName(serverName);
        if (server.getStatus() != ServerStatus.BUSY) {
            userService.addServerToUser(user1, serverName);
        } else {
            return new ResponseEntity<>("Server already busy", HttpStatus.BAD_REQUEST);
        }
        if (user1.getServer() != null) {
            return new ResponseEntity<>("Server with name: " + serverName + " " +
                    "successfully added to user with name: ", HttpStatus.OK);
        } else return new ResponseEntity<>("Error while adding server to user", HttpStatus.BAD_REQUEST);
    }



}
