package com.oleynikovee.atm.web.controller;

import com.oleynikovee.atm.model.security.User;
import com.oleynikovee.atm.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.oleynikovee.atm.config.Constants.API_KEY;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@RolesAllowed("ADMIN")
@Tag(name = "User manager", description = "Provides access to user management. Admin only!")
public class UserController {
    private final UserService userService;

    @PostMapping(path = "/add")
    @SecurityRequirement(name = API_KEY)
    @Operation(description = "Adds a new user to the system. Password should be set for new user!")
    public ResponseEntity<Integer> addUser(@RequestBody User user) {
        return ResponseEntity.accepted().body(userService.addUser(user));
    }

    @GetMapping(path = "/{userId}")
    @SecurityRequirement(name = API_KEY)
    @Operation(description = "Retrieves a user by id")
    public ResponseEntity<User> getUser(@PathVariable("userId") Integer userId) {
        return ResponseEntity.ok(userService.getUserById(userId));
    }

    @GetMapping
    @SecurityRequirement(name = API_KEY)
    @Operation(description = "Lists all users in the system")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAll());
    }

    @DeleteMapping(value = "/{userId}")
    @SecurityRequirement(name = API_KEY)
    @Operation(description = "Deletes a user by id")
    ResponseEntity<Void> deleteUser(@PathVariable("userId") Integer userId) {
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }

}
