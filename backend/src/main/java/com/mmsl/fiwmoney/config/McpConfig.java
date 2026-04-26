package com.mmsl.fiwmoney.config;

import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mmsl.fiwmoney.adapters.in.mcp.tool.StockTools;

@Configuration
public class McpConfig {

    @Bean
    ToolCallbackProvider stockToolsProvider(StockTools stockTools) {
        return MethodToolCallbackProvider.builder().toolObjects(stockTools).build();
    }
}
