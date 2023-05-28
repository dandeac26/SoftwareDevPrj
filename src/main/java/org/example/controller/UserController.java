package org.example.controller;

import org.example.entity.User;
import org.example.service.UserService;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @GetMapping("/getByEmail")
    @ResponseBody
    public User retrieveByEmail(@RequestParam("email") String email) {
        return userService.retrieveUserByEmail(email);
    }






    @DeleteMapping("/deleteID={id}")
    public ResponseEntity<Void> removeUser(@PathVariable Long id) {
        userService.removeUserById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/updateID={id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User updatedUser) {
        if (!id.equals(updatedUser.getUserId())) {
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

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody User newUser) {
        //check if email already exists
        User existingUser = userService.retrieveUserByEmail(newUser.getEmail());
        if (existingUser != null) {
            throw new IllegalArgumentException("Email already exists");
        }

        User savedUser = userService.createUser(newUser);

        return ResponseEntity.ok(savedUser);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> loginUser(@RequestBody User loginUser) {
        String email = loginUser.getEmail();
        String password = loginUser.getPassword();

        // Retrieve the user by email
        User user = userService.retrieveUserByEmail(email);

        if (user != null) {
            // Compare the provided password with the stored hashed password
            if (BCrypt.checkpw(password, user.getPassword())) {
                // Authentication successful
                Map<String, String> response = new HashMap<>();
                response.put("status", "ok");
                response.put("message", "Login successful");
                return ResponseEntity.ok(response);
            }
        }

        // Authentication failed
        Map<String, String> response = new HashMap<>();
        response.put("status", "error");
        response.put("message", "Invalid email or password");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

}
