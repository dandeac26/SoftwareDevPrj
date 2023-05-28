package org.example.service;

import org.example.entity.User;
import org.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;


    public List<User> retrieveUsers() {
        return (List<User>) userRepository.findAll();
    }

    public User retrieveUserById(Long id) {

        Optional<User> user = userRepository.findById(id);

        if(user.isPresent()) {
            return user.get();
        } else {
            return null;
        }
    }
    public void removeUserById(Long id) {
        userRepository.deleteById(id);
    }

    public User updateUser(User updatedUser) {
        //need to add a check if the user id exists!!
        User existingUser = userRepository.findById(updatedUser.getUserId()).orElseThrow();
        existingUser.setFirstName(updatedUser.getFirstName());
        existingUser.setLastName(updatedUser.getLastName());
        existingUser.setEmail(updatedUser.getEmail());
        return userRepository.save(existingUser);
    }

    public User createUser(User newUser) {
        return userRepository.save(newUser);
    }

    public User retrieveUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

}
