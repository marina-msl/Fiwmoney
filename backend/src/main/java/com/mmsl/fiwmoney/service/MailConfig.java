    package com.mmsl.fiwmoney.service;

    import org.springframework.beans.factory.annotation.Value;
    import org.springframework.context.annotation.Bean;
    import org.springframework.context.annotation.Configuration;
    import org.springframework.mail.SimpleMailMessage;
    import org.springframework.mail.javamail.JavaMailSender;
    import org.springframework.mail.javamail.JavaMailSenderImpl;

    @Configuration
    public class MailConfig {

        @Value("${spring.mail.host}")
        private String host;
        @Value("${spring.mail.username}")
        private String email;
        @Value("${spring.mail.port}")
        private int port;
        
        @Bean
        JavaMailSender mailSender() {
            JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
            mailSender.setHost(host);
            mailSender.setPort(port);
            return mailSender;
        }

        @Bean
        SimpleMailMessage templateMessage() {
            SimpleMailMessage msg = new SimpleMailMessage();
            msg.setFrom(email);
            msg.setSubject("Good news!!");
            msg.setText("Time to buy! Prices are lower!!");
            return msg;
        }
        
    }
