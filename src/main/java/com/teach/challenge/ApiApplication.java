package com.teach.challenge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@SpringBootApplication
public class ApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiApplication.class, args);
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/usuarios")
						.allowedOrigins("http://localhost:5173") // Coloque a origem do cliente aqui
						.allowedMethods("POST") // Especifique os métodos HTTP permitidos, se necessário
						.allowedHeaders("*") // Permita todos os cabeçalhos, ou especifique os cabeçalhos permitidos
						.allowCredentials(true); // Permita o envio de cookies, se necessário
			}
		};
	}

}
