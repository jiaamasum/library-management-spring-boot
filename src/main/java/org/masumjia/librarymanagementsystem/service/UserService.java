package org.masumjia.librarymanagementsystem.service;

import org.masumjia.librarymanagementsystem.dto.CreateUserRequest;
import org.masumjia.librarymanagementsystem.dto.UserDto;
import org.masumjia.librarymanagementsystem.entity.User;
import org.masumjia.librarymanagementsystem.exception.NotFoundException;
import org.masumjia.librarymanagementsystem.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final ModelMapper mapper;



    public UserService(UserRepository userRepository, ModelMapper mapper) {
        this.userRepository = userRepository;
        this.mapper = mapper;
    }

    public User getEntity(Long id){
        return userRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found"));
    }

    public UserDto create(CreateUserRequest req){
        User u = new User();
        u.setName(req.getName());
        u.setEmail(req.getEmail());
        return mapper.map(userRepository.save(u), UserDto.class);
    }


    public List<UserDto> list(){
        return userRepository.findAll().stream().map(u -> mapper.map(u, UserDto.class)).collect(Collectors.toList());
    }
}
