package com.example.demo.Controllers;


import com.example.demo.Entity.Product;
import com.example.demo.Entity.Coupon;
import com.example.demo.Entity.Review;
import com.example.demo.Entity.Users;
import com.example.demo.Service.CouponService;
import com.example.demo.Service.ProductService;
import com.example.demo.Service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true", maxAge = 3600)
public class CouponController {

    private final CouponService couponService;

    @Autowired
    private ProductService productService;
    
    @Autowired
    private UserService userService;

    public CouponController(CouponService couponService) {
        this.couponService = couponService;
    }

    @PostMapping("/service/addCoupon/{productId}")
    public ResponseEntity<Coupon> addReview(@PathVariable("productId")Long productId, @RequestBody Coupon coupon){

        Product product = productService.findProductById(productId);
        Coupon newCoupon = couponService.addCoupon(product,coupon);
        return new ResponseEntity<>(newCoupon, HttpStatus.CREATED);

    }

    @GetMapping("/product/{productId}/coupon")
    public ResponseEntity<List<Coupon>> getCoupon(@PathVariable Long productId,@RequestHeader("Authorization") String token){
        
        Users user = userService.getCurrentUser(token.substring(7));
        List<Coupon> coupons = couponService.findCouponsByProductId(productId);
        List<Coupon> newlist = new ArrayList<>();
        for (Coupon c :coupons){
            if (c.getOwnedUsers()!=null){
                Long[] couponUserIds = c.getOwnedUsers();
                boolean containsUserId = Arrays.asList(couponUserIds).contains(user.getUserId());
                if (containsUserId){

                }
                else {
                    newlist.add(c);
                }
            }
            else {
                newlist.add(c);
            }

        }
        System.out.println(coupons);
        return new ResponseEntity<>(newlist, HttpStatus.OK);
    }

    @GetMapping("/product/{productId}/redeemedCoupon")
    public ResponseEntity<List<Coupon>> getRedeemedCoupon(@PathVariable Long productId,@RequestHeader("Authorization") String token){

        Users user = userService.getCurrentUser(token.substring(7));
        List<Coupon> coupons = couponService.findCouponsByProductId(productId);
        List<Coupon> newlist = new ArrayList<>();
        for (Coupon c :coupons){
            if (c.getOwnedUsers()!=null){
                Long[] couponUserIds = c.getOwnedUsers();
                boolean containsUserId = Arrays.asList(couponUserIds).contains(user.getUserId());
                if (containsUserId){
                    newlist.add(c);
                }
                else {

                }
            }
            else {

            }

        }
        System.out.println(coupons);
        return new ResponseEntity<>(newlist, HttpStatus.OK);
    }


    @PutMapping("/product/redeem")
    public ResponseEntity<Coupon> redeemCoupon(@RequestBody Coupon coupon,@RequestHeader("Authorization") String token){

        Coupon redeemCoupon = couponService.redeemCoupon(token, coupon);

        return new ResponseEntity<>(redeemCoupon, HttpStatus.CREATED);
    }

    @PutMapping("/service/updateCoupon")
    public ResponseEntity<Coupon> editCoupon(@RequestBody Coupon coupon,@RequestHeader("Authorization") String token){

        Coupon newCoupon = couponService.editCoupon(coupon,token);

        return new ResponseEntity<>(newCoupon, HttpStatus.CREATED);
    }

    @Transactional
    @DeleteMapping("/service/deleteCoupon/{couponId}")
    public ResponseEntity<?> deleteCoupon(@PathVariable Long couponId,@RequestHeader("Authorization") String token){

        couponService.deleteCoupon(couponId,token);

        return new ResponseEntity<>(HttpStatus.OK);
    }




}
