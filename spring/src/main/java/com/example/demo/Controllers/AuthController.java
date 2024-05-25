package com.example.demo.Controllers;


import com.example.demo.DTO.RegisterReq;
import com.example.demo.Service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowCredentials = "true")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<RegisterReq> register(@RequestBody RegisterReq registerRequest){
        return ResponseEntity.ok(authService.Register(registerRequest));
    }

    @PostMapping("/login")
    public ResponseEntity<RegisterReq> login(@RequestBody RegisterReq loginRequest){
            return ResponseEntity.ok(authService.Login(loginRequest));
    }

    @PostMapping("/refresh")
    public ResponseEntity<RegisterReq> refreshToken(@RequestBody RegisterReq refreshTokenRequest){
        return ResponseEntity.ok(authService.refreshToken(refreshTokenRequest));
    }

}
