package com.mmsl.fiwmoney.adapters.controllers;

import org.springframework.ai.chat.client.ChatClient;

public class LLMController {

    private final ChatClient ai;


    LLMController(ChatClient ai) {
        this.ai = ai;
    }
    
}
