package com.coniungo.template.controller;

import com.coniungo.template.dto.UserDTO;
import com.coniungo.template.service.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    private HttpHeaders jsonHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

    // CREATE
    @PostMapping(produces = "application/json", consumes = "application/json")
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO) {
        UserDTO createdUser = userService.createUser(userDTO);
        return new ResponseEntity<>(createdUser, jsonHeaders(), HttpStatus.CREATED);
    }

    // READ ALL
    @GetMapping(produces = "application/json")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> users = userService.getAllUsers();
        return new ResponseEntity<>(users, jsonHeaders(), HttpStatus.OK);
    }

    // READ BY ID
    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<Object> getUserById(@PathVariable UUID id) {
        try {
            UserDTO user = userService.getUserById(id);
            return new ResponseEntity<>(user, jsonHeaders(), HttpStatus.OK);
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return new ResponseEntity<>(error, jsonHeaders(), HttpStatus.NOT_FOUND);
        }
    }

    // UPDATE
    @PutMapping(value = "/{id}", produces = "application/json", consumes = "application/json")
    public ResponseEntity<Object> updateUser(@PathVariable UUID id, @RequestBody UserDTO userDTO) {
        try {
            UserDTO updatedUser = userService.updateUser(id, userDTO);
            return new ResponseEntity<>(updatedUser, jsonHeaders(), HttpStatus.OK);
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return new ResponseEntity<>(error, jsonHeaders(), HttpStatus.NOT_FOUND);
        }
    }

    // DELETE
    @DeleteMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<Map<String, String>> deleteUser(@PathVariable UUID id) {
        try {
            userService.deleteUser(id);
            Map<String, String> resp = new HashMap<>();
            resp.put("message", "User deleted successfully");
            return new ResponseEntity<>(resp, jsonHeaders(), HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return new ResponseEntity<>(error, jsonHeaders(), HttpStatus.NOT_FOUND);
        }
    }

}
