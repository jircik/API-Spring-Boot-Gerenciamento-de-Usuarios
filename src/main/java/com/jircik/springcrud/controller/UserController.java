package com.jircik.springcrud.controller;

import com.jircik.springcrud.model.User;
import com.jircik.springcrud.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/users")
public class UserController {
    private final UserService userService;

    public UserController (UserService userService){
        this.userService = userService;
    }

    //read

    //get all
    @GetMapping
    public List<User> getUsers(){
        return userService.getUsers();
    }

    //getByName
    @GetMapping("/name/{name}")
    public User getUserByName(@PathVariable String name){
        return userService.getUserByName(name);
    }


    //add
    @PostMapping
    public void addNweUser(@RequestBody User user){ userService.insertUser(user); }


    //delete

    //delete by id
    @DeleteMapping("/{id}")
    public void removeUserById(@PathVariable Integer id){
        userService.deleteById(id);
    }

    //delete by name
    @DeleteMapping("/name/{name}")
    public void removeUserByName(@PathVariable String name){
        userService.deleteByName(name);
    }

    //Update
    @PutMapping
    public void updateUser(@RequestBody User user) {
        userService.updateUser(user);
    }

}
