package com.jircik.springcrud.controller;

import com.jircik.springcrud.model.User;
import com.jircik.springcrud.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public List<User> getUsers(){
        return userService.getUsers();
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable Integer id){
        return userService.getUserById(id);
    }

    @GetMapping("/name/{name}")
    public User getUserByName(@PathVariable String name){
        return userService.getUserByName(name);
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED) // Retorna o código de status HTTP 201 (Created)
    public void addNweUser(@RequestBody User user){
        userService.insertUser(user);
    }

    @PutMapping
    public void updateUser(@RequestBody User user) {
        userService.updateUser(user);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT) // Retorna o código de status HTTP 204 (No Content)
    public void removeUserById(@PathVariable Integer id){
        userService.deleteById(id);
    }

    @DeleteMapping("/name/{name}")
    @ResponseStatus(HttpStatus.NO_CONTENT) // Retorna o código de status HTTP 204 (No Content)
    public void removeUserByName(@PathVariable String name){
        userService.deleteByName(name);
    }


    @ExceptionHandler(IllegalStateException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND) // Retorna o código de status HTTP 404 (Not Found)
    public String handleIllegalStateException(IllegalStateException ex) {
        return ex.getMessage();
    }
}