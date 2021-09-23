package br.com.codenation.CentralDeErros.controller;


import br.com.codenation.CentralDeErros.DTO.ErrorEventLogDTO;
import br.com.codenation.CentralDeErros.DTO.PostErrorEventLogDTO;
import br.com.codenation.CentralDeErros.DTO.PostUserDTO;
import br.com.codenation.CentralDeErros.mapper.MapStructMapper;
import br.com.codenation.CentralDeErros.model.ErrorEventLog;
import br.com.codenation.CentralDeErros.model.User;
import br.com.codenation.CentralDeErros.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@AllArgsConstructor
@Api(value = "Users", description = "Manage users", tags = {"Users"})
public class UserController {

    @Autowired
    private UserService service;
    @Autowired
    private MapStructMapper mapStructMapper;

    @GetMapping("/user/{id}")
    public User findById(@PathVariable Long id) {
        return service.findById(id).orElse(null);
    }

    @PostMapping(value = "/user")
    @ApiOperation("Create an user")
    @ApiResponses(value = {@ApiResponse(code = 201, message = "User created successfully")})
    public ResponseEntity<User> saveUser (@Valid @RequestBody PostUserDTO postUserDTO){
        return new ResponseEntity<User>(this.service.save(mapStructMapper.PostUserDTOToUser(postUserDTO)), HttpStatus.CREATED);
    }
}