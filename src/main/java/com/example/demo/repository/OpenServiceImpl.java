package com.example.demo.repository;

import com.example.demo.model.User;
import com.example.demo.registration.UserRegister;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class OpenServiceImpl implements OpenService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final EmailService emailService;

    public OpenServiceImpl(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder, EmailService emailService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
    }


    @Override
    public boolean registerUser(UserRegister userRegister) {

        User user = new User();

        user.setFirstname(userRegister.getFirstname());
            user.setLastname(userRegister.getLastname());
            user.setUsername(userRegister.getUsername());
            user.setAge(userRegister.getAge());
            user.setBirthDate(userRegister.getBirthDate());
            user.setEmail(userRegister.getEmail());
            user.setVerification(false);
            user.setROLE("USER");
            user.setPassword(
                    passwordEncoder.encode(userRegister.getPassword())
            );

            Random rand = new Random();
            int code=rand.nextInt(10000);
            user.setCode(code);

            /*emailService.sendEmail(userRegister.getEmail(),"check this link to get authorized: " +
                    "http://localhost:8080/api/v1/open/auth/verifie"+"here is your verification code" + code,"registration"); */

            userRepository.save(user);

        return true;
    }

    @Override
    public void makeUserVerified(User user,int code) {
        if(code==user.getCode()){
            user.setVerification(true);
            userRepository.save(user);
        }






    }


}

