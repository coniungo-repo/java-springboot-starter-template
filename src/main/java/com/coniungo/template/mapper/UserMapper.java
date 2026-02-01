package com.coniungo.template.mapper;

import com.coniungo.template.dto.UserDTO;
import com.coniungo.template.model.User;

public class UserMapper {

    public static UserDTO toDTO(User user) {
        return new UserDTO(user.getId(), user.getName());
    }

    public static User toEntity(UserDTO dto) {
        User user = new User();
        user.setName(dto.getName());
        return user;
    }
}