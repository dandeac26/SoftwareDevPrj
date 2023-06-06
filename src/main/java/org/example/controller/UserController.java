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

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

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
        User user = userService.retrieveUserByEmail(email);
        if (user == null) {
            return null;
        } else {
            return user;
        }
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

//    @PostMapping("/register")
//    public ResponseEntity<User> registerUser(@RequestBody User newUser) {
//        //check if email already exists
//        User existingUser = userService.retrieveUserByEmail(newUser.getEmail());
//        if (existingUser != null) {
//
//            //throw new IllegalArgumentException("Email already exists");
//            //return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email already exists");
//              return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email already exists");
//
//        }
//
//        User savedUser = userService.createUser(newUser);
//
//        return ResponseEntity.ok(savedUser);
//    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User newUser) {
        //check if email already exists
        User existingUser = userService.retrieveUserByEmail(newUser.getEmail());
        if (existingUser != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email already exists");
        }

        User savedUser = userService.createUser(newUser);

        return ResponseEntity.ok(savedUser);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> loginUser(@RequestBody User loginUser) {
        String email = loginUser.getEmail();
        String password = loginUser.getPassword();

        // Retrieve the user by email
        User user = userService.retrieveUserByEmail(email);

        if (user != null) {
            // Compare the provided password with the stored hashed password
            if (BCrypt.checkpw(password, user.getPassword())) {
                // Authentication successful
                Map<String, Object> response = new HashMap<>();
                response.put("status", "ok");
                response.put("message", "Login successful");
                response.put("id", user.getUserId());
                response.put("email", user.getEmail());
                // Add any other necessary user information to the response

                String jwt = Jwts.builder()
                        .setSubject(String.valueOf(user.getUserId())) // set subject to user's id
                        .claim("email", user.getEmail()) // add claim for email
                        .signWith(Keys.secretKeyFor(SignatureAlgorithm.HS256))  // Sign the JWT
                        .compact();  // Build the JWT and serialize it to a compact, URL-safe string

                // Add the JWT to the response
                response.put("token", jwt);

                return ResponseEntity.ok(response);
            }
        }

        // Authentication failed
        Map<String, Object> response = new HashMap<>();
        response.put("status", "error");
        response.put("message", "Invalid email or password");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }


}
