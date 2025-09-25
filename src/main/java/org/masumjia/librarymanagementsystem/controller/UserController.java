package org.masumjia.librarymanagementsystem.controller;

import jakarta.validation.Valid;
import org.masumjia.librarymanagementsystem.dto.CreateUserRequest;
import org.masumjia.librarymanagementsystem.dto.UserDto;
import org.masumjia.librarymanagementsystem.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;
    public UserController(UserService userService) { this.userService = userService; }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto create(@Valid @RequestBody CreateUserRequest req){ return userService.create(req); }

    @GetMapping
    public List<UserDto> list(){ return userService.list(); }
}
