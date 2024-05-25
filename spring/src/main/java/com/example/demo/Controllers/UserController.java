package com.example.demo.Controllers;

import com.example.demo.DTO.PasswordChangeDTO;
import com.example.demo.DTO.RegisterReq;
import com.example.demo.DTO.UsersDto;
import com.example.demo.Entity.Users;
import com.example.demo.Service.AuthService;
import com.example.demo.Service.UserService;
import com.example.demo.Service.UtilService;
import com.example.demo.mappers.UsersMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Claims;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true", maxAge = 3600)
public class UserController {
    private final UserService userService;
    private final UtilService utilService;
    private final UsersMapper usersMapper;

    public UserController(UserService userService,UtilService utilService, UsersMapper usersMapper) {

        this.userService = userService;
        this.utilService=utilService;
        this.usersMapper=usersMapper;
    }

    @GetMapping("/user")
    public ResponseEntity<UsersDto> getUserProfile(){
        Users user = utilService.getAuthenticatedUser(); // authorization - bearer
        return ResponseEntity.ok(usersMapper.map(user));
    }
    @PutMapping("/user/update")
    public ResponseEntity<Users> updateUserProfile(@RequestHeader("Authorization") String token, @RequestBody Users user){
        Users updated_user = userService.updateUserProfile(token, user);
        return new ResponseEntity<>(updated_user, HttpStatus.OK);
    }

    @PutMapping("/user/interestedCategory")
    public ResponseEntity<Long> addInterestedCategory(@RequestHeader("Authorization") String token, @RequestBody Long categoryId) {
        userService.addInterestedCategory(token, categoryId);
        return new ResponseEntity<Long>(HttpStatus.OK);
    }

    @PutMapping("/forgotpassword")
    public ResponseEntity<?> checkUser(@RequestBody PasswordChangeDTO values){
        ResponseEntity<?> user = userService.updatePassword(values);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    // transactional ekle calısmazsa
    @Transactional
    @DeleteMapping("/deleteuser")
    public ResponseEntity<?> deleteUser(@RequestHeader("Authorization") String token){
        userService.deleteUser(token);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Transactional
    @DeleteMapping("/user/deleteInterestedCategory/{categoryId}")
    public ResponseEntity<?> deleteInterestedCategory(@RequestHeader("Authorization") String token,@PathVariable Long categoryId){
        userService.deleteInterestedCategory(token,categoryId);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
