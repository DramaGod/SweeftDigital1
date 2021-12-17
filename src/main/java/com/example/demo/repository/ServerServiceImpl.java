package com.example.demo.repository;

import com.example.demo.enums.ServerStatus;
import com.example.demo.model.Server;
import com.example.demo.repository.ServerService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ServerServiceImpl implements ServerService {

    private final ServerRepository serverRepository;

    public ServerServiceImpl(ServerRepository serverRepository) {
        this.serverRepository = serverRepository;
    }

    @Override
    public void addServer(ge.springboot.sweeftdigital.request.ServerRequest request) {

        Server server = new Server();
        server.setName(request.getName());
        server.setExpirationDate(new Date(request.getExpirationDateString()));
        server.setCapacity_MB(request.getCapacity_MB());
        if (request.getServerStatusString().toLowerCase().equals("free")) {
            server.setStatus(ServerStatus.FREE);
        } else {
            server.setStatus(ServerStatus.BUSY);
        }
        serverRepository.save(server);

    }

    @Override
    public Server findServerByName(String name) {
        Server server=serverRepository.findServerByName(name);
        if(server==null){
            throw new UsernameNotFoundException("server dont found");
        }
        return server;
    }



}
