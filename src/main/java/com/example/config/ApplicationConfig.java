package com.example.config;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

@ApplicationPath("/api") // Định nghĩa đường dẫn gốc cho API
public class ApplicationConfig extends Application {
    // JAX-RS sẽ tự động phát hiện các endpoint REST
}
