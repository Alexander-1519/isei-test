package com.example.iseitest.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class MailConfig {

    @Bean
    public JavaMailSender getMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

        mailSender.setHost("smtp.gmail.com");
        mailSender.setUsername("alexander.rybak2021@gmail.com");
        mailSender.setPassword("Lera6529963");
        mailSender.setPort(465);

        Properties properties = mailSender.getJavaMailProperties();
        properties.setProperty("mail.transport.protocol", "smtps");

        return mailSender;
    }
}
