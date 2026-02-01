package com.coniungo.template.service;

import com.coniungo.template.dto.UserDTO;
import com.coniungo.template.mapper.UserMapper;
import com.coniungo.template.model.User;
import com.coniungo.template.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Create a new user
    public UserDTO createUser(UserDTO userDTO) {
        User userEntity = UserMapper.toEntity(userDTO);
        User savedUser = userRepository.save(userEntity);
        return UserMapper.toDTO(savedUser);
    }

    // Create batch users
    public List<UserDTO> createUsers(List<UserDTO> userDTOs) {
        List<User> users = userDTOs.stream()
                .map(UserMapper::toEntity)
                .toList();
        List<User> savedUsers = userRepository.saveAll(users);
        return savedUsers.stream()
                .map(UserMapper::toDTO)
                .toList();
    }

    // Get a user by ID
    public UserDTO getUserById(UUID id) {
        return userRepository.findById(id)
                .map(UserMapper::toDTO)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + id));
    }

    // Get all users
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(UserMapper::toDTO)
                .collect(Collectors.toList());
    }

    // Update a user by ID
    public UserDTO updateUser(UUID id, UserDTO userDTO) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + id));

        existingUser.setName(userDTO.getName());
        User updatedUser = userRepository.save(existingUser);
        return UserMapper.toDTO(updatedUser);
    }

    // Delete a user by ID
    public void deleteUser(UUID id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("User not found with ID: " + id);
        }
        userRepository.deleteById(id);
    }

    // Delete all users
    public void deleteAllUsers() {
        userRepository.deleteAll();
    }
}
