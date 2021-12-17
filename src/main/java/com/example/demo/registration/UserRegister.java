package com.example.demo.registration;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

public class UserRegister implements Serializable {

    @NotNull(message = "{validation.user.firstname.required}")
    private String firstname;

    @NotNull
    private String lastname;

    @NotNull
    private String username;

    @NotNull
    private int age;

    private Date birthDate;

    @Email
    @NotNull
    private String email;

    @NotNull
    private String password;

    @NotNull
    private String rePassword;

    public UserRegister(String firstname, String lastname, String username, int age, Date birthDate, String email, String password, String rePassword) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.username = username;
        this.age = age;
        this.birthDate = birthDate;
        this.email = email;
        this.password = password;
        this.rePassword = rePassword;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRePassword() {
        return rePassword;
    }

    public void setRePassword(String rePassword) {
        this.rePassword = rePassword;
    }
}
