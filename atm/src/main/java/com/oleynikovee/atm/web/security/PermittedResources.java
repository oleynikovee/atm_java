package com.oleynikovee.atm.web.security;

public interface PermittedResources {
    String[] SWAGGER_WHITELIST = {
            "/","/swagger-ui/**",
            "/v3/api-docs**",
            "/v3/api-docs/**",
            "/swagger-resources/**",
            "/webjars/**"
    };

    String[] INDEX_WHITELIST = {
            "/index.css",
            "/img/*",
            "/favicon.ico"
    };

    String[] APP_WHITELIST = {
            "/auth/**",
            "/sso/**",
            "/csrf/**",

            "/",
            "/login/",
            "/user/"
    };

    String[] CONSOLE_WHITELIST = {
            "/console/**"
    };

    String[] ACTUATOR_WHITELIST = {
            "/actuator/info",
            "/actuator/health"
    };
}