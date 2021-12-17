package com.example.demo.repository;

import com.example.demo.Exceptions.InvalidOperationException;
import com.example.demo.enums.ServerStatus;
import com.example.demo.model.Server;
import com.example.demo.model.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ServerRepository serverRepository;

    public UserServiceImpl(UserRepository userRepository, ServerRepository serverRepository) {
        this.userRepository = userRepository;
        this.serverRepository = serverRepository;
    }


    @Override
    public User findByEmail(String email) {
        User user=userRepository.findByEmail(email);
        if(user==null){
            throw new UsernameNotFoundException("user dont found");
        }
        return user;

    }

    @Override
    public List<User> getUserList() {

        return userRepository.findAll();

    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user= userRepository.findByEmail(username);
        if (user==null){
            throw new UsernameNotFoundException("Dont Found "+username);
        }

        return  user;
    }

    @Override
    @Transactional
    public void addServerToUser(User user, String serverName) {
        Server server = serverRepository.findServerByName(serverName);
        if (server != null && server.getStatus() == ServerStatus.FREE) {
            server.setStatus(ServerStatus.BUSY);
            serverRepository.save(server);
            user.setServer(server);
            userRepository.save(user);

        } else {
            throw  new InvalidOperationException("Server didn't added.");
        }
    }

}
