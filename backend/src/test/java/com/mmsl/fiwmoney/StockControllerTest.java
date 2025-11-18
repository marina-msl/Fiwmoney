package com.mmsl.fiwmoney;

import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.mmsl.fiwmoney.controller.walletService;
import com.mmsl.fiwmoney.service.StockService;

@WebMvcTest(controllers = walletService.class,
     excludeAutoConfiguration = {
        org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration.class,
        org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration.class
    })
public class StockControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StockService service;

    @MockBean
    private JavaMailSender mailSender;

    @MockBean
    private SimpleMailMessage templateMessage;

    @Test
    public void testGetStockNotFound() throws Exception {
        String json = "{ \"code\": null, \"averagePrice\": 50.0, \"notify\": true }";

        mockMvc.perform(post("/stock")
               .contentType(MediaType.APPLICATION_JSON)
               .content(json))
            .andExpect(status().isBadRequest());

        verify(service, never()).fetchCurrentPrice(any());
        verify(service, never()).save(any());
    }
}
