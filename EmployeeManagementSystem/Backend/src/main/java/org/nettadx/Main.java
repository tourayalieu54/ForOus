package org.nettadx;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
      SpringApplication.run(Main.class, args);
    }


  @Bean
  public WebMvcConfigurer corsConfigurer() {

    // Configure CORS globally versus
    // controller-by-controller.
    // Can be combined with .
    return new WebMvcConfigurer() {

      @Override
      public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
          //.allowedOrigins("*")
          // Should tighten up CORS policies.
          // For now, we allow everything.
          .allowedOrigins("http://localhost:3000",
            "http://localhost:4200",
            "http://127.0.0.1:5500")
          .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS");
      }
    };
  }
}


