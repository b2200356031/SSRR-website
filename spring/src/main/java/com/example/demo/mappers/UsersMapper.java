package com.example.demo.mappers;

import com.example.demo.DTO.UsersDto;
import com.example.demo.Entity.Users;
import org.springframework.stereotype.Component;

@Component

public class UsersMapper {
    public static UsersDto map(Users user){
        UsersDto usersDto=new UsersDto();
        if(user==null) return usersDto;
        usersDto.setId(user.getId());
        usersDto.setNick(user.getNick());
        usersDto.setUsername(user.getUsername());
        usersDto.setFirstName(user.getFirstName());
        usersDto.setEmail(user.getEmail());
        usersDto.setRole(user.getRole());
        usersDto.setInterestedCategory(user.getInterestedCategory());
        return usersDto;
    }
}
