package com.proud.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "katsubushi")
@Data
public class KatsubushiProperties {
    private String address;
    private int port;
}
