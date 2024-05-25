package com.example.demo.Service;


import com.example.demo.DTO.PasswordChangeDTO;
import com.example.demo.Entity.Users;
import com.example.demo.UserRepo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class UserService {
    private final UserRepo userRepo;
    private final JWTUtils jwtUtils;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserService(UserRepo userRepo, JWTUtils jwtUtils) {
        this.userRepo = userRepo;
        this.jwtUtils = jwtUtils;
    }

    public List<Users> findAllUsers(){
        return userRepo.findAll();
    }

    public Users findUserByEmail(String email){
        return userRepo.findByEmail(email).orElseThrow();
    }

    public Users getCurrentUser(String token) {
        String userEmail = jwtUtils.extractUsername(token);
        return userRepo.findByEmail(userEmail).orElseThrow();
    }

    public Users updateUserProfile(String token, Users user){
        Users olduser = getCurrentUser(token.substring(7));
        olduser.setEmail(user.getEmail());
        olduser.setNick(user.getNick());
        olduser.setUsername(user.getNick());
        olduser.setPassword(passwordEncoder.encode(user.getPassword()));
        olduser.setFirstName(user.getFirstName());
        olduser.setLastName(user.getLastName());
        return userRepo.save(olduser);
    }

    public void addInterestedCategory(String token, Long categoryId) {
        Users user = getCurrentUser(token.substring(7));
        if (user.getInterestedCategory()==null){

            Long[] interestedCategory = new Long[1];
            interestedCategory[0] = categoryId;
            user.setInterestedCategory(interestedCategory);
            userRepo.save(user);
        }
        else {
            Long[] interestedCategory = user.getInterestedCategory();
            boolean check = true;
            for (Long a:interestedCategory){
                if (a==categoryId){
                    check = false;
                }
            }
            if (check){
                int newLength = user.getInterestedCategory().length + 1;
                Long[] copy = Arrays.copyOf(interestedCategory, newLength);
                copy[copy.length - 1] = categoryId;
                user.setInterestedCategory(copy);
                userRepo.save(user);
            }

        }


    }

    public ResponseEntity<?> updatePassword(PasswordChangeDTO values){
        try
        {
            Users user = findUserByEmail(values.getEmail());
            user.setPassword(passwordEncoder.encode(values.getPassword()));
            userRepo.save(user);
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
        catch (NoSuchElementException e)
        {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }
    }

    public void deleteUser(String token){
        userRepo.deleteById(getCurrentUser(token.substring(7)).getId());
    }

    public void deleteInterestedCategory(String token, Long categoryId) {
        Users user = getCurrentUser(token.substring(7));
        Long[] interestedCategory = user.getInterestedCategory();
        List<Long> updatedCategories = new ArrayList<>(Arrays.asList(interestedCategory));
        updatedCategories.remove(categoryId);
        int newLength = user.getInterestedCategory().length - 1;
        Long[] newInterestedCategory = new Long[newLength];
        int i=0;
        for (Long category:updatedCategories){
            newInterestedCategory[i]=category;
            i++;
        }
        user.setInterestedCategory(newInterestedCategory);
        userRepo.save(user);



    }
}
