package com.oleynikovee.atm.web.controller;

import com.oleynikovee.atm.model.security.User;
import com.oleynikovee.atm.model.security.UserPrincipal;
import com.oleynikovee.atm.service.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.oleynikovee.atm.config.Constants.API_KEY;

@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
@Tag(name = "Authorization", description = "Provides login/me endpoints")
public class LoginController {
    private final AuthenticationService service;
    private final UserPrincipal loggedUser;

    @PostMapping(path = "/")
    @Operation(description = "Exchanges login/password for a valid bearer token")
    public ResponseEntity<String> login(@RequestParam String login, @RequestParam String password) {
        return ResponseEntity.ok(service.login(login, password));
    }

    @GetMapping(path = "/me")
    @SecurityRequirement(name = API_KEY)
    @Operation(description = "Provides user information based on bearer token")
    public ResponseEntity<User> me() {
        return ResponseEntity.ok(loggedUser.getUser());
    }
}