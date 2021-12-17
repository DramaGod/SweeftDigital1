package com.example.demo.repository;

public interface EmailService {
    void sendEmail(String to,String body,String topic);
    void sendHtmlEmail(String to,String body,String topic);
}
