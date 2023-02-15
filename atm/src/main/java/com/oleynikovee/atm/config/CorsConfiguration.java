package com.oleynikovee.atm.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
public class CorsConfiguration implements WebMvcConfigurer {
    private final CorsConfigProperties corsConfigProperties;
    private static final String SWAGGER_UI = "/swagger-ui.html";

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addRedirectViewController("/", SWAGGER_UI);
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        log.info("Enable CORS with following configuration: [{}]", corsConfigProperties);
        registry.addMapping("/**")
                .allowedOrigins(getTargetParameters(corsConfigProperties.getAllowedOrigins()))
                .allowedMethods(getTargetParameters(corsConfigProperties.getAllowedMethods()))
                .allowedHeaders(getTargetParameters(corsConfigProperties.getAllowedHeaders()))
                .allowCredentials(corsConfigProperties.getAllowCredentials())
                .exposedHeaders("Content-Disposition")
                .maxAge(corsConfigProperties.getMaxAge());
    }

    private String[] getTargetParameters(List<String> targetParameters) {
        return targetParameters.toArray(new String[0]);
    }
}