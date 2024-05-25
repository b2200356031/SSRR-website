package com.example.demo.Service;

import com.example.demo.Entity.Coupon;
import com.example.demo.Entity.Product;

import com.example.demo.Entity.Users;
import com.example.demo.UserRepo.CouponRepo;
import com.example.demo.UserRepo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;


@Service
public class CouponService {

    private final CouponRepo couponRepo;

    @Autowired
    public CouponService(CouponRepo couponRepo) {
        this.couponRepo = couponRepo;
    }

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepo userRepo;

    public Coupon addCoupon(Product product, Coupon coupon){
        coupon.setProduct(product);
        return couponRepo.save(coupon);
    }

    public Coupon updateCoupon(Product product, Coupon coupon){
        coupon.setProduct(product);
        return couponRepo.save(coupon);
    }

    public void deleteCoupon(Long id){
       couponRepo.deleteCouponByCouponId(id);
    }

    public List<Coupon> findCouponsByProductId(Long productId){

        return couponRepo.findCouponByProductId(productId);
    }


    public Coupon redeemCoupon(String token, Coupon coupon) {
        Users user = userService.getCurrentUser(token.substring(7));

        if(user.getPoints()>=coupon.getPrice()){
            if (coupon.getAvaibleCoupons()>0){
            System.out.println(31);

            user.setPoints(user.getPoints()-coupon.getPrice());
            userRepo.save(user);
            coupon.setAvaibleCoupons(coupon.getAvaibleCoupons()-1);
            return couponRepo.save(this.addOwnedUser(coupon,user.getUserId()));}
            else {
                throw new RuntimeException();
            }
        }
        else throw new RuntimeException();


    }


    public Coupon addOwnedUser(Coupon coupon,Long newUserId) {
        if (coupon.getOwnedUsers()==null){

            Long[] ownedUsers = new Long[1];
            ownedUsers[0] = newUserId;
            coupon.setOwnedUsers(ownedUsers);
            return coupon;
        }
        else {

            int newLength = coupon.getOwnedUsers().length + 1;
            Long[] ownedUsers = coupon.getOwnedUsers();
            Long[] copy = Arrays.copyOf(ownedUsers, newLength);
            copy[copy.length - 1] = newUserId;
            coupon.setOwnedUsers(copy);
            return coupon;


        }

    }

    public Coupon editCoupon(Coupon coupon,String token){
        Users user = userService.getCurrentUser(token.substring(7));
        Product product = coupon.getProduct();
        if (product.getMerchantId()==user.getUserId()){
            System.out.println(11);
        }


        return coupon;
    }

    public void deleteCoupon(Long couponId,String token){
        Users user = userService.getCurrentUser(token.substring(7));
        Coupon coupon = couponRepo.findCouponByCouponId(couponId);
        Product product = coupon.getProduct();
        if (product.getMerchantId()==user.getUserId()){
            System.out.println(11);
            couponRepo.deleteCouponByCouponId(couponId);
        }



    }
}
