package com.jircik.springcrud.service;

import com.jircik.springcrud.model.User;
import com.jircik.springcrud.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    //Get All
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public User getUserByName(String name) {
        return userRepository.findByName(name).orElseThrow(() -> new IllegalStateException(name + " not found"));
    }

    public void insertUser(User user) {
        userRepository.save(user);
    }

    public void deleteById(Integer id) {
        if (userRepository.existsById(id)){
            userRepository.deleteById(id);
        } else{
            throw new IllegalStateException("User with ID " + id + " does not exist and cannot be deleted.");
        }
    }

    public void deleteByName(String name) {
        User userToDelete = userRepository.findByName(name).orElseThrow(() -> new IllegalStateException("User with name " + name + " does not exist and cannot be deleted."));

        userRepository.delete(userToDelete);
    }

    public void updateUser(User updatedUser) {
        // 1. Find the user by ID, or else throw an exception
        User existingUser = userRepository.findById(updatedUser.getId())
                .orElseThrow(() -> new IllegalStateException(
                        "User with ID " + updatedUser.getId() + " does not exist and cannot be updated."
                ));

        // 2. Modify the existing entity with the new data from the updatedUser object

        // Check if the name is provided and is different before setting
        if (updatedUser.getName() != null &&
                !updatedUser.getName().isEmpty() &&
                !existingUser.getName().equals(updatedUser.getName())) {

            existingUser.setName(updatedUser.getName());
        }

        // Check if the email is provided and is different before setting
        if (updatedUser.getEmail() != null &&
                !updatedUser.getEmail().isEmpty() &&
                !existingUser.getEmail().equals(updatedUser.getEmail())) {

            existingUser.setEmail(updatedUser.getEmail());
        }

        // 3. Save the modified existing entity (this performs the SQL UPDATE)
        userRepository.save(existingUser);
    }
}
