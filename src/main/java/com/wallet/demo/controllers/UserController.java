package com.wallet.demo.controllers;

import com.wallet.demo.models.User;
import com.wallet.demo.models.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path="/users")
public class UserController {
    
    @Autowired
    private UserRepository userRepository;

    @PostMapping(path = "")
    public String createUser(@RequestParam String name, @RequestParam String email) {
        User n = new User();
        n.setName(name);
        n.setEmail(email);
        userRepository.save(n);
        return "saved";
    } 

    @GetMapping(path = "/ping")
    public String ping() {
        return "pong";
    }
}
