package br.com.codenation.CentralDeErros.controller;


import br.com.codenation.CentralDeErros.model.User;
import br.com.codenation.CentralDeErros.service.UserService;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@Api(value = "Users", description = "Manage users", tags = {"Users"})
public class UserController {

    private UserService service;

    @GetMapping("/user/{id}")
    public User findById(@PathVariable Long id) {
        return service.findById(id).orElse(null);
    }

}