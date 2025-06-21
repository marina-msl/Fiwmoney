    package com.mmsl.fiwmoney.service;

import java.util.Properties;

    import org.springframework.context.annotation.Bean;
    import org.springframework.context.annotation.Configuration;
    import org.springframework.mail.SimpleMailMessage;
    import org.springframework.mail.javamail.JavaMailSender;
    import org.springframework.mail.javamail.JavaMailSenderImpl;

    @Configuration
    public class MailConfig {

        // @Value("${spring.mail.host}")
        // private String host;
        // @Value("${spring.mail.username}")
        // private String email;
        // @Value("${spring.mail.port}")
        // private int port;
        
        @Bean
        JavaMailSender mailSender() {
            JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
            mailSender.setHost("sandbox.smtp.mailtrap.io");
            mailSender.setPort(2525);
            mailSender.setUsername("22ac82083cd13d");
            mailSender.setPassword("c1c1d0da4f0b1e");
            Properties props = mailSender.getJavaMailProperties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttl.enable", "true");
            props.put("mail.smtp.ssl.enable", "false");

            return mailSender;
        }

        @Bean
        SimpleMailMessage templateMessage() {
            SimpleMailMessage msg = new SimpleMailMessage();
            msg.setFrom("fiwmoney.99@gmail.com");
            msg.setSubject("Good news!!");
            msg.setText("Time to buy! Prices are lower!!");
            return msg;
        }
        
    }
