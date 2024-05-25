package com.example.demo.Controllers;


import com.example.demo.DTO.RegisterReq;
import com.example.demo.Entity.Product;
import com.example.demo.UserRepo.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true", maxAge = 3600)
//@CrossOrigin(origins = "http://localhost:4201", allowCredentials = "true", maxAge = 3600)

public class AdminController {

    @Autowired
    private ProductRepo productRepo;

    @GetMapping("/public/product")
    public ResponseEntity<Object> getAllProducts(){
        return ResponseEntity.ok(productRepo.findAll());
    }

    @PostMapping("/admin/saveproduct")
    public ResponseEntity<Object> signUp(@RequestBody RegisterReq productRequest){
        Product productToSave = new Product();
        productToSave.setName(productRequest.getName());
        return ResponseEntity.ok(productRepo.save(productToSave));
    }

    @GetMapping("/user/alone")
    public ResponseEntity<Object> userAlone(){
        return ResponseEntity.ok("Users alone can access this api!");
    }


    @GetMapping("/adminuser/both")
    public ResponseEntity<Object> bothAdminandUser(){
        return ResponseEntity.ok("Both admin and user can access this api");
    }

}
