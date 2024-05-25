package com.example.demo.DTO;


import com.example.demo.Entity.Product;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)

public class RegisterReq {
    private int statusCode;
    private String error;
    private String message;
    private String token;
    private String refreshToken;
    private String expirationTime;
    private String firstname;
    private String lastname;
    private String nick;
    private String username;
    private String name;
    private String email;
    private String password;
    private String role;
    private List<Product> products;
    private String users;
    private String profilePic;
}
