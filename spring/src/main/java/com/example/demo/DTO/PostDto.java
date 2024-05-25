package com.example.demo.DTO;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PostDto {
    Long id;
    String title;
    String content;
    Integer likesCount= 0;
    Integer commentsCount= 0;
    Integer viewsCount= 0;
    UsersDto usersDto;
    LocalDateTime creationDate;

}
