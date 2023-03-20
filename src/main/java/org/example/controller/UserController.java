package org.example.controller;

import org.example.entity.User;
import org.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping( "/users")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping( "/getAll")
    @ResponseBody
    public List<User> retrieveUsers() {
        return userService.retrieveUsers();
    }


    @GetMapping("/getById")
    @ResponseBody
    public User retrieveById(){
        return userService.retrieveUserById(1L);
    }

    @DeleteMapping("/deleteCNP={cnp}")
    public ResponseEntity<Void> removeUser(@PathVariable Long cnp) {
        userService.removeUserById(cnp);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/updateCNP={cnp}")
    public ResponseEntity<User> updateUser(@PathVariable Long cnp, @RequestBody User updatedUser) {
        if (!cnp.equals(updatedUser.getUserId())) {
            throw new IllegalArgumentException("ID in path must match ID in request body");
        }
        User savedUser = userService.updateUser(updatedUser);
        return ResponseEntity.ok(savedUser);
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User newUser) {
        User savedUser = userService.createUser(newUser);
        return ResponseEntity.ok(savedUser);
    }
}
