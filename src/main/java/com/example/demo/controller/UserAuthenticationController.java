package com.example.demo.controller;


import com.example.demo.Exceptions.InvalidOperationException;
import com.example.demo.bag.WebResponse;
import com.example.demo.jwt.JwtTokenProvider;
import com.example.demo.model.User;
import com.example.demo.registration.UserRegister;
import com.example.demo.repository.OpenService;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.UserService;
import com.example.demo.token.CreateTokenRequest;
import io.swagger.annotations.Api;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Api(tags = "User")
@RestController
public class UserAuthenticationController extends OpenController{

    private final AuthenticationManager authenticationManager ;
    private UserService userService;
    private final OpenService openService;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;

    public UserAuthenticationController(AuthenticationManager authenticationManager, UserService userService, OpenService openService, JwtTokenProvider jwtTokenProvider, UserRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.openService = openService;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userRepository = userRepository;
    }

    @RequestMapping(value = "/auth/sign-up", method = RequestMethod.POST)
    //რეგისტრაცია
    public ResponseEntity<String> register(@Valid @RequestBody UserRegister userRegister){
        if (openService.registerUser(userRegister)){
            return new ResponseEntity(HttpStatus.OK);
        }

        throw new InvalidOperationException("Cannot Register User");
    }

    @PostMapping("/auth/verifie/{code}")
    //აქ მომხმარებელმა უნდა გაიაროს ვერიფიკაცია იმეილზე მიღებული ოთხნიშნა კოდით;
    public void verifieUser(@Valid @RequestBody CreateTokenRequest data, @PathVariable int code) {
        User user = (User) userService.loadUserByUsername(data.getEmail());
        openService.makeUserVerified(user,code);

    }

    @PostMapping("/auth/token")
    public ResponseEntity signin(@Valid @RequestBody CreateTokenRequest data, HttpServletRequest request) {
        User user = (User) userService.loadUserByUsername(data.getEmail());

        System.out.println(user.getPassword());
        System.out.println(data.getPassword());
        List<String>ana=new ArrayList<>();
        ana.add(user.getROLE());
        Authentication authentication = new UsernamePasswordAuthenticationToken(user.getUsername(),data.getPassword(),getGrantedAuthorities(ana) );
        authenticationManager.authenticate(authentication);
        String token;
        SecurityContextHolder.getContext().setAuthentication(authentication);
        if (user.isVerification()==false){
            token=null;
        }else{
            token = jwtTokenProvider.createToken(user);
        }
        //თუ ვერიფიკაცია არ აქვს გავლილი ტოკენი არ დაუბრუნდება და შესაბამისად სხვა ენდპოინტებსაც გავუწერთ, რომ არავერიფიცირებულ მომხმარებელს წვდომა არ მივცეთ

        return WebResponse
                .create()
                .add("token",token)
                .compactWeb();
    }

    private List<GrantedAuthority> getGrantedAuthorities(List<String> privileges) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String privilege : privileges) {
            authorities.add(new SimpleGrantedAuthority(privilege));
        }
        return authorities;
    }

}
