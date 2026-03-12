package com.jircik.springcrud.service;

import com.jircik.springcrud.model.User;
import com.jircik.springcrud.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Integer id) {
        return userRepository.findById(id).orElseThrow(() -> new IllegalStateException("User with ID " + id + " not found"));
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
        User existingUser = userRepository.findById(updatedUser.getId())
                .orElseThrow(() -> new IllegalStateException(
                        "User with ID " + updatedUser.getId() + " does not exist and cannot be updated."
                ));

        if (updatedUser.getName() != null &&
                !updatedUser.getName().isEmpty() &&
                !existingUser.getName().equals(updatedUser.getName())) {

            existingUser.setName(updatedUser.getName());
        }

        if (updatedUser.getEmail() != null &&
                !updatedUser.getEmail().isEmpty() &&
                !existingUser.getEmail().equals(updatedUser.getEmail())) {

            existingUser.setEmail(updatedUser.getEmail());
        }
        userRepository.save(existingUser);
    }
}
