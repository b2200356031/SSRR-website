package com.example.demo.Service;

import com.example.demo.DTO.RegisterReq;
import com.example.demo.Entity.Users;
import com.example.demo.UserRepo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class AuthService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private JWTUtils jwtUtils;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    public RegisterReq Register(RegisterReq registrationRequest){
        RegisterReq resp = new RegisterReq();

        try{
            Users users = new Users();
            users.setEmail(registrationRequest.getEmail());
            users.setUsername(registrationRequest.getUsername());

            users.setNick(registrationRequest.getUsername());

            users.setFirstName(registrationRequest.getFirstname());
            users.setLastName(registrationRequest.getLastname());

            users.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
            users.setRole(registrationRequest.getRole());

            Users ourUserResult = userRepo.save(users);
            if (ourUserResult.getId() > 0){
                resp.setUsers(String.valueOf(ourUserResult));
                resp.setMessage("User Saved Successfully!");
                resp.setStatusCode(200);
            }
        }
        catch (Exception e){
            resp.setStatusCode(500);
            resp.setError(e.getMessage());
        }
        return resp;
    }

    public RegisterReq Login(RegisterReq loginRequest){
        RegisterReq response  = new RegisterReq();

        try{
           authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
           var user = userRepo.findByEmail(loginRequest.getEmail()).orElseThrow();

           System.out.println("USER IS: "+ user);

           var jwt = jwtUtils.generateToken(user);
           var refreshToken = jwtUtils.generateRefreshToken(new HashMap<>(), user);
           response.setStatusCode(200);
           response.setToken(jwt);
           response.setRefreshToken(refreshToken);
           response.setExpirationTime("24Hr");
           response.setMessage("User Logged In!");
           System.out.println(response);
        }
        catch (Exception e){
            response.setStatusCode(500);
            response.setError(e.getMessage());
        }

        return response;
    }

    public RegisterReq refreshToken(RegisterReq refreshTokenRequest){

        RegisterReq response  = new RegisterReq();
        String ourEmail = jwtUtils.extractUsername(refreshTokenRequest.getToken());
        Users users = userRepo.findByEmail(ourEmail).orElseThrow();

        if(jwtUtils.isTokenValid(refreshTokenRequest.getToken(), users)){
            var jwt = jwtUtils.generateToken(users);
            response.setStatusCode(200);
            response.setToken(jwt);
            response.setRefreshToken(refreshTokenRequest.getRefreshToken());
            response.setExpirationTime("24Hr");
            response.setMessage("User Logged In!");
        }

        response.setStatusCode(500);
        return response;
    }


}
